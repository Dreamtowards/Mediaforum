package dev.mediaforum.network.dao.model;

import javax.persistence.*;

/**
 * A Entry/Item corresponding a SavelistId and a PostId
 */

@Entity
@Table(name = "savelist_items")
public class SavelistItem {

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int id;

    public int savelistId;

    public int postId;

    public long addedTime;

    public SavelistItem() { }

    public SavelistItem(int savelistId, int postId) {
        this.savelistId = savelistId;
        this.postId = postId;
        this.addedTime = System.currentTimeMillis();
    }
}
