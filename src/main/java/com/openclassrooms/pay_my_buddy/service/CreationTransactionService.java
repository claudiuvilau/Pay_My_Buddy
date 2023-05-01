package com.openclassrooms.pay_my_buddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Descriptions;
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

    @Autowired
    private DescriptionsService descriptionsService;

    @Autowired
    private Descriptions description;

    public CreationTransactionService() {
    }

    public CreationTransactionService(ServiceTransactionl serviceTransactionl, Transactions transaction,
            Transactions transactionEncaissement, CostsDetailsTransactions costsDetailsTransaction,
            CostsDetailsTransactions costsDetailsTransactionEncaissement,
            CostsDetailsTransactions costsDetailsTransactionFrais, NameTransactionsService nameTransactionsService,
            UsersService usersService, CollectionMoneyService collectionMoneyService,
            DescriptionsService descriptionsService, Descriptions description) {
        this.serviceTransactionl = serviceTransactionl;
        this.transaction = transaction;
        this.transactionEncaissement = transactionEncaissement;
        this.costsDetailsTransaction = costsDetailsTransaction;
        this.costsDetailsTransactionEncaissement = costsDetailsTransactionEncaissement;
        this.costsDetailsTransactionFrais = costsDetailsTransactionFrais;
        this.nameTransactionsService = nameTransactionsService;
        this.usersService = usersService;
        this.collectionMoneyService = collectionMoneyService;
        this.descriptionsService = descriptionsService;
        this.description = description;
    }

    public ServiceTransactionl getServiceTransactionl() {
        return this.serviceTransactionl;
    }

    public void setServiceTransactionl(ServiceTransactionl serviceTransactionl) {
        this.serviceTransactionl = serviceTransactionl;
    }

    public Transactions getTransaction() {
        return this.transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }

    public Transactions getTransactionEncaissement() {
        return this.transactionEncaissement;
    }

    public void setTransactionEncaissement(Transactions transactionEncaissement) {
        this.transactionEncaissement = transactionEncaissement;
    }

    public CostsDetailsTransactions getCostsDetailsTransaction() {
        return this.costsDetailsTransaction;
    }

    public void setCostsDetailsTransaction(CostsDetailsTransactions costsDetailsTransaction) {
        this.costsDetailsTransaction = costsDetailsTransaction;
    }

    public CostsDetailsTransactions getCostsDetailsTransactionEncaissement() {
        return this.costsDetailsTransactionEncaissement;
    }

    public void setCostsDetailsTransactionEncaissement(CostsDetailsTransactions costsDetailsTransactionEncaissement) {
        this.costsDetailsTransactionEncaissement = costsDetailsTransactionEncaissement;
    }

    public CostsDetailsTransactions getCostsDetailsTransactionFrais() {
        return this.costsDetailsTransactionFrais;
    }

    public void setCostsDetailsTransactionFrais(CostsDetailsTransactions costsDetailsTransactionFrais) {
        this.costsDetailsTransactionFrais = costsDetailsTransactionFrais;
    }

    public NameTransactionsService getNameTransactionsService() {
        return this.nameTransactionsService;
    }

    public void setNameTransactionsService(NameTransactionsService nameTransactionsService) {
        this.nameTransactionsService = nameTransactionsService;
    }

    public UsersService getUsersService() {
        return this.usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public CollectionMoneyService getCollectionMoneyService() {
        return this.collectionMoneyService;
    }

    public void setCollectionMoneyService(CollectionMoneyService collectionMoneyService) {
        this.collectionMoneyService = collectionMoneyService;
    }

    public DescriptionsService getDescriptionsService() {
        return this.descriptionsService;
    }

    public void setDescriptionsService(DescriptionsService descriptionsService) {
        this.descriptionsService = descriptionsService;
    }

    public Descriptions getDescription() {
        return this.description;
    }

    public void setDescription(Descriptions description) {
        this.description = description;
    }

    public void createTransaction(Users nameUser, String typeTransConnection, String amount, String descriptionToAdd) {

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
        // si nom transaction = 3 envoi, add description
        if (defineNameTrans == 3) {
            description = defineNewDescription(descriptionToAdd);
        }
        costsDetailsTransaction = defineNewCostsDetailsTransaction(transaction, nameUser, nameBuddy, defineNameTrans,
                amount, description);

        // si nom transaction = 3 envoi
        if (defineNameTrans == 3) {
            // add buddy in transactions
            transactionEncaissement = defineNewTransactionEncaissement(nameBuddy, dateTransNow);
            // add encaissement in table = 4
            defineNameTrans = 4;
            costsDetailsTransactionEncaissement = defineNewDetailsCostsTransactionEncaissement(transactionEncaissement,
                    nameUser, defineNameTrans, amount, description);
            // add frais
            defineNameTrans = 5; // interest in table is 5
            costsDetailsTransactionFrais = defineNewCostsDetailsFraisTransaction(transaction, nameUser,
                    defineNameTrans, dateTransNow, amount);
        }

        serviceTransactionl.updateTableTransactionsAndCostsDetailsTransactions(transaction, costsDetailsTransaction,
                costsDetailsTransactionFrais, transactionEncaissement, costsDetailsTransactionEncaissement);
    }

    private Descriptions defineNewDescription(String descriptionToAdd) {

        description = new Descriptions();
        description.setDescription(descriptionToAdd);
        return description;
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
            Users nameBuddy, int defineNameTrans, String amount, Descriptions description) {

        costsDetailsTransaction = new CostsDetailsTransactions();
        costsDetailsTransaction.setAmount(Double.parseDouble(amount));
        if (defineNameTrans == 3) {
            // make to_from_user the buddy
            costsDetailsTransaction.setUsers(nameBuddy);
            // add description
            costsDetailsTransaction.setDescriptions(description);
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
            Users nameUser, int defineNameTrans, String amount, Descriptions descriptionAdded) {

        costsDetailsTransactionEncaissement = new CostsDetailsTransactions();
        costsDetailsTransactionEncaissement.setAmount(Double.parseDouble(amount));
        costsDetailsTransactionEncaissement.setUsers(nameUser);
        costsDetailsTransactionEncaissement.setTransactions(transactionEncaissement);
        if (defineNameTrans == 4) {
            // add description
            costsDetailsTransactionEncaissement.setDescriptions(descriptionAdded);
        }

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
