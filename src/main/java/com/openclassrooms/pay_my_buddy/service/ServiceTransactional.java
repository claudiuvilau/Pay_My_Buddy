package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ServiceTransactional {

  private boolean transactionOk;

  @Autowired
  private TransactionsService transactionsService; // instance of object

  @Autowired
  private CostsDetailsTransactionsService costsDetailsTransactionsService; // instance of object

  public ServiceTransactional() {}

  public ServiceTransactional(
    boolean transactionOk,
    TransactionsService transactionsService,
    CostsDetailsTransactionsService costsDetailsTransactionsService
  ) {
    this.transactionOk = transactionOk;
    this.transactionsService = transactionsService;
    this.costsDetailsTransactionsService = costsDetailsTransactionsService;
  }

  public boolean isTransactionOk() {
    return this.transactionOk;
  }

  public boolean getTransactionOk() {
    return this.transactionOk;
  }

  public void setTransactionOk(boolean transactionOk) {
    this.transactionOk = transactionOk;
  }

  public TransactionsService getTransactionsService() {
    return this.transactionsService;
  }

  public void setTransactionsService(TransactionsService transactionsService) {
    this.transactionsService = transactionsService;
  }

  public CostsDetailsTransactionsService getCostsDetailsTransactionsService() {
    return this.costsDetailsTransactionsService;
  }

  public void setCostsDetailsTransactionsService(
    CostsDetailsTransactionsService costsDetailsTransactionsService
  ) {
    this.costsDetailsTransactionsService = costsDetailsTransactionsService;
  }

  public void updateTableTransactionsAndCostsDetailsTransactions(
    Transactions transaction,
    CostsDetailsTransactions costsDetailsTransaction,
    CostsDetailsTransactions costsDetailsTransactionFrais,
    Transactions transactionEncaissement,
    CostsDetailsTransactions costsDetailsTransactionEncaissement
  ) {
    try {
      transactionsService.addTransaction(transaction);
      costsDetailsTransactionsService.addCostDetailTrans(
        costsDetailsTransaction
      );

      if (costsDetailsTransactionEncaissement.getTransactions() != null) {
        costsDetailsTransactionsService.addCostDetailTrans(
          costsDetailsTransactionFrais
        );
        transactionsService.addTransaction(transactionEncaissement);
        costsDetailsTransactionsService.addCostDetailTrans(
          costsDetailsTransactionEncaissement
        );
      }
      transactionOk = true;
    } catch (Exception e) {
      transactionOk = false;
    }
  }
}
