package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
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
@Table(name = "nametransactions")
public class NameTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_name_trans")
    private int idNameTrans;

    @Column(name = "name_trans")
    private String nameTrans;

    @OneToMany
    @JoinColumn(name = "name_trans")
    List<CostsDetailsTransactions> costsDetailsTransactions = new ArrayList<>();

    public int getIdNameTrans() {
        return this.idNameTrans;
    }

    public void setIdNameTrans(int idNameTrans) {
        this.idNameTrans = idNameTrans;
    }

    public List<CostsDetailsTransactions> getCostsDetailsTransactions() {
        return this.costsDetailsTransactions;
    }

    public void setCostsDetailsTransactions(List<CostsDetailsTransactions> costsDetailsTransactions) {
        this.costsDetailsTransactions = costsDetailsTransactions;
    }

    public String getNameTrans() {
        return this.nameTrans;
    }

    public void setNameTrans(String nameTrans) {
        this.nameTrans = nameTrans;
    }

}
