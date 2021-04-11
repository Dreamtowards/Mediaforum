package dev.mediaforum.network.api;

import dev.mediaforum.network.conf.RestException;
import dev.mediaforum.network.dao.*;
import dev.mediaforum.network.dao.model.Savelist;
import dev.mediaforum.network.dao.model.SavelistItem;
import dev.mediaforum.network.dao.model.User;
import dev.mediaforum.network.externalutil.CollectionUtils;
import dev.mediaforum.network.externalutil.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/", method = RequestMethod.POST)
public class RestSavelist {

    public static RestSavelist INSTANCE;
    {
        INSTANCE = this;
    }

    @Autowired
    public SavelistRepository savelistRepository;

    @Autowired
    public SavelistItemRepository savelistItemRepository;

    public static void initCreateUserBasicSavelists(User user) {
        INSTANCE.savelistRepository.save(new Savelist(user.id, Savelist.HISTORY));
        INSTANCE.savelistRepository.save(new Savelist(user.id, Savelist.READLATER));
        INSTANCE.savelistRepository.save(new Savelist(user.id, Savelist.LIKEDPOSTS));
        INSTANCE.savelistRepository.flush();
    }

    public static Savelist validateUserCreatedSavelist(int authorId, int savelistId) {
        Savelist savelist = INSTANCE.savelistRepository.findById(savelistId).orElseThrow(() -> new RestException("The savelist not exists."));
        Validate.isTrue(savelist.authorId == authorId, "You had not permission to do the savelist.");
        return savelist;
    }

    public static void validateSavelistNameSysKey(String savelistName) {
        Validate.isTrue(
                !savelistName.equalsIgnoreCase(Savelist.HISTORY) &&
                !savelistName.equalsIgnoreCase(Savelist.READLATER) &&
                !savelistName.equalsIgnoreCase(Savelist.LIKEDPOSTS), "Illegal savelist operation. triggered savelist SysKeyName.");
    }


    /**
     * req:  {*I, savelist_id, post_id, op: add/remove}
     * resp: <empty when success>
     */
    @RequestMapping("savelist_opitem")
    public void savelistAdditem(@RequestBody Map<String, Object> data) {
        User user = RestUser.validateUserAccess(data);
        int savelistId = (int)data.get("savelist_id");
        int postId = (int)data.get("post_id");
        String op = (String)data.get("op");

        // checks is the savelist was been created by the user
        RestSavelist.validateUserCreatedSavelist(user.id, savelistId);

        SavelistItem alreadyExistedItem = savelistItemRepository.findBySavelistIdAndPostId(savelistId, postId);
        if (op.equals("add")) {
            if (alreadyExistedItem != null)  // already existed
                return;
            savelistItemRepository.save(new SavelistItem(savelistId, postId));
        } else if (op.equals("remove")) {
            if (alreadyExistedItem == null)  // already none exists
                return;
            savelistItemRepository.deleteBySavelistIdAndPostId(savelistId, postId);
        } else {
            throw new RestException("Illegal op enum.");
        }
    }

    /**
     * req:  {*I, savelist_id}
     * resp: {name}
     */
    @RequestMapping("savelist_getinfo")
    public Map<String, Object> savelistGetInfo(@RequestBody Map<String, Object> data) {
        User user = RestUser.validateUserAccess(data);
        int savelistId = (int)data.get("savelist_id");

        Savelist savelist = RestSavelist.validateUserCreatedSavelist(user.id, savelistId);

        return CollectionUtils.asMap(
                "name", savelist.name
        );
    }

    /**
     * req:  {*I, name}
     * resp: <empty when success>
     */
    @RequestMapping("savelist_new")
    public Map<String, Object> savelistNew(@RequestBody Map<String, Object> data) {
        User user = RestUser.validateUserAccess(data);
        String name = (String)data.get("name");

        Validate.isTrue(name.length() >= 5, "Savelist name had bigger than 5 characters.");
        RestSavelist.validateSavelistNameSysKey(name);

        Savelist savelist = new Savelist(user.id, name);
        savelistRepository.save(savelist);
        return CollectionUtils.asMap("id", savelist.id);
    }

    /**
     * req:  {*I, savelist_ids:[], post_id}
     * resp: [contained_savelist_ids]
     */
    @RequestMapping("savelist_containspost")
    public List<Integer> savelistContains(@RequestBody Map<String, Object> data) {
        User user = RestUser.validateUserAccess(data);
        List<Integer> savelistIds = (List<Integer>)data.get("savelist_ids");
        int postId = (int)data.get("post_id");

        List<Integer> containedSavelistIds = new ArrayList<>();
        for (int savelistId : savelistIds) {
            RestSavelist.validateUserCreatedSavelist(user.id, savelistId);
            if (RestSavelist.INSTANCE.savelistItemRepository.findBySavelistIdAndPostId(savelistId, postId) != null) {
                containedSavelistIds.add(savelistId);
            }
        }
        return containedSavelistIds;
    }

    /**
     * MODIFY OPERATION!
     *
     * req:  {*I, savelist_id}
     * resp: <empty when success>
     */
    @RequestMapping("savelist_delete")
    public void savelistDelete(@RequestBody Map<String, Object> data) {
        User user = RestUser.validateUserAccess(data);
        int savelistId = (int)data.get("savelist_id");
        Savelist savelist = RestSavelist.validateUserCreatedSavelist(user.id, savelistId);
        RestSavelist.validateSavelistNameSysKey(savelist.name);

        // del its savelist items
        savelistItemRepository.deleteBySavelistId(savelistId);

        savelistRepository.deleteById(savelistId);
    }

}
