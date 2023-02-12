package com.openclassrooms.pay_my_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TypeTransactions")
public class TypeTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_trans")
    private int idTypeTrans;

    @Column(name = "type_trans")
    private String typeTrans;

    public TypeTransactions() {
    }

    public TypeTransactions(int idTypeTrans, String typeTrans) {
        this.idTypeTrans = idTypeTrans;
        this.typeTrans = typeTrans;
    }

    public int getIdTypeTrans() {
        return idTypeTrans;
    }

    public void setIdTypeTrans(int idTypeTrans) {
        this.idTypeTrans = idTypeTrans;
    }

    public String getTypeTrans() {
        return typeTrans;
    }

    public void setTypeTrans(String typeTrans) {
        this.typeTrans = typeTrans;
    }

}