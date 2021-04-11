package dev.mediaforum.network.dao.model;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int id;

    public int authorId;

    public int postId;

    public int parentCommentId;

    public String content;  // long str

    public long postTime;

    public Comment() { }

    public Comment(int authorId, int postId, int parentCommentId, String content) {
        this.authorId = authorId;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.content = content;
        this.postTime = System.currentTimeMillis();
    }
}
