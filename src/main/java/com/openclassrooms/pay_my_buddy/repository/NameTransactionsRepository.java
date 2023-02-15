package com.openclassrooms.pay_my_buddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.pay_my_buddy.model.NameTransactions;

@Repository
public interface NameTransactionsRepository extends CrudRepository<NameTransactions, Integer> {

}
