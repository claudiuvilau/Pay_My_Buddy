package com.openclassrooms.pay_my_buddy.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CollectionMoney")
public class CollectionMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "amount_percentage")
    private Double amountPercentage;

    public CollectionMoney() {
    }

    public CollectionMoney(int id, Date startDate, Date endDate, Double amountPercentage) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amountPercentage = amountPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getAmountPercentage() {
        return amountPercentage;
    }

    public void setAmountPercentage(Double amountPercentage) {
        this.amountPercentage = amountPercentage;
    }

}