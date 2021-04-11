package dev.mediaforum.network.dao.model;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int id;

    public int authorId;

    public String title;

    public String content;  // long str

    public long postTime;

    public Post() { }

    public Post(int authorId, String title, String content) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.postTime = System.currentTimeMillis();
    }
}
