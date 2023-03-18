package com.openclassrooms.pay_my_buddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;

@Service
@Transactional
public class ServiceTransactionl {

    @Autowired
    private TransactionsService transactionsService; // instance of object

    @Autowired
    private CostsDetailsTransactionsService costsDetailsTransactionsService; // instance of object

    public void updateTableTransactionsAndCostsDetailsTransactions(Transactions transaction,
            CostsDetailsTransactions costsDetailsTransaction,
            CostsDetailsTransactions costsDetailsTransactionFrais, Transactions transactionEncaissement,
            CostsDetailsTransactions costsDetailsTransactionEncaissement) {

        transactionsService.addTransaction(transaction);
        costsDetailsTransactionsService.addCostDetailTrans(costsDetailsTransaction);

        if (costsDetailsTransactionEncaissement.getTransactions() != null) {
            costsDetailsTransactionsService.addCostDetailTrans(costsDetailsTransactionFrais);
            transactionsService.addTransaction(transactionEncaissement);
            costsDetailsTransactionsService.addCostDetailTrans(costsDetailsTransactionEncaissement);
        }

    }

}
