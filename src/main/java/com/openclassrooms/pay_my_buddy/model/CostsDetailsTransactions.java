package com.openclassrooms.pay_my_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "costsdetailstransactions")
public class CostsDetailsTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "trans_id_trans")
    private int transIdTrans;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type_trans")
    private int typeTrans;

    @Column(name = "name_trans")
    private int nameTrans;

    public CostsDetailsTransactions() {
    }

    public CostsDetailsTransactions(int id, int transIdTrans, double amount, int typeTrans, int nameTrans) {
        this.id = id;
        this.transIdTrans = transIdTrans;
        this.amount = amount;
        this.typeTrans = typeTrans;
        this.nameTrans = nameTrans;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransIdTrans() {
        return this.transIdTrans;
    }

    public void setTransIdTrans(int transIdTrans) {
        this.transIdTrans = transIdTrans;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTypeTrans() {
        return this.typeTrans;
    }

    public void setTypeTrans(int typeTrans) {
        this.typeTrans = typeTrans;
    }

    public int getNameTrans() {
        return this.nameTrans;
    }

    public void setNameTrans(int nameTrans) {
        this.nameTrans = nameTrans;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", transIdTrans='" + getTransIdTrans() + "'" +
                ", amount='" + getAmount() + "'" +
                ", typeTrans='" + getTypeTrans() + "'" +
                ", nameTrans='" + getNameTrans() + "'" +
                "}";
    }

}
