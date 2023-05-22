package com.openclassrooms.pay_my_buddy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.NameTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.TypeTransactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.TransactionsService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TransactionsServiceTests {

  @Autowired
  TransactionsService transactionService;

  @MockBean
  Users users;

  @MockBean
  CostsDetailsTransactions costsDetailsTransactions;

  @MockBean
  Transactions transaction;

  @MockBean
  NameTransactions nameTransactions;

  @MockBean
  TypeTransactions typeTransactions;

  @Test
  public void testDetailTransForUser() {
    List<Transactions> listT = new ArrayList<Transactions>();
    listT.add(0, transaction);
    when(users.getTransactionsList()).thenReturn(listT);
    List<CostsDetailsTransactions> listCosts = new ArrayList<>();
    listCosts.add(0, costsDetailsTransactions);
    when(transaction.getCostsDetailsTransactionsList()).thenReturn(listCosts);
    listCosts = transactionService.detailTransForUser(users, true);

    assertEquals(false, listCosts.isEmpty());
  }

  @Test
  public void testDetailTransForUserSendToBuddy() {
    List<Transactions> listT = new ArrayList<Transactions>();
    listT.add(0, transaction);
    when(users.getTransactionsList()).thenReturn(listT);
    List<CostsDetailsTransactions> listCosts = new ArrayList<>();
    listCosts.add(0, costsDetailsTransactions);
    when(transaction.getCostsDetailsTransactionsList()).thenReturn(listCosts);
    //costsDetailsTransactions = new CostsDetailsTransactions();
    nameTransactions.setIdNameTrans(3);
    nameTransactions.setNameTrans("envoi");
    costsDetailsTransactions.setNameTransactions(nameTransactions);
    when(costsDetailsTransactions.getNameTransactions())
      .thenReturn(nameTransactions);
    when(costsDetailsTransactions.getNameTransactions().getIdNameTrans())
      .thenReturn(3);
    listCosts = transactionService.detailTransForUser(users, false);

    assertEquals(false, listCosts.isEmpty());
  }

  @Test
  public void testDetailTransForUserNotSendToBuddy() {
    List<Transactions> listT = new ArrayList<Transactions>();
    listT.add(0, transaction);
    when(users.getTransactionsList()).thenReturn(listT);
    List<CostsDetailsTransactions> listCosts = new ArrayList<>();
    listCosts.add(0, costsDetailsTransactions);
    when(transaction.getCostsDetailsTransactionsList()).thenReturn(listCosts);
    nameTransactions.setIdNameTrans(1);
    nameTransactions.setNameTrans("versement");
    costsDetailsTransactions.setNameTransactions(nameTransactions);
    when(costsDetailsTransactions.getNameTransactions())
      .thenReturn(nameTransactions);
    when(costsDetailsTransactions.getNameTransactions().getIdNameTrans())
      .thenReturn(1);
    listCosts = transactionService.detailTransForUser(users, false);

    assertEquals(true, listCosts.isEmpty());
  }

  @Test
  public void testDetailTransForUserEmptyList() {
    when(users.getTransactionsList()).thenReturn(null);
    List<CostsDetailsTransactions> listCosts;
    listCosts = transactionService.detailTransForUser(users, false);

    assertEquals(true, listCosts.isEmpty());
  }

  @Test
  public void testDebit() {
    String debitOrCredit = "DEBIT";
    double amount = 15;
    List<Transactions> listT = new ArrayList<Transactions>();
    List<CostsDetailsTransactions> listCostsDetailsTransactions = new ArrayList<CostsDetailsTransactions>();
    costsDetailsTransactions.setAmount(amount);
    costsDetailsTransactions.setUsers(users);
    costsDetailsTransactions.setId(1);
    typeTransactions.setIdTypeTrans(1);
    typeTransactions.setNomTypeTrans(debitOrCredit);
    listCostsDetailsTransactions.add(costsDetailsTransactions);
    transaction.setCostsDetailsTransactionsList(listCostsDetailsTransactions);
    listT.add(transaction);
    when(users.getTransactionsList()).thenReturn(listT);
    when(transaction.getCostsDetailsTransactionsList())
      .thenReturn(listCostsDetailsTransactions);
    when(costsDetailsTransactions.getTypeTransactions())
      .thenReturn(typeTransactions);
    when(costsDetailsTransactions.getTypeTransactions().getNomTypeTrans())
      .thenReturn(debitOrCredit);
    when(costsDetailsTransactions.getAmount()).thenReturn(amount);
    double debit = transactionService.debit(users);
    assertEquals(amount, debit, 0);
  }

  @Test
  public void testDebitTypeTransCredit() {
    String debitOrCredit = "DEBIT";
    double amount = 0;
    List<Transactions> listT = new ArrayList<Transactions>();
    List<CostsDetailsTransactions> listCostsDetailsTransactions = new ArrayList<CostsDetailsTransactions>();
    costsDetailsTransactions.setAmount(amount);
    costsDetailsTransactions.setUsers(users);
    costsDetailsTransactions.setId(1);
    typeTransactions.setIdTypeTrans(1);
    typeTransactions.setNomTypeTrans(debitOrCredit);
    listCostsDetailsTransactions.add(costsDetailsTransactions);
    transaction.setCostsDetailsTransactionsList(listCostsDetailsTransactions);
    listT.add(transaction);
    when(users.getTransactionsList()).thenReturn(listT);
    when(transaction.getCostsDetailsTransactionsList())
      .thenReturn(listCostsDetailsTransactions);
    when(costsDetailsTransactions.getTypeTransactions())
      .thenReturn(typeTransactions);
    when(costsDetailsTransactions.getTypeTransactions().getNomTypeTrans())
      .thenReturn("CREDIT");
    when(costsDetailsTransactions.getAmount()).thenReturn(amount);
    double debit = transactionService.debit(users);
    assertEquals(amount, debit, 0);
  }

  @Test
  public void testCredit() {
    String debitOrCredit = "CREDIT";
    double amount = 15;
    List<Transactions> listT = new ArrayList<Transactions>();
    List<CostsDetailsTransactions> listCostsDetailsTransactions = new ArrayList<CostsDetailsTransactions>();
    costsDetailsTransactions.setAmount(amount);
    costsDetailsTransactions.setUsers(users);
    costsDetailsTransactions.setId(1);
    typeTransactions.setIdTypeTrans(1);
    typeTransactions.setNomTypeTrans(debitOrCredit);
    listCostsDetailsTransactions.add(costsDetailsTransactions);
    transaction.setCostsDetailsTransactionsList(listCostsDetailsTransactions);
    listT.add(transaction);
    when(users.getTransactionsList()).thenReturn(listT);
    when(transaction.getCostsDetailsTransactionsList())
      .thenReturn(listCostsDetailsTransactions);
    when(costsDetailsTransactions.getTypeTransactions())
      .thenReturn(typeTransactions);
    when(costsDetailsTransactions.getTypeTransactions().getNomTypeTrans())
      .thenReturn(debitOrCredit);
    when(costsDetailsTransactions.getAmount()).thenReturn(amount);
    double credit = transactionService.credit(users);
    assertEquals(amount, credit, 0);
  }

  @Test
  public void testCreditTyepTransDebit() {
    String debitOrCredit = "CREDIT";
    double amount = 0;
    List<Transactions> listT = new ArrayList<Transactions>();
    List<CostsDetailsTransactions> listCostsDetailsTransactions = new ArrayList<CostsDetailsTransactions>();
    costsDetailsTransactions.setAmount(amount);
    costsDetailsTransactions.setUsers(users);
    costsDetailsTransactions.setId(1);
    typeTransactions.setIdTypeTrans(1);
    typeTransactions.setNomTypeTrans(debitOrCredit);
    listCostsDetailsTransactions.add(costsDetailsTransactions);
    transaction.setCostsDetailsTransactionsList(listCostsDetailsTransactions);
    listT.add(transaction);
    when(users.getTransactionsList()).thenReturn(listT);
    when(transaction.getCostsDetailsTransactionsList())
      .thenReturn(listCostsDetailsTransactions);
    when(costsDetailsTransactions.getTypeTransactions())
      .thenReturn(typeTransactions);
    when(costsDetailsTransactions.getTypeTransactions().getNomTypeTrans())
      .thenReturn("DEBIT");
    when(costsDetailsTransactions.getAmount()).thenReturn(amount);
    double credit = transactionService.credit(users);
    assertEquals(amount, credit, 0);
  }
}
