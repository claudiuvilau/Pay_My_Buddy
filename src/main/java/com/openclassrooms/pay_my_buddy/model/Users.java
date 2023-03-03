package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_users")
    private int idUsers;

    @Column(name = "id_email")
    private String idEmail;

    @Column(name = "name_user")
    private String nameUser;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "password")
    private String password;

    @Column(name = "role_id")
    private int roleId;

    @OneToMany
    @JoinColumn(name = "buddy")
    List<Friends> buddy = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "users_id_users")
    List<Friends> friends = new ArrayList<>();

    public List<Friends> getBuddy() {
        return this.buddy;
    }

    public void setBuddy(List<Friends> buddy) {
        this.buddy = buddy;
    }

    public List<Friends> getFriends() {
        return this.friends;
    }

    public void setFriends(List<Friends> friends) {
        this.friends = friends;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public String getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(String idEmail) {
        this.idEmail = idEmail;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}