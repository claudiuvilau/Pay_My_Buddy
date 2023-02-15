package com.openclassrooms.pay_my_buddy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public NameTransactions() {
    }

    public NameTransactions(int idNameTrans, String nameTrans) {
        this.idNameTrans = idNameTrans;
        this.nameTrans = nameTrans;
    }

    public int getIdNameTrans() {
        return this.idNameTrans;
    }

    public void setIdNameTrans(int idNameTrans) {
        this.idNameTrans = idNameTrans;
    }

    public String getNameTrans() {
        return this.nameTrans;
    }

    public void setNameTrans(String nameTrans) {
        this.nameTrans = nameTrans;
    }

    @Override
    public String toString() {
        return "{" +
                " idNameTrans='" + getIdNameTrans() + "'" +
                ", nameTrans='" + getNameTrans() + "'" +
                "}";
    }

}
