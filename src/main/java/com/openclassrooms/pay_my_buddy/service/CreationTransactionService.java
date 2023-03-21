package com.openclassrooms.pay_my_buddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.NameTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.Users;

@Service
public class CreationTransactionService {

    @Autowired
    private ServiceTransactionl serviceTransactionl; // instance of object

    @Autowired
    private Transactions transaction;

    @Autowired
    private Transactions transactionEncaissement;

    @Autowired
    private CostsDetailsTransactions costsDetailsTransaction;

    @Autowired
    private CostsDetailsTransactions costsDetailsTransactionEncaissement;

    @Autowired
    private CostsDetailsTransactions costsDetailsTransactionFrais;

    @Autowired
    private NameTransactionsService nameTransactionsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CollectionMoneyService collectionMoneyService;

    public void createTransaction(Users nameUser, String typeTransConnection, String amount) {

        // date du jour de la transaction
        Date dateTransNow = new Date();

        Users nameBuddy = usersService.getUser(typeTransConnection);

        int defineNameTrans = 0;
        // si c'est une adresse email => c'est un envoi vers un ami
        if (typeTransConnection.contains("@")) {
            // name trans = 3 = envoi
            defineNameTrans = 3;
        } else {
            // la valeur par défaut de la table du nom transaction
            defineNameTrans = Integer.parseInt(typeTransConnection);
        }

        transaction = defineNewTransaction(nameUser, dateTransNow);
        costsDetailsTransaction = defineNewCostsDetailsTransaction(transaction, nameUser, nameBuddy, defineNameTrans,
                amount);

        // si nom transaction = 3 envoi

        if (defineNameTrans == 3) {
            // add buddy in transactions
            transactionEncaissement = defineNewTransactionEncaissement(nameBuddy, dateTransNow);
            // add encaissement in table = 4
            defineNameTrans = 4;
            costsDetailsTransactionEncaissement = defineNewDetailsCostsTransactionEncaissement(transactionEncaissement,
                    nameUser, defineNameTrans, amount);
            // add frais
            defineNameTrans = 5; // interest in table is 5
            costsDetailsTransactionFrais = defineNewCostsDetailsFraisTransaction(transaction, nameUser,
                    defineNameTrans, dateTransNow, amount);
        }

        serviceTransactionl.updateTableTransactionsAndCostsDetailsTransactions(transaction, costsDetailsTransaction,
                costsDetailsTransactionFrais,
                transactionEncaissement, costsDetailsTransactionEncaissement);
    }

    private Transactions defineNewTransaction(Users nameUser, Date dateTransNow) {

        transaction = new Transactions();
        transaction.setDateTrans(dateTransNow);
        transaction.setInvoiced(false);
        transaction.setUser(nameUser.getIdUsers());

        return transaction;
    }

    private Transactions defineNewTransactionEncaissement(Users nameBuddy, Date dateTransNow) {

        transactionEncaissement = new Transactions();
        transactionEncaissement.setDateTrans(dateTransNow);
        transactionEncaissement.setInvoiced(false);
        transactionEncaissement.setUser(nameBuddy.getIdUsers());

        return transactionEncaissement;
    }

    private CostsDetailsTransactions defineNewCostsDetailsTransaction(Transactions transactionCreated, Users nameUser,
            Users nameBuddy, int defineNameTrans, String amount) {

        costsDetailsTransaction = new CostsDetailsTransactions();
        costsDetailsTransaction.setAmount(Double.parseDouble(amount));
        if (defineNameTrans == 3) {
            // make to_from_user the buddy
            costsDetailsTransaction.setUsers(nameBuddy);
        } else {
            costsDetailsTransaction.setUsers(nameUser);
        }

        // add costs in transaction
        List<CostsDetailsTransactions> lCostsDetailsTransactions = new ArrayList<>();
        lCostsDetailsTransactions.add(costsDetailsTransaction);
        transactionCreated.setCostsDetailsTransactionsList(lCostsDetailsTransactions);
        costsDetailsTransaction.setTransactions(transactionCreated);

        Optional<NameTransactions> nameTransOpt = recupererNameTypeTransactions(defineNameTrans);
        if (nameTransOpt.isPresent()) {
            NameTransactions nameTrans = nameTransOpt.get();
            costsDetailsTransaction.setTypeTransactions(nameTrans.getTypeTransactions());
            costsDetailsTransaction.setNameTransactions(nameTrans);
        }

        return costsDetailsTransaction;
    }

    private CostsDetailsTransactions defineNewCostsDetailsFraisTransaction(Transactions transactionCreated,
            Users nameUser,
            int defineNameTrans, Date dateTransNow, String amount) {

        costsDetailsTransactionFrais = new CostsDetailsTransactions();
        costsDetailsTransactionFrais.setAmount(findInterestCollectionMoney(dateTransNow) * Double.parseDouble(amount));
        costsDetailsTransactionFrais.setUsers(nameUser);

        // add costs in transaction
        List<CostsDetailsTransactions> lCostsDetailsTransactions = new ArrayList<>();
        lCostsDetailsTransactions.add(costsDetailsTransactionFrais);
        transactionCreated.setCostsDetailsTransactionsList(lCostsDetailsTransactions);
        costsDetailsTransactionFrais.setTransactions(transactionCreated);

        Optional<NameTransactions> nameTransOpt = recupererNameTypeTransactions(defineNameTrans);
        if (nameTransOpt.isPresent()) {
            NameTransactions nameTrans = nameTransOpt.get();
            costsDetailsTransactionFrais.setTypeTransactions(nameTrans.getTypeTransactions());
            costsDetailsTransactionFrais.setNameTransactions(nameTrans);
        }

        return costsDetailsTransactionFrais;

    }

    private double findInterestCollectionMoney(Date dateTransNow) {
        return collectionMoneyService.getCollectionMoneyToDate(dateTransNow);
    }

    private CostsDetailsTransactions defineNewDetailsCostsTransactionEncaissement(Transactions transactionEncaissement,
            Users nameUser, int defineNameTrans, String amount) {

        costsDetailsTransactionEncaissement = new CostsDetailsTransactions();
        costsDetailsTransactionEncaissement.setAmount(Double.parseDouble(amount));
        costsDetailsTransactionEncaissement.setUsers(nameUser);
        costsDetailsTransactionEncaissement.setTransactions(transactionEncaissement);

        Optional<NameTransactions> nameTransOpt = recupererNameTypeTransactions(defineNameTrans);
        if (nameTransOpt.isPresent()) {
            NameTransactions nameTrans = nameTransOpt.get();
            costsDetailsTransactionEncaissement.setTypeTransactions(nameTrans.getTypeTransactions());
            costsDetailsTransactionEncaissement.setNameTransactions(nameTrans);
        }

        return costsDetailsTransactionEncaissement;
    }

    private Optional<NameTransactions> recupererNameTypeTransactions(int defineTypeTrans) {
        return nameTransactionsService.getNameTransactionById(defineTypeTrans);
    }
}
