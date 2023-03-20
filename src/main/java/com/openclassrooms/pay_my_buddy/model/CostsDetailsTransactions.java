package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
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
@Table(name = "costsdetailstransactions")
public class CostsDetailsTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // @Column(name = "trans_id_trans")
    // private int transIdTrans;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "trans_id_trans")
    private Transactions transactions;

    @Column(name = "amount")
    private double amount;

    // @Column(name = "type_trans")
    // private int typeTrans;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "type_trans")
    private TypeTransactions typeTransactions;

    // @Column(name = "name_trans")
    // private int nameTrans;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "name_trans")
    private NameTransactions nameTransactions;

    // @Column(name = "to_from_user")
    // private int toUser;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "to_from_user")
    private Users users;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_trans")
    List<Transactions> transactionsList = new ArrayList<>();

    public TypeTransactions getTypeTransactions() {
        return this.typeTransactions;
    }

    public void setTypeTransactions(TypeTransactions typeTransactions) {
        this.typeTransactions = typeTransactions;
    }

    public NameTransactions getNameTransactions() {
        return this.nameTransactions;
    }

    public void setNameTransactions(NameTransactions nameTransactions) {
        this.nameTransactions = nameTransactions;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Transactions getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public List<Transactions> getTransactionsList() {
        return this.transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
