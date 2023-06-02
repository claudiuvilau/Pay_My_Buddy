package com.openclassrooms.pay_my_buddy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.pay_my_buddy.controller.LoginController;
import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.RequestClass;
import com.openclassrooms.pay_my_buddy.model.Roles;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.repository.FriendsRepository;
import com.openclassrooms.pay_my_buddy.repository.UsersRepository;
import jakarta.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PayMyBuddyTestsIT {

  private String userNameConnected = "mireille.benoit@hotmail.com";
  private String userBuddyIdEmail = "id_buddy@paymybuddy.com";
  private Date date;
  private Users usersName;
  private Users userBuddy;
  private String firstName = "newUserFirstName";
  private String lastName = "newUserLastName";

  @Inject
  private LoginController loginController;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private Friends friends;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  FriendsRepository friendsRepository;

  @Autowired
  UsersRepository usersRepository;

  @MockBean
  RequestClass requestClass;

  @BeforeEach
  public void setup() throws ParseException {
    mockMvc =
      MockMvcBuilders
        .webAppContextSetup(context)
        //.defaultRequest(get("/").with(user(userNameConnected).roles("USER")))
        //.defaultRequest(post("/").with(user(userNameConnected).roles("USER")))
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
    userBuddy.setIdEmail(userBuddyIdEmail);
    userBuddy.setFirstName("FirstNameBuddy");
    userBuddy.setNameUser("NameBuddy");
    userBuddy.setBirthDate(date);
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddConnection() throws Exception {
    mockMvc.perform(get("/addconnection")).andExpect(status().is(200));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testDetailTotalAmount() throws Exception {
    mockMvc.perform(get("/detailTotalAmount")).andExpect(status().is(200));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testSelectConnection() throws Exception {
    mockMvc.perform(get("/selectconnection")).andExpect(status().is(200));
  }

  @Test
  @WithMockUser(
    username = "mireille.benoit@hotmail.com",
    password = "2",
    authorities = "ADMIN"
  )
  public void testGetAdmin() throws Exception {
    mockMvc.perform(get("/admin")).andExpect(status().is(200));
  }

  @Test
  @WithMockUser(
    username = "mireille.benoit@hotmail.com",
    password = "2",
    authorities = "ADMIN"
  )
  public void testUsersAccounts() throws Exception {
    when(requestClass.requestParameter("usersaccounts"))
      .thenReturn("mireille.benoit@hotmail.com");
    mockMvc.perform(post("/admin").with(csrf())).andExpect(status().is(302));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddedConnectionBuddyNull() throws Exception {
    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().is(404));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddedConnectionBuddyEqualsUser() throws Exception {
    when(requestClass.requestParameter("email"))
      .thenReturn("mireille.benoit@hotmail.com");
    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().is(406));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddedConnectionBuddyAlreadyConnected() throws Exception {
    when(requestClass.requestParameter("email"))
      .thenReturn("jack.dupont@yahoo.fr");
    int idUser = 2;
    int idBuddy = 1;
    // before adding
    friends = friendsRepository.qselectBuddy(idUser, idBuddy);
    boolean delete = false;
    if (friends == null) {
      friendsRepository.qinsertBuddy(idUser, idBuddy);
      friends = friendsRepository.qselectBuddy(idUser, idBuddy);
      delete = true;
    }
    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().is(304));

    if (friends != null) {
      assertEquals(
        Integer.toString(friends.getUsersIdUsers()) +
        Integer.toString(friends.getUsers().getIdUsers()),
        Integer.toString(idUser) + Integer.toString(idBuddy)
      );
    }

    if (delete) {
      friendsRepository.qdeleteBuddy(idUser, idBuddy);
    }
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testAddedConnectionErrorDataBuddy() throws Exception {
    when(requestClass.requestParameter("email"))
      .thenReturn("sebastien.martin@hotmail.fr"); // id = 3
    String dateN = "1900-12-31"; // error birth date of the buddy
    when(requestClass.requestParameter("dateDeNaissance")).thenReturn(dateN);
    when(requestClass.requestParameter("prenom")).thenReturn(firstName); // error first name of the buddy
    when(requestClass.requestParameter("nom")).thenReturn(lastName); // // error last name of the buddy
    int idUser = 2;
    int idBuddy = 3;
    // before adding
    friends = friendsRepository.qselectBuddy(idUser, idBuddy);
    boolean deleted = false;
    if (
      friends != null &&
      friends.getUsersIdUsers() == idUser &&
      friends.getUsers().getIdUsers() == idBuddy
    ) {
      friendsRepository.qdeleteBuddy(idUser, idBuddy);
      deleted = true;
    }
    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().is(206));

    if (deleted) {
      friendsRepository.qinsertBuddy(idUser, idBuddy);
    }
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  @Query(value = "")
  public void testAddedConnection() throws Exception {
    int idUser = 2;
    int idBuddy = 3;
    when(requestClass.requestParameter("email"))
      .thenReturn("sebastien.martin@hotmail.fr"); // id user is 3
    String dateN = "1977-09-19";
    when(requestClass.requestParameter("dateDeNaissance")).thenReturn(dateN);
    when(requestClass.requestParameter("prenom")).thenReturn("Sébastien");
    when(requestClass.requestParameter("nom")).thenReturn("MARTIN");

    // before adding
    friends = friendsRepository.qselectBuddy(idUser, idBuddy);
    if (
      friends != null &&
      friends.getUsersIdUsers() == idUser &&
      friends.getUsers().getIdUsers() == idBuddy
    ) {
      friendsRepository.qdeleteBuddy(idUser, idBuddy);
    }

    mockMvc
      .perform(post("/addedconnection").with(csrf()))
      .andExpect(status().is(201));

    if (friends != null) {
      assertEquals(
        Integer.toString(idUser) + Integer.toString(idBuddy),
        Integer.toString(friends.getUsersIdUsers()) +
        Integer.toString(friends.getUsers().getIdUsers())
      );
    }
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testSelectedConnection() throws Exception {
    when(requestClass.requestParameter("connections"))
      .thenReturn("jack.dupont@yahoo.fr");
    when(requestClass.requestParameter("amount")).thenReturn("99");
    when(requestClass.requestParameter("description"))
      .thenReturn("description");

    mockMvc.perform(post("/paid").with(csrf())).andExpect(status().is(302));
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testRegister() throws Exception {
    String newUser = "new.user@paymybuddy.com";
    when(requestClass.requestParameter("username")).thenReturn(newUser);
    when(requestClass.requestParameter("password")).thenReturn("99");
    when(requestClass.requestParameter("prenom")).thenReturn("newFirstName");
    when(requestClass.requestParameter("nom")).thenReturn("newLastName");
    when(requestClass.requestParameter("dateDeNaissance"))
      .thenReturn("1977-09-19");

    // before adding
    usersName = usersRepository.qselectUserByIdMail(newUser);
    if (usersName != null && usersName.getIdEmail().equals(newUser)) {
      // delete friends if it exist
      if (
        friendsRepository.qselectBuddyidUser(usersName.getIdUsers()) != null
      ) {
        friendsRepository.qdeleteBuddyidUser(usersName.getIdUsers());
      }
      if (
        friendsRepository.qselectBuddyidBuddy(usersName.getIdUsers()) != null
      ) {
        friendsRepository.qdeleteBuddyidBuddy(usersName.getIdUsers());
      }
      usersRepository.qdeleteUserByIdMail(newUser);
    }

    mockMvc.perform(post("/register").with(csrf())).andExpect(status().is(201));

    assertEquals(
      newUser,
      usersRepository.qselectUserByIdMail(newUser).getIdEmail()
    );
  }

  @Test
  @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
  public void testRegisterIdEmailExistAlready() throws Exception {
    String newUser = "new.user@paymybuddy.com";
    when(requestClass.requestParameter("username")).thenReturn(newUser);
    String password = "99";
    when(requestClass.requestParameter("password")).thenReturn(password);
    String newFirstName = "newFirstName";
    when(requestClass.requestParameter("prenom")).thenReturn(newFirstName);
    String newLastName = "newLastName";
    when(requestClass.requestParameter("nom")).thenReturn(newLastName);
    String dateN = "2023-01-01";
    when(requestClass.requestParameter("dateDeNaissance")).thenReturn(dateN);

    // before add if not exist
    usersName = usersRepository.qselectUserByIdMail(newUser);
    boolean insert = false;
    if (usersName == null) {
      usersRepository.qinsertUser(
        newUser,
        newLastName,
        newFirstName,
        date,
        password,
        1
      );
      insert = true;
    }
    mockMvc.perform(post("/register").with(csrf())).andExpect(status().is(404));

    if (insert) {
      usersRepository.qdeleteUserByIdMail(newUser);
    }
  }
}
