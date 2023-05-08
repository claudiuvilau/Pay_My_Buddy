package com.openclassrooms.pay_my_buddy;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.RequestClass;
import com.openclassrooms.pay_my_buddy.model.Roles;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.CreationTransactionService;
import com.openclassrooms.pay_my_buddy.service.FriendsService;
import com.openclassrooms.pay_my_buddy.service.TransactionsService;
import com.openclassrooms.pay_my_buddy.service.UsersService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTests {

  private String userNameConnected = "mireille.benoit@hotmail.com";
  private Date date;
  private Users usersName;
  private Users userBuddy;
  private String firstName = "newUserFirstName";
  private String lastName = "newUserLastName";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @MockBean
  UsersService usersService;

  @MockBean
  Users users;

  @MockBean
  TransactionsService transactionsService;

  @MockBean
  FriendsService friendsService;

  @MockBean
  RequestClass requestClass;

  @MockBean
  CreationTransactionService creationTransactionService;

  @BeforeEach
  public void setup() throws ParseException {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(context)
        // .defaultRequest(get("/").with(user(userNameConnected).roles("USER")))
        // .defaultRequest(post("/").with(user(userNameConnected).roles("USER")))
        .apply(springSecurity())
        .build();
    crateDate();
    createUser();
  }

  private void crateDate() throws ParseException {
    String dateString = "2023-12-31";
    SimpleDateFormat formatter = new SimpleDateFormat(
      "yyyy-MM-dd",
      Locale.ENGLISH
    );
    date = formatter.parse(dateString);
  }

  private void createUser() {
    Roles roles;
    usersName = new Users();
    int roleId = 2; // 2 = user
    roles = new Roles();
    roles.setIdRoles(roleId);
    roles.setNameRole("USER");
    usersName = new Users();
    usersName.setIdUsers(1);
    usersName.setIdEmail(userNameConnected);
    usersName.setPassword("newUserPassword");
    usersName.setFirstName(firstName);
    usersName.setNameUser(lastName);
    usersName.setBirthDate(date);
    usersName.setRole(roles);

    userBuddy = new Users();
    userBuddy.setIdUsers(2);
    userBuddy.setIdEmail("id_buddy@paymybuddy.com");
    userBuddy.setFirstName("FirstNameBuddy");
    userBuddy.setNameUser("NameBuddy");
    userBuddy.setBirthDate(date);
  }

  // @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAfterLogin() throws Exception {
    List<CostsDetailsTransactions> listCostsUserToBuddy = new ArrayList<>();
    CostsDetailsTransactions costsDetailsTransactions = new CostsDetailsTransactions();

    Transactions transactions = new Transactions();
    transactions.setDateTrans(date);

    listCostsUserToBuddy.add(costsDetailsTransactions);

    List<Users> list = new ArrayList<>();
    list.add(usersName);

    costsDetailsTransactions.setAmount(999);
    costsDetailsTransactions.setTransactions(transactions);
    costsDetailsTransactions.setUsers(usersName);

    when(transactionsService.detailTransForUser(usersName, false))
      .thenReturn(listCostsUserToBuddy);
    when(transactionsService.debit(usersName)).thenReturn(99.99);
    when(transactionsService.credit(usersName)).thenReturn(999.99);

    when(usersService.getUsers()).thenReturn(list);
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);
    when(users.getFirstName()).thenReturn(firstName);
    when(users.getNameUser()).thenReturn(lastName);

    mockMvc.perform(get("/*")).andExpect(status().isOk());
  }

  // @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddConnection() throws Exception {
    List<CostsDetailsTransactions> listCostsUserToBuddy = new ArrayList<>();
    CostsDetailsTransactions costsDetailsTransactions = new CostsDetailsTransactions();

    Transactions transactions = new Transactions();
    transactions.setDateTrans(date);

    listCostsUserToBuddy.add(costsDetailsTransactions);

    List<Users> list = new ArrayList<>();
    list.add(usersName);

    costsDetailsTransactions.setAmount(999);
    costsDetailsTransactions.setTransactions(transactions);
    costsDetailsTransactions.setUsers(usersName);

    when(transactionsService.detailTransForUser(usersName, false))
      .thenReturn(listCostsUserToBuddy);
    when(transactionsService.debit(usersName)).thenReturn(99.99);
    when(transactionsService.credit(usersName)).thenReturn(999.99);

    when(usersService.getUsers()).thenReturn(list);
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);
    when(users.getFirstName()).thenReturn(firstName);
    when(users.getNameUser()).thenReturn(lastName);

    mockMvc.perform(get("/addconnection")).andExpect(status().is(200));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testDetailTotalAmount() throws Exception {
    List<Users> list = new ArrayList<>();
    list.add(usersName);

    List<CostsDetailsTransactions> listDetailTransForUser = new ArrayList<>();
    when(transactionsService.detailTransForUser(usersName, true))
      .thenReturn(listDetailTransForUser);

    when(usersService.getUsers()).thenReturn(list);
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);
    when(users.getFirstName()).thenReturn(firstName);
    when(users.getNameUser()).thenReturn(lastName);

    mockMvc.perform(get("/detailTotalAmount")).andExpect(status().is(200));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testSelectConnection() throws Exception {
    List<Friends> listFriends = new ArrayList<>();
    when(users.getFriends()).thenReturn(listFriends);

    List<Users> list = new ArrayList<>();
    list.add(usersName);

    when(usersService.getUsers()).thenReturn(list);
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);
    when(users.getFirstName()).thenReturn(firstName);
    when(users.getNameUser()).thenReturn(lastName);

    mockMvc.perform(get("/selectconnection")).andExpect(status().is(200));
  }

  @Test
  public void testLogin() throws Exception {
    mockMvc.perform(get("/login")).andExpect(status().is(200));
  }

  @Test
  @WithMockUser(
    username = "mireille.benoit@hotmail.com",
    password = "2",
    authorities = "ADMIN"
  )
  public void testGetAdmin() throws Exception {
    // config role admin of user
    Roles roleAdmin = new Roles();
    roleAdmin.setIdRoles(1); // role admin
    roleAdmin.setNameRole("ADMIN");
    usersName.setRole(roleAdmin);

    List<CostsDetailsTransactions> listDetailTransForUser = new ArrayList<>();
    when(transactionsService.detailTransForUser(usersName, true))
      .thenReturn(listDetailTransForUser);
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);
    when(users.getFirstName()).thenReturn(firstName);
    when(users.getNameUser()).thenReturn(lastName);

    mockMvc.perform(get("/admin")).andExpect(status().is(200));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddedConnectionBuddyNull() throws Exception {
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);
    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddedConnectionBuddyEqualsUser() throws Exception {
    when(usersService.getUser(null)).thenReturn(usersName);
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);
    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().is(200));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddedConnectionBuddyAlreadyConnected() throws Exception {
    when(usersService.getUser(null)).thenReturn(userBuddy);
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);

    Friends friend = new Friends();
    friend.setId(1);
    friend.setUsers(userBuddy);
    friend.setUsersIdUsers(usersName.getIdUsers());
    when(
      friendsService.getFriend(usersName.getIdUsers(), userBuddy.getIdUsers())
    )
      .thenReturn(friend);

    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddedConnectionErrorDataBuddy() throws Exception {
    when(usersService.getUser(null)).thenReturn(userBuddy);
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);

    when(
      friendsService.getFriend(usersName.getIdUsers(), userBuddy.getIdUsers())
    )
      .thenReturn(null);

    String parameterHtml = "dateDeNaissance";
    when(requestClass.requestParameter(parameterHtml))
      .thenReturn("{2023-10-31}");

    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddedConnection() throws Exception {
    when(usersService.getUser(null)).thenReturn(userBuddy);
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);

    when(
      friendsService.getFriend(usersName.getIdUsers(), userBuddy.getIdUsers())
    )
      .thenReturn(null);

    String parameterHtml = "dateDeNaissance";
    Date date = userBuddy.getBirthDate();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = dateFormat.format(date);
    when(requestClass.requestParameter(parameterHtml)).thenReturn(strDate);
    when(requestClass.requestParameter("prenom"))
      .thenReturn(userBuddy.getFirstName());
    when(requestClass.requestParameter("nom"))
      .thenReturn(userBuddy.getNameUser());
    when(users.getFirstName()).thenReturn(firstName);
    when(users.getNameUser()).thenReturn(lastName);

    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().is(201));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testSelectedConnection() throws Exception {
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);

    when(requestClass.requestParameter("connections")).thenReturn("envoi");
    when(requestClass.requestParameter("amount")).thenReturn("10");
    when(requestClass.requestParameter("description"))
      .thenReturn("description");

    when(
      creationTransactionService.createTransaction(
        usersName,
        "envoi",
        "10",
        "description"
      )
    )
      .thenReturn(true);
    mockMvc.perform(post("/paid").with(csrf())).andExpect(status().is(201));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testSelectedConnectionNoTransaction() throws Exception {
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);

    when(requestClass.requestParameter("connections")).thenReturn("envoi");
    when(requestClass.requestParameter("amount")).thenReturn("10");
    when(requestClass.requestParameter("description"))
      .thenReturn("description");

    when(
      creationTransactionService.createTransaction(
        usersName,
        "envoi",
        "10",
        "description"
      )
    )
      .thenReturn(false);
    mockMvc.perform(post("/paid").with(csrf())).andExpect(status().is(202));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testSelectedConnectionNo() throws Exception {
    when(usersService.getUser(userNameConnected)).thenReturn(usersName);

    when(requestClass.requestParameter("connections")).thenReturn("envoi");
    when(requestClass.requestParameter("amount")).thenReturn("10");
    when(requestClass.requestParameter("description"))
      .thenReturn("description");

    when(
      creationTransactionService.createTransaction(
        usersName,
        "envoi",
        "10",
        "description"
      )
    )
      .thenReturn(false);
    mockMvc.perform(post("/paid").with(csrf())).andExpect(status().is(202));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testRegisterNewUserNoNewUser() throws Exception {
    when(requestClass.requestParameter("username"))
      .thenReturn("new_user_mail@paymybuddy.com");
    when(requestClass.requestParameter("password")).thenReturn("pswd");
    when(requestClass.requestParameter("prenom"))
      .thenReturn("firstNameNewUser");
    when(requestClass.requestParameter("nom")).thenReturn("lastNameNewUser");
    String parameterHtml = "dateDeNaissance";
    Date date = userBuddy.getBirthDate();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = dateFormat.format(date);
    when(requestClass.requestParameter(parameterHtml)).thenReturn(strDate);
    mockMvc.perform(post("/register").with(csrf())).andExpect(status().is(200));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testRegisterNewUser() throws Exception {
    when(requestClass.requestParameter("username"))
      .thenReturn("new_user_mail@paymybuddy.com");
    when(requestClass.requestParameter("password")).thenReturn("pswd");
    when(requestClass.requestParameter("prenom"))
      .thenReturn("firstNameNewUser");
    when(requestClass.requestParameter("nom")).thenReturn("lastNameNewUser");
    String parameterHtml = "dateDeNaissance";
    Date date = userBuddy.getBirthDate();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = dateFormat.format(date);
    when(requestClass.requestParameter(parameterHtml)).thenReturn(strDate);
    when(usersService.getUser("new_user_mail@paymybuddy.com"))
      .thenReturn(userBuddy);
    mockMvc.perform(post("/register").with(csrf())).andExpect(status().is(201));
  }
}
