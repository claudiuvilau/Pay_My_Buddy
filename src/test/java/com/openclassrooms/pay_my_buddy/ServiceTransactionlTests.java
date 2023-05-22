package com.openclassrooms.pay_my_buddy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.service.CostsDetailsTransactionsService;
import com.openclassrooms.pay_my_buddy.service.ServiceTransactional;
import com.openclassrooms.pay_my_buddy.service.TransactionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ServiceTransactionlTests {

  @Autowired
  ServiceTransactional serviceTransactional;

  @MockBean
  TransactionsService transactionsService;

  @MockBean
  CostsDetailsTransactionsService costsDetailsTransactionsService;

  @MockBean
  CostsDetailsTransactions costsDetailsTransactionEncaissement;

  @Test
  public void testUpdateTableTransactionsAndCostsDetailsTransactions() {
    Transactions transaction = new Transactions();

    when(costsDetailsTransactionEncaissement.getTransactions())
      .thenReturn(transaction);

    boolean addedTrans = serviceTransactional.updateTableTransactionsAndCostsDetailsTransactions(
      transaction,
      null,
      null,
      null,
      costsDetailsTransactionEncaissement
    );
    assertEquals(true, addedTrans);
  }
}
