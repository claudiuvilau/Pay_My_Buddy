package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.repository.TransactionsRepository;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    public Iterable<Transactions> getTransactions() {
        return transactionsRepository.findAll();
    }

    public Optional<Transactions> getTransactionById(Integer id) {
        return transactionsRepository.findById(id);
    }

    public Transactions addTransaction(Transactions transactions) {
        return transactionsRepository.save(transactions);
    }

    public void deletetransaction(Transactions transactions) {
        transactionsRepository.delete(transactions);
    }

    public void deleteTransactionById(Integer id) {
        transactionsRepository.deleteById(id);
    }

}
