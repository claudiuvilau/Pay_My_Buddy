package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Component
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

    @OneToMany
    @JoinColumn(name = "users_id_users")
    List<Friends> friends = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Roles role;

    @OneToMany
    @JoinColumn(name = "user")
    List<Transactions> transactionsList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "to_from_user")
    List<CostsDetailsTransactions> costsDetailsTransactions = new ArrayList<>();


    public Users() {
    }

    public Users(int idUsers, String idEmail, String nameUser, String firstName, Date birthDate, String password, List<Friends> friends, Roles role, List<Transactions> transactionsList, List<CostsDetailsTransactions> costsDetailsTransactions) {
        this.idUsers = idUsers;
        this.idEmail = idEmail;
        this.nameUser = nameUser;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.password = password;
        this.friends = friends;
        this.role = role;
        this.transactionsList = transactionsList;
        this.costsDetailsTransactions = costsDetailsTransactions;
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
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Friends> getFriends() {
        return friends;
    }

    public void setFriends(List<Friends> friends) {
        this.friends = friends;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public List<CostsDetailsTransactions> getCostsDetailsTransactions() {
        return costsDetailsTransactions;
    }

    public void setCostsDetailsTransactions(List<CostsDetailsTransactions> costsDetailsTransactions) {
        this.costsDetailsTransactions = costsDetailsTransactions;
    }
}