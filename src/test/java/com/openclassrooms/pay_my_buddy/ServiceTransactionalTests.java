package com.openclassrooms.pay_my_buddy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.service.CostsDetailsTransactionsService;
import com.openclassrooms.pay_my_buddy.service.ServiceTransactional;
import com.openclassrooms.pay_my_buddy.service.TransactionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTransactionalTests {

  @Autowired
  private Transactions transaction;

  @Autowired
  private CostsDetailsTransactions costsDetailsTransaction;

  @Autowired
  CostsDetailsTransactions costsDetailsTransactionFrais;

  @Autowired
  private Transactions transactionEncaissement;

  @Autowired
  private CostsDetailsTransactions costsDetailsTransactionEncaissement;

  @Autowired
  private ServiceTransactional serviceTransactional;

  @MockBean
  TransactionsService transactionsService;

  @MockBean
  CostsDetailsTransactionsService costsDetailsTransactionsService;

  @Test
  public void testUpdateTableTransactionsAndCostsDetailsTransactionsException()
    throws Exception {
    costsDetailsTransaction.setAmount(19);

    when(transactionsService.addTransaction(transaction))
      .thenThrow(NullPointerException.class);
    serviceTransactional.updateTableTransactionsAndCostsDetailsTransactions(
      transaction,
      costsDetailsTransaction,
      costsDetailsTransactionFrais,
      transactionEncaissement,
      costsDetailsTransactionEncaissement
    );
    assertEquals(false, serviceTransactional.getTransactionOk());
  }

  @Test
  public void testUpdateTableTransactionsAndCostsDetailsTransactions()
    throws Exception {
    /*
      descriptions.setIdDescriptions(2);
    nameTransactions.setIdNameTrans(1);
    typeTransactions.setIdTypeTrans(1);
    
    Users users = new Users();
    users.setIdUsers(1);
    
    transaction.setInvoiced(false);
    transaction.setIdTrans(99);
    transaction.setUser(1);
    SimpleDateFormat formatter = new SimpleDateFormat(
      "yyyy-MM-dd",
      Locale.ENGLISH
    );
    Date dateTrans = formatter.parse("2023-09-12");
    
    transaction.setDateTrans(dateTrans);
    
    costsDetailsTransaction.setAmount(19);
    costsDetailsTransaction.setDescriptions(descriptions);
    costsDetailsTransaction.setNameTransactions(nameTransactions);
    costsDetailsTransaction.setTypeTransactions(typeTransactions);
    costsDetailsTransaction.setUsers(users);
    costsDetailsTransaction.setTransactions(transaction);
    */

    transaction.setInvoiced(false);
    transaction.setIdTrans(99);
    transaction.setUser(1);

    costsDetailsTransactionEncaissement.setTransactions(transaction);

    serviceTransactional.updateTableTransactionsAndCostsDetailsTransactions(
      transaction,
      costsDetailsTransaction,
      costsDetailsTransactionFrais,
      transactionEncaissement,
      costsDetailsTransactionEncaissement
    );
    assertEquals(true, serviceTransactional.getTransactionOk());
  }

  @Test
  public void testUpdateTableTransactionsAndCostsDetailsTransactionsNull()
    throws Exception {
    /*
      descriptions.setIdDescriptions(2);
    nameTransactions.setIdNameTrans(1);
    typeTransactions.setIdTypeTrans(1);
    
    Users users = new Users();
    users.setIdUsers(1);
    
    transaction.setInvoiced(false);
    transaction.setIdTrans(99);
    transaction.setUser(1);
    SimpleDateFormat formatter = new SimpleDateFormat(
      "yyyy-MM-dd",
      Locale.ENGLISH
    );
    Date dateTrans = formatter.parse("2023-09-12");
    
    transaction.setDateTrans(dateTrans);
    
    costsDetailsTransaction.setAmount(19);
    costsDetailsTransaction.setDescriptions(descriptions);
    costsDetailsTransaction.setNameTransactions(nameTransactions);
    costsDetailsTransaction.setTypeTransactions(typeTransactions);
    costsDetailsTransaction.setUsers(users);
    costsDetailsTransaction.setTransactions(transaction);
    */

    serviceTransactional.updateTableTransactionsAndCostsDetailsTransactions(
      transaction,
      costsDetailsTransaction,
      costsDetailsTransactionFrais,
      transactionEncaissement,
      costsDetailsTransactionEncaissement
    );
    assertEquals(true, serviceTransactional.getTransactionOk());
  }
}
