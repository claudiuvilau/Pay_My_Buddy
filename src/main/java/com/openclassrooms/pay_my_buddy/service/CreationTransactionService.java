package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Descriptions;
import com.openclassrooms.pay_my_buddy.model.NameTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.TypeTransactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreationTransactionService {

  @Autowired
  private ServiceTransactional serviceTransactional; // instance of object

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
  private NameTransactions nameTransactions;

  @Autowired
  private TypeTransactions typeTransactions;

  @Autowired
  private TypeTransactionsService typeTransactionsService;

  @Autowired
  private UsersService usersService;

  @Autowired
  private CollectionMoneyService collectionMoneyService;

  @Autowired
  private DescriptionsService descriptionsService;

  @Autowired
  private Descriptions description;

  @Autowired
  private Descriptions descriptionFrais;

  public CreationTransactionService() {}

  public CreationTransactionService(
    ServiceTransactional serviceTransactional,
    Transactions transaction,
    Transactions transactionEncaissement,
    CostsDetailsTransactions costsDetailsTransaction,
    CostsDetailsTransactions costsDetailsTransactionEncaissement,
    CostsDetailsTransactions costsDetailsTransactionFrais,
    NameTransactionsService nameTransactionsService,
    NameTransactions nameTransactions,
    TypeTransactions typeTransactions,
    TypeTransactionsService typeTransactionsService,
    UsersService usersService,
    CollectionMoneyService collectionMoneyService,
    DescriptionsService descriptionsService,
    Descriptions description,
    Descriptions descriptionFrais
  ) {
    this.serviceTransactional = serviceTransactional;
    this.transaction = transaction;
    this.transactionEncaissement = transactionEncaissement;
    this.costsDetailsTransaction = costsDetailsTransaction;
    this.costsDetailsTransactionEncaissement =
      costsDetailsTransactionEncaissement;
    this.costsDetailsTransactionFrais = costsDetailsTransactionFrais;
    this.nameTransactionsService = nameTransactionsService;
    this.nameTransactions = nameTransactions;
    this.typeTransactions = typeTransactions;
    this.typeTransactionsService = typeTransactionsService;
    this.usersService = usersService;
    this.collectionMoneyService = collectionMoneyService;
    this.descriptionsService = descriptionsService;
    this.description = description;
    this.descriptionFrais = descriptionFrais;
  }

  public ServiceTransactional getServiceTransactional() {
    return this.serviceTransactional;
  }

  public void setServiceTransactionl(
    ServiceTransactional serviceTransactional
  ) {
    this.serviceTransactional = serviceTransactional;
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

  public void setCostsDetailsTransaction(
    CostsDetailsTransactions costsDetailsTransaction
  ) {
    this.costsDetailsTransaction = costsDetailsTransaction;
  }

  public CostsDetailsTransactions getCostsDetailsTransactionEncaissement() {
    return this.costsDetailsTransactionEncaissement;
  }

  public void setCostsDetailsTransactionEncaissement(
    CostsDetailsTransactions costsDetailsTransactionEncaissement
  ) {
    this.costsDetailsTransactionEncaissement =
      costsDetailsTransactionEncaissement;
  }

  public CostsDetailsTransactions getCostsDetailsTransactionFrais() {
    return this.costsDetailsTransactionFrais;
  }

  public void setCostsDetailsTransactionFrais(
    CostsDetailsTransactions costsDetailsTransactionFrais
  ) {
    this.costsDetailsTransactionFrais = costsDetailsTransactionFrais;
  }

  public NameTransactionsService getNameTransactionsService() {
    return this.nameTransactionsService;
  }

  public void setNameTransactionsService(
    NameTransactionsService nameTransactionsService
  ) {
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

  public void setCollectionMoneyService(
    CollectionMoneyService collectionMoneyService
  ) {
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

  public Descriptions getDescriptionFrais() {
    return this.descriptionFrais;
  }

  public void setDescriptionFrais(Descriptions descriptionFrais) {
    this.descriptionFrais = descriptionFrais;
  }

  public void setServiceTransactional(
    ServiceTransactional serviceTransactional
  ) {
    this.serviceTransactional = serviceTransactional;
  }

  public NameTransactions getNameTransactions() {
    return this.nameTransactions;
  }

  public void setNameTransactions(NameTransactions nameTransactions) {
    this.nameTransactions = nameTransactions;
  }

  public TypeTransactions getTypeTransactions() {
    return this.typeTransactions;
  }

  public void setTypeTransactions(TypeTransactions typeTransactions) {
    this.typeTransactions = typeTransactions;
  }

  public TypeTransactionsService getTypeTransactionsService() {
    return this.typeTransactionsService;
  }

  public void setTypeTransactionsService(
    TypeTransactionsService typeTransactionsService
  ) {
    this.typeTransactionsService = typeTransactionsService;
  }

  public boolean createTransaction(
    Users nameUser,
    String typeTransConnection,
    String amount,
    String descriptionToAdd
  ) {
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

    defineNewTransactionsAndCosts(
      defineNameTrans,
      nameUser,
      nameBuddy,
      dateTransNow,
      amount,
      descriptionToAdd
    );

    serviceTransactional.updateTableTransactionsAndCostsDetailsTransactions(
      transaction,
      costsDetailsTransaction,
      costsDetailsTransactionFrais,
      transactionEncaissement,
      costsDetailsTransactionEncaissement
    );
    return serviceTransactional.getTransactionOk();
  }

  private void defineNewTransactionsAndCosts(
    int defineNameTrans,
    Users nameUser,
    Users nameBuddy,
    Date dateTransNow,
    String amount,
    String descriptionToAdd
  ) {
    int defineTypeTrans;

    transaction = defineNewTransactions();
    transaction.setDateTrans(dateTransNow);
    transaction.setUser(nameUser.getIdUsers());
    transaction.setInvoiced(false);

    //List<CostsDetailsTransactions> listCostsDetailsTransactions = new ArrayList<>();

    // if send to buddy
    if (defineNameTrans == 3) {
      defineTypeTrans = 2; // debit
      defineTransactionDetail(
        nameBuddy,
        transaction,
        amount,
        descriptionToAdd,
        defineNameTrans,
        defineTypeTrans
      );

      //listCostsDetailsTransactions.add(costsDetailsTransaction);

      defineNameTrans = 5; // frais
      defineTypeTrans = 2; // debit
      descriptionFrais = defineNewDescription();
      costsDetailsTransactionFrais = defineNewCostsDetailsTransactions();
      defineTransactionFrais(
        nameUser,
        transaction,
        dateTransNow,
        amount,
        defineNameTrans,
        defineTypeTrans
      );

      //listCostsDetailsTransactions.add(costsDetailsTransactionFrais);

      //transaction.setCostsDetailsTransactionsList(listCostsDetailsTransactions);

      transactionEncaissement = defineNewTransactions();
      transactionEncaissement.setDateTrans(dateTransNow);
      transactionEncaissement.setUser(nameBuddy.getIdUsers());
      transactionEncaissement.setInvoiced(false);

      defineNameTrans = 4; // encaissement
      defineTypeTrans = 1; // credit
      costsDetailsTransactionEncaissement = defineNewCostsDetailsTransactions();
      defineTransactionDetailEncaissement(
        nameUser,
        transactionEncaissement,
        amount,
        descriptionToAdd,
        defineNameTrans,
        defineTypeTrans
      );
      //listCostsDetailsTransactions = new ArrayList<>();
      //listCostsDetailsTransactions.add(costsDetailsTransactionEncaissement);
      //transactionEncaissement.setCostsDetailsTransactionsList(
      //listCostsDetailsTransactions
      //);
    } else if (defineNameTrans == 1 || defineNameTrans == 4) {
      defineTypeTrans = 1; // credit
      defineTransactionDetail(
        nameUser,
        transaction,
        amount,
        descriptionToAdd,
        defineNameTrans,
        defineTypeTrans
      );
      //listCostsDetailsTransactions.add(costsDetailsTransaction);
      //transaction.setCostsDetailsTransactionsList(listCostsDetailsTransactions);
    } else if (defineNameTrans == 2) {
      defineTypeTrans = 2; // debit
      defineTransactionDetail(
        nameUser,
        transaction,
        amount,
        descriptionToAdd,
        defineNameTrans,
        defineTypeTrans
      );
      //listCostsDetailsTransactions.add(costsDetailsTransaction);
      //transaction.setCostsDetailsTransactionsList(listCostsDetailsTransactions);
    }
  }

  private void defineTransactionDetail(
    Users nameUserOrnameBuddy,
    Transactions transaction,
    String amount,
    String descriptionToAdd,
    int defineNameTrans,
    int defineTypeTrans
  ) {
    //List<Transactions> transactionsList = new ArrayList<>();
    //transactionsList.add(transaction);
    //costsDetailsTransaction.setTransactionsList(transactionsList);

    costsDetailsTransaction = defineNewCostsDetailsTransactions();
    costsDetailsTransaction.setTransactions(transaction);
    costsDetailsTransaction.setAmount(Double.parseDouble(amount));
    // définir name transaction
    recupererNameTransactions(defineNameTrans);
    costsDetailsTransaction.setNameTransactions(nameTransactions);
    // définir type transaction
    recupererTypeTransactions(defineTypeTrans);
    costsDetailsTransaction.setTypeTransactions(typeTransactions);

    costsDetailsTransaction.setUsers(nameUserOrnameBuddy);
    if (defineNameTrans != 3) {
      descriptionToAdd = nameTransactions.getNameTrans().toLowerCase();
    }
    // verify if the description exist in the table description
    Descriptions descriptionFinded = descriptionsService.getDescription(
      descriptionToAdd
    );
    description = defineNewDescription();
    if (descriptionFinded != null) {
      costsDetailsTransaction.setDescriptions(descriptionFinded);
    } else {
      description.setDescription(descriptionToAdd);
      costsDetailsTransaction.setDescriptions(description);
    }
  }

  private void defineTransactionFrais(
    Users nameUser,
    Transactions transaction,
    Date dateTransNow,
    String amount,
    int defineNameTrans,
    int defineTypeTrans
  ) {
    //List<Transactions> transactionsList = new ArrayList<>();
    //transactionsList.add(transaction);
    //costsDetailsTransactionFrais.setTransactionsList(transactionsList);

    costsDetailsTransactionFrais = defineNewCostsDetailsTransactions();
    costsDetailsTransactionFrais.setTransactions(transaction);
    costsDetailsTransactionFrais.setAmount(
      findInterestCollectionMoney(dateTransNow) * Double.parseDouble(amount)
    );
    // définir name transaction : 5 = frais
    recupererNameTransactions(defineNameTrans);
    costsDetailsTransactionFrais.setNameTransactions(nameTransactions);
    // définir type transaction : 2 = debit
    recupererTypeTransactions(defineTypeTrans);
    costsDetailsTransactionFrais.setTypeTransactions(typeTransactions);

    costsDetailsTransactionFrais.setUsers(nameUser);
    // description - the same like name transaction to lower case
    String descriptionToAdd = nameTransactions.getNameTrans().toLowerCase();
    // verify if the description exist in the table description
    Descriptions descriptionFinded = descriptionsService.getDescription(
      descriptionToAdd
    );
    descriptionFrais = defineNewDescription();
    if (descriptionFinded != null) {
      costsDetailsTransactionFrais.setDescriptions(descriptionFinded);
    } else {
      descriptionFrais.setDescription(descriptionToAdd);
      costsDetailsTransactionFrais.setDescriptions(descriptionFrais);
    }
  }

  private void defineTransactionDetailEncaissement(
    Users nameBuddy,
    Transactions transactionEncaissement,
    String amount,
    String descriptionToAdd,
    int defineNameTrans,
    int defineTypeTrans
  ) {
    //List<Transactions> transactionsList = new ArrayList<>();
    //transactionsList.add(transaction);
    //costsDetailsTransactionEncaissement.setTransactionsList(transactionsList);

    costsDetailsTransactionEncaissement.setTransactions(
      transactionEncaissement
    );
    costsDetailsTransactionEncaissement.setAmount(Double.parseDouble(amount));
    // définir name transaction
    recupererNameTransactions(defineNameTrans);
    costsDetailsTransactionEncaissement.setNameTransactions(nameTransactions);
    // définir type transaction : 1 = credit
    recupererTypeTransactions(defineTypeTrans);
    costsDetailsTransactionEncaissement.setTypeTransactions(typeTransactions);

    costsDetailsTransactionEncaissement.setUsers(nameBuddy);

    // verify if the description exist in the table description
    Descriptions descriptionFinded = descriptionsService.getDescription(
      descriptionToAdd
    );
    if (descriptionFinded != null) {
      //description.setDescription(descriptionFinded.getDescription());
      costsDetailsTransactionEncaissement.setDescriptions(descriptionFinded);
    } else {
      description.setDescription(descriptionToAdd);
      costsDetailsTransactionEncaissement.setDescriptions(description);
    }
  }

  private void recupererNameTransactions(int defineTypeTrans) {
    Optional<NameTransactions> nameTransOpt = nameTransactionsService.getNameTransactionById(
      defineTypeTrans
    );
    if (nameTransOpt.isPresent()) {
      nameTransactions = defineNewNameTransactions();
      nameTransactions = nameTransOpt.get();
    }
  }

  private void recupererTypeTransactions(int defineTypeTrans) {
    Optional<TypeTransactions> typeTransOpt = typeTransactionsService.getTypeTransactionById(
      defineTypeTrans
    );
    if (typeTransOpt.isPresent()) {
      typeTransactions = defineNewTypeTransactions();
      typeTransactions = typeTransOpt.get();
    }
  }

  private double findInterestCollectionMoney(Date dateTransNow) {
    return collectionMoneyService.getCollectionMoneyToDate(dateTransNow);
  }

  private Descriptions defineNewDescription() {
    return new Descriptions();
  }

  private Transactions defineNewTransactions() {
    return new Transactions();
  }

  private CostsDetailsTransactions defineNewCostsDetailsTransactions() {
    return new CostsDetailsTransactions();
  }

  private NameTransactions defineNewNameTransactions() {
    return new NameTransactions();
  }

  private TypeTransactions defineNewTypeTransactions() {
    return new TypeTransactions();
  }
}
