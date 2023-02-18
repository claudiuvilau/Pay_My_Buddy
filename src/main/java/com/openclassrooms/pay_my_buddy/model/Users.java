package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id_users")
    List<FriendsNetwork> friendsNetworks = new ArrayList<>();

    @OneToMany(mappedBy = "buddy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FriendsNetwork> friendsBuddy = new ArrayList<>();

    @ManyToMany(mappedBy = "usersList", cascade = CascadeType.ALL)
    private List<FriendsNetwork> friendsNetworksManyToMany = new ArrayList<>();

    public void addFriend(FriendsNetwork comment) {
        friendsBuddy.add(comment);
        comment.setUsersList((List<Users>) this);
    }

    public void removeFriend(FriendsNetwork comment) {
        friendsBuddy.remove(comment);
        comment.setUsersList(null);
    }

    public Users() {
    }

    public Users(int idUsers, String idEmail, String nameUser, String firstName, Date birthDate,
            List<FriendsNetwork> friendsNetworks, List<FriendsNetwork> friendsBuddy) {
        this.idUsers = idUsers;
        this.idEmail = idEmail;
        this.nameUser = nameUser;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.friendsNetworks = friendsNetworks;
        this.friendsBuddy = friendsBuddy;
    }

    public List<FriendsNetwork> getFriendsNetworksManyToMany() {
        return this.friendsNetworksManyToMany;
    }

    public void setFriendsNetworksManyToMany(List<FriendsNetwork> friendsNetworksManyToMany) {
        this.friendsNetworksManyToMany = friendsNetworksManyToMany;
    }

    public List<FriendsNetwork> getFriendsBuddy() {
        return this.friendsBuddy;
    }

    public void setFriendsBuddy(List<FriendsNetwork> friendsBuddy) {
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

    public List<FriendsNetwork> getFriendsNetworks() {
        return friendsNetworks;
    }

    public void setFriendsNetworks(List<FriendsNetwork> friendsNetworks) {
        this.friendsNetworks = friendsNetworks;
    }

}