package com.openclassrooms.pay_my_buddy.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date_inv")
    private Date dateInv;

    @Column(name = "transactions_id_transaction")
    private String transactionsIdTransaction;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    public Invoices() {
    }

    public Invoices(int id, Date dateInv, String transactionsIdTransaction, String invoiceNumber) {
        this.id = id;
        this.dateInv = dateInv;
        this.transactionsIdTransaction = transactionsIdTransaction;
        this.invoiceNumber = invoiceNumber;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateInv() {
        return this.dateInv;
    }

    public void setDateInv(Date dateInv) {
        this.dateInv = dateInv;
    }

    public String getTransactionsIdTransaction() {
        return this.transactionsIdTransaction;
    }

    public void setTransactionsIdTransaction(String transactionsIdTransaction) {
        this.transactionsIdTransaction = transactionsIdTransaction;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", dateInv='" + getDateInv() + "'" +
                ", transactionsIdTransaction='" + getTransactionsIdTransaction() + "'" +
                ", invoiceNumber='" + getInvoiceNumber() + "'" +
                "}";
    }

}
