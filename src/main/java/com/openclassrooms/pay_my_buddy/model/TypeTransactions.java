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
@Table(name = "typetransactions")
public class TypeTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_trans")
    private int idTypeTrans;

    @Column(name = "nom_type_trans")
    private String nomTypeTrans;

    @OneToMany
    @JoinColumn(name = "type_trans")
    List<CostsDetailsTransactions> costsDetailsTransactions = new ArrayList<>();

    public int getIdTypeTrans() {
        return idTypeTrans;
    }

    public void setIdTypeTrans(int idTypeTrans) {
        this.idTypeTrans = idTypeTrans;
    }

    public List<CostsDetailsTransactions> getCostsDetailsTransactions() {
        return this.costsDetailsTransactions;
    }

    public void setCostsDetailsTransactions(List<CostsDetailsTransactions> costsDetailsTransactions) {
        this.costsDetailsTransactions = costsDetailsTransactions;
    }

    public String getNomTypeTrans() {
        return this.nomTypeTrans;
    }

    public void setNomTypeTrans(String nomTypeTrans) {
        this.nomTypeTrans = nomTypeTrans;
    }

}