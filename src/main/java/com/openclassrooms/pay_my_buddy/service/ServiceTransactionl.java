package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceTransactionl {

  @Autowired
  private TransactionsService transactionsService; // instance of object

  @Autowired
  private CostsDetailsTransactionsService costsDetailsTransactionsService; // instance of object

  public boolean updateTableTransactionsAndCostsDetailsTransactions(
    Transactions transaction,
    CostsDetailsTransactions costsDetailsTransaction,
    CostsDetailsTransactions costsDetailsTransactionFrais,
    Transactions transactionEncaissement,
    CostsDetailsTransactions costsDetailsTransactionEncaissement
  ) {
    boolean addedTrans = false;
    transactionsService.addTransaction(transaction);
    costsDetailsTransactionsService.addCostDetailTrans(costsDetailsTransaction);

    if (costsDetailsTransactionEncaissement.getTransactions() != null) {
      costsDetailsTransactionsService.addCostDetailTrans(
        costsDetailsTransactionFrais
      );
      transactionsService.addTransaction(transactionEncaissement);
      costsDetailsTransactionsService.addCostDetailTrans(
        costsDetailsTransactionEncaissement
      );
    }
    addedTrans = true;
    return addedTrans;
  }
}
