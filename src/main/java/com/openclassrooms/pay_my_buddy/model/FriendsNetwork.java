package com.openclassrooms.pay_my_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "FriendsNetwork")
public class FriendsNetwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user")
    private int user;

    @Column(name = "buddy")
    private int buddy;

    public FriendsNetwork() {
    }

    public FriendsNetwork(int id, int user, int buddy) {
        this.id = id;
        this.user = user;
        this.buddy = buddy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getBuddy() {
        return buddy;
    }

    public void setBuddy(int buddy) {
        this.buddy = buddy;
    }

}