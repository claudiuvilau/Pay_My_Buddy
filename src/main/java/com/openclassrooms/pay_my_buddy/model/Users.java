package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch =
    // FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL) // pour pouvoir supprimer des données de la table Friends
    @JoinColumn(name = "users_id_users")
    List<Friends> friends = new ArrayList<>();

    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch =
    // FetchType.EAGER)
    // @JoinColumn(name = "buddy")
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Friends> friendsBuddy = new ArrayList<>();

    public Users() {
    }

    public Users(int idUsers, String idEmail, String nameUser, String firstName, Date birthDate, List<Friends> friends,
            List<Friends> friendsBuddy) {
        this.idUsers = idUsers;
        this.idEmail = idEmail;
        this.nameUser = nameUser;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.friends = friends;
        this.friendsBuddy = friendsBuddy;
    }

    public List<Friends> getFriendsBuddy() {
        return this.friendsBuddy;
    }

    public void setFriendsBuddy(List<Friends> friendsBuddy) {
        this.friendsBuddy = friendsBuddy;
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

    public List<Friends> getFriends() {
        return friends;
    }

    public void setFriends(List<Friends> friends) {
        this.friends = friends;
    }

}