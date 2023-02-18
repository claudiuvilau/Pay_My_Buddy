package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "users", joinColumns = @JoinColumn(name = "users_id_users"), inverseJoinColumns = @JoinColumn(name = "id_users"))
    private List<Users> usersList = new ArrayList<>();

    public void addUser(Users users) {
        usersList.add(users);
        users.getFriendsNetworksManyToMany().add(this);
    }

    public void removeUser(Users users) {
        usersList.remove(users);
        users.getFriendsNetworksManyToMany().remove(this);
    }

    public FriendsNetwork() {
    }

    public FriendsNetwork(int id, int usersIdUsers, int buddy, List<Users> usersList) {
        this.id = id;
        this.usersIdUsers = usersIdUsers;
        this.buddy = buddy;
        this.usersList = usersList;
    }

    public List<Users> getUsersList() {
        return this.usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
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