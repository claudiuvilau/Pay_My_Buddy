package com.openclassrooms.pay_my_buddy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Descriptions;
import com.openclassrooms.pay_my_buddy.model.NameTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.TypeTransactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.CreationTransactionService;
import com.openclassrooms.pay_my_buddy.service.DescriptionsService;
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
  DescriptionsService descriptionsService;

  @MockBean
  NameTransactions nameTransaction;

  @MockBean
  TypeTransactions typeTransactions;

  @MockBean
  CostsDetailsTransactions costsDetailsTransaction;

  @MockBean
  ServiceTransactional serviceTransactional;

  @Test
  public void testCreateTransactionSend() {
    Users nameBuddy = new Users();
    String typeTransConnection = "id_buddy@paymybuddy.com";
    when(usersService.getUser(typeTransConnection)).thenReturn(nameBuddy);
    when(serviceTransactional.getTransactionOk()).thenReturn(true);
    transaction = new Transactions();
    costsDetailsTransaction = new CostsDetailsTransactions();
    creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      "description"
    );
    assertEquals(true, serviceTransactional.getTransactionOk());
  }

  @Test
  public void testCreateTransactionTransferSend() {
    Users nameBuddy = new Users();
    String typeTransConnection = "2"; // 2 = transfer
    when(usersService.getUser(typeTransConnection)).thenReturn(nameBuddy);
    when(serviceTransactional.getTransactionOk()).thenReturn(true);
    transaction = new Transactions();
    costsDetailsTransaction = new CostsDetailsTransactions();
    creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      null
    );
    assertEquals(true, serviceTransactional.getTransactionOk());
  }

  @Test
  public void testCreateTransactionSendNoDescriptionInTableDescriptions() {
    Users nameBuddy = new Users();
    String typeTransConnection = "id_buddy@paymybuddy.com";
    when(usersService.getUser(typeTransConnection)).thenReturn(nameBuddy);
    when(serviceTransactional.getTransactionOk()).thenReturn(true);
    when(descriptionsService.getDescriptions()).thenReturn(null);
    transaction = new Transactions();
    costsDetailsTransaction = new CostsDetailsTransactions();
    creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      "description"
    );
    assertEquals(true, serviceTransactional.getTransactionOk());
  }

  @Test
  public void testCreateTransactionSendDescriptionInTableDescriptions() {
    Users nameBuddy = new Users();
    String typeTransConnection = "id_buddy@paymybuddy.com";
    when(usersService.getUser(typeTransConnection)).thenReturn(nameBuddy);
    when(serviceTransactional.getTransactionOk()).thenReturn(true);
    String descriptionToAdd = "description";
    Descriptions descriptionFinded = new Descriptions();
    descriptionFinded.setDescription(descriptionToAdd);
    when(descriptionsService.getDescription(descriptionToAdd))
      .thenReturn(descriptionFinded);
    transaction = new Transactions();
    costsDetailsTransaction = new CostsDetailsTransactions();
    creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      descriptionToAdd
    );
    assertEquals(true, serviceTransactional.getTransactionOk());
  }

  @Test
  public void testCreateTransactionSendDescriptionFraisInTableDescriptions() {
    Users nameBuddy = new Users();
    String typeTransConnection = "id_buddy@paymybuddy.com";
    when(usersService.getUser(typeTransConnection)).thenReturn(nameBuddy);
    when(serviceTransactional.getTransactionOk()).thenReturn(true);
    String descriptionToAdd = "frais";
    Descriptions descriptionFinded = new Descriptions();
    descriptionFinded.setDescription(descriptionToAdd);
    when(descriptionsService.getDescription(descriptionToAdd))
      .thenReturn(descriptionFinded);
    transaction = new Transactions();
    costsDetailsTransaction = new CostsDetailsTransactions();
    creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      descriptionToAdd
    );
    assertEquals(true, serviceTransactional.getTransactionOk());
  }

  @Test
  public void testCreateTransactionSendNoAdded() {
    Users nameBuddy = new Users();
    String typeTransConnection = "id_buddy@paymybuddy.com";
    when(usersService.getUser(typeTransConnection)).thenReturn(nameBuddy);
    when(serviceTransactional.getTransactionOk()).thenReturn(false);
    creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      "description"
    );
    assertEquals(false, serviceTransactional.getTransactionOk());
  }

  @Test
  public void testCreateTransactionOtherType() {
    Users nameBuddy = new Users();
    String typeTransConnection = "1"; // without @ or 3 to test other type transaction
    when(usersService.getUser("id_buddy@paymybuddy.com")).thenReturn(nameBuddy);
    when(serviceTransactional.getTransactionOk()).thenReturn(true);
    creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      "description"
    );
    assertEquals(true, serviceTransactional.getTransactionOk());
  }

  @Test
  public void testCreateTransactionOtherTypeNoAdded() {
    Users nameBuddy = new Users();
    String typeTransConnection = "1"; // without @ or 3 to test other type transaction
    when(usersService.getUser("id_buddy@paymybuddy.com")).thenReturn(nameBuddy);
    when(serviceTransactional.getTransactionOk()).thenReturn(false);
    creationTransactionService.createTransaction(
      users,
      typeTransConnection,
      "10",
      "description"
    );
    assertEquals(false, serviceTransactional.getTransactionOk());
  }
}
