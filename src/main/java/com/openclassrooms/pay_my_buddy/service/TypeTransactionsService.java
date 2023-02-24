package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.pay_my_buddy.model.TypeTransactions;
import com.openclassrooms.pay_my_buddy.repository.TypeTransactionsRepository;

@Service
public class TypeTransactionsService {

    @Autowired
    TypeTransactionsRepository typeTransactionsRepository;

    public Iterable<TypeTransactions> getTypeTransactions() {
        return typeTransactionsRepository.findAll();
    }

    public Optional<TypeTransactions> getTypeTransactionById(Integer id) {
        return typeTransactionsRepository.findById(id);
    }

    public TypeTransactions addTypeTransaction(TypeTransactions typeTransactions) {
        return typeTransactionsRepository.save(typeTransactions);
    }

    public void deleteTypeTransaction(TypeTransactions typeTransactions) {
        typeTransactionsRepository.delete(typeTransactions);
    }

    public void deleteTypeTransactionsById(Integer id) {
        typeTransactionsRepository.deleteById(id);
    }

}
