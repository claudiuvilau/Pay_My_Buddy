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
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trans")
    private int idTrans;

    @Column(name = "date_trans")
    private Date dateTrans;

    @Column(name = "user")
    private int user;

    @Column(name = "invoiced")
    private Boolean invoiced;

    @OneToMany
    @JoinColumn(name = "trans_id_trans")
    List<CostsDetailsTransactions> costsDetailsTransactionsList = new ArrayList<>();

    public int getUser() {
        return this.user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Boolean isInvoiced() {
        return this.invoiced;
    }

    public List<CostsDetailsTransactions> getCostsDetailsTransactionsList() {
        return this.costsDetailsTransactionsList;
    }

    public void setCostsDetailsTransactionsList(List<CostsDetailsTransactions> costsDetailsTransactionsList) {
        this.costsDetailsTransactionsList = costsDetailsTransactionsList;
    }

    public int getIdTrans() {
        return idTrans;
    }

    public void setIdTrans(int idTrans) {
        this.idTrans = idTrans;
    }

    public Date getDateTrans() {
        return dateTrans;
    }

    public void setDateTrans(Date dateTrans) {
        this.dateTrans = dateTrans;
    }

    public Boolean getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(Boolean invoiced) {
        this.invoiced = invoiced;
    }

}