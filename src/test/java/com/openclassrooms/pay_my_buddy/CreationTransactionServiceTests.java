package com.openclassrooms.pay_my_buddy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Descriptions;
import com.openclassrooms.pay_my_buddy.model.NameTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.CreationTransactionService;
import com.openclassrooms.pay_my_buddy.service.ServiceTransactional;
import com.openclassrooms.pay_my_buddy.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CreationTransactionServiceTests {

  @Autowired
  CreationTransactionService creationTransactionService;

  @MockBean
  UsersService usersService;

  @MockBean
  Transactions transaction;

  @MockBean
  Users users;

  @MockBean
  Descriptions description;

  @MockBean
  NameTransactions nameTransaction;

  @MockBean
  CostsDetailsTransactions costsDetailsTransaction;

  @MockBean
  ServiceTransactional serviceTransactional;

  @Test
  public void testCreateTransactionSend() {
    Users nameBuddy = new Users();
    String typeTransConnection = "id_buddy@paymybuddy.com";
    when(usersService.getUser(typeTransConnection)).thenReturn(nameBuddy);
    when(
      serviceTransactional.updateTableTransactionsAndCostsDetailsTransactions(
        transaction,
        costsDetailsTransaction,
        costsDetailsTransaction,
        transaction,
        costsDetailsTransaction
      )
    )
      .thenReturn(true);
    boolean addedTrans = creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      "description"
    );
    assertEquals(true, addedTrans);
  }

  @Test
  public void testCreateTransactionSendNoAdded() {
    Users nameBuddy = new Users();
    String typeTransConnection = "id_buddy@paymybuddy.com";
    when(usersService.getUser(typeTransConnection)).thenReturn(nameBuddy);

    when(
      serviceTransactional.updateTableTransactionsAndCostsDetailsTransactions(
        transaction,
        costsDetailsTransaction,
        costsDetailsTransaction,
        transaction,
        costsDetailsTransaction
      )
    )
      .thenReturn(false);
    boolean addedTrans = creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      "description"
    );
    assertEquals(false, addedTrans);
  }

  @Test
  public void testCreateTransactionOtherType() {
    Users nameBuddy = new Users();
    String typeTransConnection = "1"; // without @ or 3 to test other type transaction
    when(usersService.getUser("id_buddy@paymybuddy.com")).thenReturn(nameBuddy);
    when(
      serviceTransactional.updateTableTransactionsAndCostsDetailsTransactions(
        transaction,
        costsDetailsTransaction,
        costsDetailsTransaction,
        transaction,
        costsDetailsTransaction
      )
    )
      .thenReturn(true);
    boolean addedTrans = creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      "description"
    );
    assertEquals(true, addedTrans);
  }

  @Test
  public void testCreateTransactionOtherTypeNoAdded() {
    Users nameBuddy = new Users();
    String typeTransConnection = "1"; // without @ or 3 to test other type transaction
    when(usersService.getUser("id_buddy@paymybuddy.com")).thenReturn(nameBuddy);

    when(
      serviceTransactional.updateTableTransactionsAndCostsDetailsTransactions(
        transaction,
        costsDetailsTransaction,
        costsDetailsTransaction,
        transaction,
        costsDetailsTransaction
      )
    )
      .thenReturn(false);
    boolean addedTrans = creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      "description"
    );
    assertEquals(false, addedTrans);
  }
}
