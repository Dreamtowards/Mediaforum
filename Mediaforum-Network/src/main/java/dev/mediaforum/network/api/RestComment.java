package dev.mediaforum.network.api;

import dev.mediaforum.network.dao.model.Comment;
import dev.mediaforum.network.dao.CommentRepository;
import dev.mediaforum.network.dao.model.User;
import dev.mediaforum.network.externalutil.CollectionUtils;
import dev.mediaforum.network.externalutil.Validate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/", method = RequestMethod.POST)
public class RestComment {

    public static RestComment INSTANCE;
    {
        INSTANCE = this;
    }

    @Autowired
    public CommentRepository commentRepository;

    /**
     * req:  {*I, post_id, parent_comment_id, content}
     * resp: {id: the_new_comment_id}
     */
    @RequestMapping("comment_new")
    public Map<String, Object> commentNew(@RequestBody Map<String, Object> data) {
        User user = RestUser.validateUserAccess(data);
        int postId = (int)data.get("post_id");
        int parentCommentId = (int)data.get("parent_comment_id");
        String content = (String)data.get("content");

        Validate.isTrue(!content.isEmpty(), "comment content cant be empty.");

        Comment comment = new Comment(user.id, postId, parentCommentId, content);
        commentRepository.saveAndFlush(comment);

        return CollectionUtils.asMap("id", comment.id);
    }

    // todo: display "my" comment at first
    /**
     * req:  {post_id}
     * resp: [{
     *     id: commentId,
     *     parent_comment_id, parentCommentId
     *     post_time: commentPostTime,
     *     content: commentContent,
     *     user_id:,
     *     user_username:,
     *     user_avatar_url:,
     *     replies: [{
     *         ...,
     *         replies:[]
     *     }]
     * }]
     */
    @RequestMapping("comment_getlist")
    public List<Object> commentGetList(@RequestBody Map<String, Object> data) {
        int postId = (int)data.get("post_id");
        List<Comment> rootCommentList = commentRepository.findAllByPostIdAndParentCommentId(postId, 0);
        JSONArray jCommentList = new JSONArray();
        for (Comment comment : rootCommentList) {
            jCommentList.put(fillCommentInfo(comment, postId));
        }
        return jCommentList.toList();
    }

    private JSONObject fillCommentInfo(Comment comment, int postId) {
        JSONObject jsonComm = new JSONObject();

        jsonComm.put("id", comment.id)
                .put("parent_comment_id", comment.parentCommentId)
                .put("post_time", comment.postTime)
                .put("content", comment.content);


        User user = RestUser.INSTANCE.userRepository.findById(comment.authorId).get();
        jsonComm.put("user_id", user.id)
                .put("user_username", user.username)
                .put("user_avatar_url", RestUser.getUserAvatarUrl(user));

        JSONArray jSubReplies = new JSONArray();
        for (Comment c : commentRepository.findAllByPostIdAndParentCommentId(postId, comment.id)) {
            jSubReplies.put(
                    fillCommentInfo(c, postId)
            );
        }
        jsonComm.put("replies", jSubReplies);
        return jsonComm;
    }

}
