package com.openclassrooms.pay_my_buddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.pay_my_buddy.model.TypeTransactions;

@Repository
public interface TypeTransactionsRepository extends CrudRepository<TypeTransactions, Integer> {

}
