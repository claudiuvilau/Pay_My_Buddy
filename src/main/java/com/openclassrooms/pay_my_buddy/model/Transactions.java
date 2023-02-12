package com.openclassrooms.pay_my_buddy.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transactions")
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

    public Transactions() {
    }

    public Transactions(int idTrans, Date dateTrans, int user, Boolean invoiced) {
        this.idTrans = idTrans;
        this.dateTrans = dateTrans;
        this.user = user;
        this.invoiced = invoiced;
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

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Boolean getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(Boolean invoiced) {
        this.invoiced = invoiced;
    }

}