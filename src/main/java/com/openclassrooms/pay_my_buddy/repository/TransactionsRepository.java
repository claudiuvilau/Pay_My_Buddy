package com.openclassrooms.pay_my_buddy.repository;

import org.springframework.data.repository.CrudRepository;

import com.openclassrooms.pay_my_buddy.model.Transactions;

public interface TransactionsRepository extends CrudRepository<Transactions, Integer> {

}
