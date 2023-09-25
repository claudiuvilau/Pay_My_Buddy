package com.openclassrooms.pay_my_buddy.repository;

import com.openclassrooms.pay_my_buddy.model.Transactions;
import org.springframework.data.repository.CrudRepository;

public interface TransactionsRepository
  extends CrudRepository<Transactions, Integer> {}
