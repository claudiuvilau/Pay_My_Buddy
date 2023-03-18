package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.repository.CostsDetailsTransactionsRepository;

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

    public CostsDetailsTransactions addCostDetailTrans(CostsDetailsTransactions costsDetailsTransactions) {
        return costsDetailsTransactionsRepository.save(costsDetailsTransactions);
    }

    public void deleteCostDetailTrans(CostsDetailsTransactions costsDetailsTransactions) {
        costsDetailsTransactionsRepository.delete(costsDetailsTransactions);
    }

    public void deleteCostDetailTransById(Integer id) {
        costsDetailsTransactionsRepository.deleteById(id);
    }

}
