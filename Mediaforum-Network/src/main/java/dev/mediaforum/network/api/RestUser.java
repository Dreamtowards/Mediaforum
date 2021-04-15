package dev.mediaforum.network.api;

import dev.mediaforum.network.NetworkApplication;
import dev.mediaforum.network.conf.RestException;
import dev.mediaforum.network.dao.model.Savelist;
import dev.mediaforum.network.dao.model.User;
import dev.mediaforum.network.dao.UserRepository;
import dev.mediaforum.network.externalutil.CollectionUtils;
import dev.mediaforum.network.externalutil.Validate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/", method = RequestMethod.POST)
public class RestUser {

    public static RestUser INSTANCE;
    {
        INSTANCE = this;
    }
    private static final Predicate<String> VALID_EMAIL_ADDRESS =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).asPredicate();


    @Autowired
    public UserRepository userRepository;

    /**
     * req:  {email:"Email.lowCase", username:"Username.len>=5", password_digest:"hex(sha256(pwdUtf8))"}
     * resp: {*I}
     */
    //todo: validate email_validate_id. a id of dataSet{id:int, email:String, validateCode:?, status:Enum}
    @RequestMapping("user_register")
    public Map<String, Object> userRegister(@RequestBody Map<String, Object> data) {
        String email = ((String)data.get("email")).toLowerCase();
        String username = (String)data.get("username");
        String passwordDigest = ((String)data.get("password_digest")).toLowerCase();  // hex-str str-lowCase str-len:64, 256bits.

        Validate.isTrue(VALID_EMAIL_ADDRESS.test(email), "Invalid email address.");
        Validate.isTrue(username.length() >= 5, "Username can't short than 5 characters.");
        Validate.isTrue(passwordDigest.length() == 64, "Illegal password_digest sha256 hex string.");

        if (userRepository.findByEmail(email) != null)
            throw new RestException("This account email already exists.");
        if (userRepository.findByUsernameIgnoreCase(username) != null)
            throw new RestException("This account username already exists.");

        // create the User
        User user = new User();
        user.email = email;
        user.username = username;
        user.passwordDigest = passwordDigest;
        user.registerTime = System.currentTimeMillis();
        userRepository.saveAndFlush(user);

        // create init their savelist-s
        RestSavelist.initCreateUserBasicSavelists(user);

        return proto_login_pass(user);
    }

    /**
     * req:  {account:"emailOrUsername", password_digest:"hex(sha256(pwdUtf8))"}
     * resp: {*I}
     */
    @RequestMapping(value = "user_login")
    public Map<String, Object> userLogin(@RequestBody Map<String, Object> data) {
        String emailOrUsername = (String)data.get("account");
        String passwordDigest  = ((String)data.get("password_digest")).toLowerCase();

        User user = userRepository.findByEmail(emailOrUsername);
        if (user == null) {
            user = userRepository.findByUsernameIgnoreCase(emailOrUsername);
            if (user == null)
                throw new RestException("doesn't exists the email or username.");
        }

        if (!user.passwordDigest.equals(passwordDigest)) {
            throw new RestException("wrong password.");
        }

        return proto_login_pass(user);
    }

    private Map<String, Object> proto_login_pass(User user) {
        return CollectionUtils.asMap(
                "uid", user.id,
                "accessToken", user.passwordDigest
        );
    }

    /**
     * req:  {*I}
     * resp: {username, avatar_url, email, savelists: {
     *     HISTORY: list_id,
     *     READLATER: list_id,
     *     LIKEDPOSTS: list_id,
     *     created: {
     *         name: "list_name",
     *         id: list_id
     *     },
     *     // collected: { ... }
     * }}
     */
    @RequestMapping(value = "user_info")  // user_getinfo ?
    public Map<String, Object> userInfo(@RequestBody Map<String, Object> data) {
        User user = validateUserAccess(data);

        List<Savelist> userCreatedSavelists = RestSavelist.INSTANCE.savelistRepository.findAllByAuthorIdOrderByCreatedTimeDesc(user.id);
        Savelist lHis=null, lRea=null, lLik=null;
        for (Savelist l : userCreatedSavelists) {
            switch (l.name) {
                case Savelist.HISTORY: lHis = l;break;
                case Savelist.READLATER: lRea = l;break;
                case Savelist.LIKEDPOSTS: lLik = l;break;
            }
        }
        userCreatedSavelists.remove(lHis);userCreatedSavelists.remove(lRea);userCreatedSavelists.remove(lLik);
        JSONArray jUserCreatedSavelists = new JSONArray();
        for (Savelist s : userCreatedSavelists) {
            jUserCreatedSavelists.put(savelistInfo(s));
        }

        return CollectionUtils.asMap(
                "username", user.username,
                "avatar_url", getUserAvatarUrl(user),
                "email", user.email,
                "savelists", CollectionUtils.asMap(
                        "HISTORY", savelistInfo(lHis).toMap(),
                        "READLATER", savelistInfo(lRea).toMap(),
                        "LIKEDPOSTS", savelistInfo(lLik).toMap(),
                        "created", jUserCreatedSavelists.toList()
                )
        );
    }
    private static JSONObject savelistInfo(Savelist savelist) {
        return new JSONObject()
                .put("id", savelist.id)
                .put("name", savelist.name);
    }


    public static String getUserAvatarUrl(User user) {
        return user.avatarUrl==null?NetworkApplication.API_BASE +"/usercontents/1/1b9d6bcd-bbfd-4b2d-9b5d-ab8dfbbd4bed/default_avatar.png": user.avatarUrl;
    }


    public static User validateUserAccess(Map<String, Object> map) {
        return validateUserAccess((Integer)map.get("uid"), (String)map.get("accessToken"));
    }
    public static User validateUserAccess(Integer uid, String accessToken) {
        User user = INSTANCE.userRepository.findById(uid).orElseThrow(() -> new RestException("the user id doesn't exists."));
        if (!user.passwordDigest.equals(accessToken))
            throw new RestException("wrong accessToken");
        return user;
    }
}
