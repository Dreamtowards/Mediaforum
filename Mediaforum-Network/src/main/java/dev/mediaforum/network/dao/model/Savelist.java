package dev.mediaforum.network.dao.model;

import javax.persistence.*;

/**
 * List of Posts
 *
 * for otherwise, every user had 3 savelist (basics. created when register). {history, likedposts, readlater}
 */

@Entity
@Table(name = "savelists")
public class Savelist {

    public static final String HISTORY =    "HISTORY";
    public static final String READLATER =  "READLATER";
    public static final String LIKEDPOSTS = "LIKEDPOSTS";

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int id;

    public int authorId;

    public String name;

    public long createdTime;

    public Savelist() {}

    public Savelist(int authorId, String name) {
        this.authorId = authorId;
        this.name = name;
        this.createdTime = System.currentTimeMillis();
    }

}
