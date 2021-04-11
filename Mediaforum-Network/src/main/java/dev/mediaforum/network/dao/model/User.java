package dev.mediaforum.network.dao.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int id;

    public String email;

    public String username;

    public String passwordDigest;  // lowCase(toHexStr(sha256(pwd)));

    public long registerTime;

    public String avatarUrl;

}
