package com.openclassrooms.pay_my_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name = "friendsnetwork")
public class FriendsNetwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "users_id_users")
    private int usersIdUsers;

    @Column(name = "buddy")
    private int buddy;

    public FriendsNetwork() {
    }

    public FriendsNetwork(int id, int usersIdUsers, int buddy) {
        this.id = id;
        this.usersIdUsers = usersIdUsers;
        this.buddy = buddy;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsersIdUsers() {
        return this.usersIdUsers;
    }

    public void setUsersIdUsers(int usersIdUsers) {
        this.usersIdUsers = usersIdUsers;
    }

    public int getBuddy() {
        return this.buddy;
    }

    public void setBuddy(int buddy) {
        this.buddy = buddy;
    }

}