package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.repository.CostsDetailsTransactionsRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CostsDetailsTransactionsService {

  @Autowired
  CostsDetailsTransactionsRepository costsDetailsTransactionsRepository;

  public Iterable<CostsDetailsTransactions> getCostsDetailTrans() {
    return costsDetailsTransactionsRepository.findAll();
  }

  public Optional<CostsDetailsTransactions> getCostDetailTransById(Integer id) {
    return costsDetailsTransactionsRepository.findById(id);
  }

  public CostsDetailsTransactions addCostDetailTrans(
    CostsDetailsTransactions costsDetailsTransactions
  ) {
    return costsDetailsTransactionsRepository.save(costsDetailsTransactions);
  }

  public void deleteCostDetailTrans(
    CostsDetailsTransactions costsDetailsTransactions
  ) {
    costsDetailsTransactionsRepository.delete(costsDetailsTransactions);
  }

  public void deleteCostDetailTransById(Integer id) {
    costsDetailsTransactionsRepository.deleteById(id);
  }
}
