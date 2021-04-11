package dev.mediaforum.network.api;

import dev.mediaforum.network.dao.*;
import dev.mediaforum.network.dao.model.Post;
import dev.mediaforum.network.dao.model.Savelist;
import dev.mediaforum.network.dao.model.SavelistItem;
import dev.mediaforum.network.dao.model.User;
import dev.mediaforum.network.externalutil.CollectionUtils;
import dev.mediaforum.network.externalutil.Validate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.mediaforum.network.api.RestUser.validateUserAccess;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/", method = RequestMethod.POST)
public class RestPost {

    @Autowired
    private PostRepository postRepository;

    /**
     * req:  {*I, title, content}
     * resp: {post_id}
     */
    @RequestMapping(value = "post_new")
    public Map<String, Object> postNew(@RequestBody Map<String, Object> data) {
        User user = validateUserAccess(data);
        String title   = (String)data.get("title");
        String content = (String)data.get("content");

        Validate.isTrue(!title.isEmpty(), "title cant be empty");
        Validate.isTrue(!content.isEmpty(), "content cant be empty.");

        Post post = new Post(user.id, title, content);
        postRepository.saveAndFlush(post);

        return CollectionUtils.asMap("post_id", post.id);
    }

    @RequestMapping("post_get")
    public Post postGet(@RequestBody Map<String, Object> data) {
        int postId = (int)data.get("post_id");
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("the article doesnt exists."));
    }

    /**
     * resp: [{
     *     id: postId,
     *     title: postTitle,
     *     content: markdownOriginalContent
     * }]
     */
    public List<Object> postGetList(User user, List<Integer> postIds) {
        int userLIKEDPOSTS_Id = RestSavelist.INSTANCE.savelistRepository.findByAuthorIdAndName(user.id, Savelist.LIKEDPOSTS).id;

        JSONArray jPostList = new JSONArray();
        for (Integer postId : postIds) {
            Post post = postRepository.findById(postId).get();  // findAllById() not working with ids-order.
            JSONObject jPost = new JSONObject();
            jPost.put("id", post.id);
            jPost.put("title", post.title);
            jPost.put("content", post.content);
            jPost.put("comment_count", RestComment.INSTANCE.commentRepository.countByPostId(post.id));
            jPost.put("post_time", post.postTime);

            User author = RestUser.INSTANCE.userRepository.findById(post.authorId).get();
            jPost.put("user_username", author.username);
            jPost.put("user_avatar_url", RestUser.userAvatarUrl(author));

            jPost.put("was_liked", RestSavelist.INSTANCE.savelistItemRepository.findBySavelistIdAndPostId(userLIKEDPOSTS_Id, post.id) != null);
            jPost.put("liked_count", RestSavelist.INSTANCE.savelistItemRepository.countByPostId(post.id));  // even not really liked-count, there is "saved" count actually.
            jPostList.put(jPost);
        }
        return jPostList.toList();
    }

    /**
     * req:  {*I, }
     * resp: <postGetList>
     */
    @RequestMapping("post_getfeedlist")
    public List<Object> postGetListFeed(@RequestBody Map<String, Object> data) {
        User user = RestUser.validateUserAccess(data);

        List<Integer> ids = postRepository.findAllByOrderByPostTimeDesc().stream().map(p -> p.id).collect(Collectors.toList());

        return postGetList(user, ids);
    }

    /**
     * this may should in RestSavelist
     * req:  {*I, savelist_id}
     * resp: <postGetList>
     */
    @RequestMapping("post_getsavelist")
    public List<Object> tmp_postGetSavelist(@RequestBody Map<String, Object> data) {
        User user = RestUser.validateUserAccess(data);
        int savelistId = (int)data.get("savelist_id");

        RestSavelist.validateUserCreatedSavelist(user.id, savelistId);
        List<SavelistItem> savelistItems = RestSavelist.INSTANCE.savelistItemRepository.findAllBySavelistIdOrderByAddedTimeDesc(savelistId);

        List<Integer> ids = savelistItems.stream().map(i -> i.postId).collect(Collectors.toList());
        return postGetList(user, ids);
    }
}
