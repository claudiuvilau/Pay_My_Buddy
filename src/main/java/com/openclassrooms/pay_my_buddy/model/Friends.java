package com.openclassrooms.pay_my_buddy.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "friends")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "users_id_users")
    private int usersIdUsers;

    @Column(name = "buddy")
    private int buddy;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_users")
    private Users users;

    public Friends() {
    }

    public Friends(int id, int usersIdUsers, int buddy, Users users) {
        this.id = id;
        this.usersIdUsers = usersIdUsers;
        this.buddy = buddy;
        this.users = users;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
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