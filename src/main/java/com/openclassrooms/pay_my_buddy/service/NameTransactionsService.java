package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.model.NameTransactions;
import com.openclassrooms.pay_my_buddy.repository.NameTransactionsRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NameTransactionsService {

  @Autowired
  NameTransactionsRepository nameTransactionsRepository;

  public Iterable<NameTransactions> getNameTransactions() {
    return nameTransactionsRepository.findAll();
  }

  public Optional<NameTransactions> getNameTransactionById(Integer id) {
    return nameTransactionsRepository.findById(id);
  }

  public NameTransactions addNameTransaction(
    NameTransactions nameTransactions
  ) {
    return nameTransactionsRepository.save(nameTransactions);
  }

  public void deleteNameTransactions(NameTransactions nameTransactions) {
    nameTransactionsRepository.delete(nameTransactions);
  }

  public void deleteNameTransactionsById(Integer id) {
    nameTransactionsRepository.deleteById(id);
  }
}
