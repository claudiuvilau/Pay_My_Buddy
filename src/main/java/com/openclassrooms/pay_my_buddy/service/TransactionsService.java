package com.openclassrooms.pay_my_buddy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.repository.TransactionsRepository;

@Service
@Transactional
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

    public List<CostsDetailsTransactions> detailTransForUser(Users nameUser, boolean b) {
        List<CostsDetailsTransactions> listCosts = new ArrayList<>();

        List<Transactions> listT = nameUser.getTransactionsList();

        for (int i = 0; i < listT.size(); i++) {
            List<CostsDetailsTransactions> listC = listT.get(i).getCostsDetailsTransactionsList();
            for (int j = 0; j < listC.size(); j++) {
                if (b) {
                    listCosts.add(listC.get(j));
                } else {
                    // if b = false => and name trans = 3 (envoi), so to buddy
                    if (listC.get(j).getNameTransactions().getIdNameTrans() == 3) {
                        listCosts.add(listC.get(j));
                    }
                }
            }
        }
        return listCosts;
    }

    public double debit(Users nameUser) {
        double debit = 0;
        String debitString = "DEBIT";
        debit = recupererDebitCredit(nameUser, debitString, debit);

        return debit;
    }

    public double credit(Users nameUser) {
        double debit = 0;
        String debitString = "CREDIT";
        debit = recupererDebitCredit(nameUser, debitString, debit);

        return debit;
    }

    private double recupererDebitCredit(Users nameUser, String debitCreditString, double debitCreditDouble) {
        List<Transactions> listT = nameUser.getTransactionsList();
        for (int i = 0; i < listT.size(); i++) {
            List<CostsDetailsTransactions> listC = listT.get(i).getCostsDetailsTransactionsList();
            for (int j = 0; j < listC.size(); j++) {
                if (listC.get(j).getTypeTransactions().getNomTypeTrans().equals(debitCreditString)) {
                    debitCreditDouble += listC.get(j).getAmount();
                }
            }
        }
        return debitCreditDouble;
    }

}
