package com.openclassrooms.pay_my_buddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.pay_my_buddy.model.Transactions;

@Repository
public interface TransactionsRepository extends CrudRepository<Transactions, Integer> {

}
