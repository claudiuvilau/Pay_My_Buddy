package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    // @Column(name = "buddy")
    // private int buddy;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buddy")
    private Users users;

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

}