package com.openclassrooms.pay_my_buddy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.Roles;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.CreationTransactionService;
import com.openclassrooms.pay_my_buddy.service.FriendsService;
import com.openclassrooms.pay_my_buddy.service.LoginControllerService;
import com.openclassrooms.pay_my_buddy.service.UsersService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PayMyBuddyApplicationServiceTests {

  private Date date;
  private String newUserIdEmail = "newusermail@paymybuddy.com";
  private String newUserPassword = "newUserPassword";
  private String newUserFirstName = "newUserFirstName";
  private String newUserNameUser = "newUserLastName";
  private String newUserBirthDate = "2020-01-01";
  private String userConnectedIdEmail = "userconnected@paymybuddy.com";
  private String userConnectedPassword = "userconnectedPassword";
  private String userConnectedFirstName = "userconnectedFirstName";
  private String userConnectedNameUser = "userconnectedLastName";
  private String userConnectedBirthDate = "2018-01-01";

  @Autowired
  private Users userConnected;

  @Autowired
  private Users newUser;

  @Autowired
  private LoginControllerService loginControllerService;

  @MockBean
  UsersService usersService;

  @MockBean
  Users users;

  @MockBean
  FriendsService friendsService;

  @MockBean
  Friends friends;

  @MockBean
  CreationTransactionService creationTransactionService;

  @BeforeEach
  public void setup() throws ParseException {
    createUserConnected();
    createNewUser();
  }

  private void createDate(String dateString) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat(
      "yyyy-MM-dd",
      Locale.ENGLISH
    );
    date = formatter.parse(dateString);
  }

  private void createUserConnected() throws ParseException {
    int roleId = 1; // 1 = user
    Roles roles = new Roles();
    roles.setIdRoles(roleId);
    userConnected = new Users();
    userConnected.setIdUsers(1);
    userConnected.setIdEmail(userConnectedIdEmail);
    userConnected.setPassword(userConnectedPassword);
    userConnected.setFirstName(userConnectedFirstName);
    userConnected.setNameUser(userConnectedNameUser);
    createDate(userConnectedBirthDate);
    userConnected.setBirthDate(date);
    userConnected.setRole(roles);
  }

  private void createNewUser() throws ParseException {
    int roleId = 1; // 1 = user
    Roles roles = new Roles();
    roles.setIdRoles(roleId);
    newUser = new Users();
    newUser.setIdUsers(2);
    newUser.setIdEmail(newUserIdEmail);
    newUser.setPassword(newUserPassword);
    newUser.setFirstName(newUserFirstName);
    newUser.setNameUser(newUserNameUser);
    createDate(newUserBirthDate);
    newUser.setBirthDate(date);
    newUser.setRole(roles);
  }

  @Test
  public void testRegisterNewUserNoAddedUser() throws Exception {
    when((usersService.getUser(newUserIdEmail))).thenReturn(null);
    int codeReturn = loginControllerService.addNewUser(
      "null",
      "null",
      "null",
      "null",
      newUserBirthDate
    );
    assertEquals(404, codeReturn);
  }

  @Test
  public void testRegisterNewUser() throws Exception {
    when((usersService.getUser(newUserIdEmail))).thenReturn(newUser);

    int codeReturn = loginControllerService.addNewUser(
      newUserIdEmail,
      newUserPassword,
      newUserFirstName,
      newUserNameUser,
      newUserBirthDate
    );
    assertEquals(201, codeReturn);
  }

  @Test
  public void testAddedConnectionUserBuddyNull() {
    when(usersService.getUser(newUserIdEmail)).thenReturn(null);
    int codeReturn = loginControllerService.addedconnection(
      newUserIdEmail,
      userConnected,
      newUserBirthDate,
      newUserFirstName,
      newUserNameUser
    );
    assertEquals(404, codeReturn);
  }

  @Test
  public void testAddedConnectionSomeEmail() {
    when(usersService.getUser(userConnectedIdEmail)).thenReturn(userConnected);
    when(users.getIdEmail()).thenReturn(userConnectedIdEmail);
    int codeReturn = loginControllerService.addedconnection(
      userConnectedIdEmail,
      userConnected,
      newUserBirthDate,
      newUserFirstName,
      newUserNameUser
    );
    assertEquals(406, codeReturn);
  }

  @Test
  public void testAddedConnectionAlreadyBuddyExisting() {
    when(usersService.getUser(newUserIdEmail)).thenReturn(newUser);
    when(users.getIdEmail()).thenReturn(userConnectedIdEmail);

    friends = new Friends();
    friends.setUsersIdUsers(1);
    friends.setUsers(newUser);

    when(friendsService.getFriend(1, 2)).thenReturn(friends);
    int codeReturn = loginControllerService.addedconnection(
      newUserIdEmail,
      userConnected,
      newUserBirthDate,
      newUserFirstName,
      newUserNameUser
    );
    assertEquals(304, codeReturn);
  }

  @Test
  public void testAddedConnectionAddBuddy() {
    when(usersService.getUser(newUserIdEmail)).thenReturn(newUser);
    when(users.getIdEmail()).thenReturn(userConnectedIdEmail);
    when(friendsService.getFriend(1, 2)).thenReturn(null);
    int codeReturn = loginControllerService.addedconnection(
      newUserIdEmail,
      userConnected,
      newUserBirthDate,
      newUserFirstName,
      newUserNameUser
    );
    assertEquals(201, codeReturn);
  }

  @Test
  public void testAddedConnectionAddBuddyAnotherDate() {
    when(usersService.getUser(newUserIdEmail)).thenReturn(newUser);
    when(users.getIdEmail()).thenReturn(userConnectedIdEmail);
    when(friendsService.getFriend(1, 2)).thenReturn(null);
    newUserBirthDate = "1901-12-31"; // other date
    int codeReturn = loginControllerService.addedconnection(
      newUserIdEmail,
      userConnected,
      newUserBirthDate,
      newUserFirstName,
      newUserNameUser
    );
    assertEquals(206, codeReturn);
  }

  @Test
  public void testAddedConnectionAddBuddyAnotherFirstName() {
    when(usersService.getUser(newUserIdEmail)).thenReturn(newUser);
    when(users.getIdEmail()).thenReturn(userConnectedIdEmail);
    when(friendsService.getFriend(1, 2)).thenReturn(null);
    newUserFirstName = "otherFirstName"; // other first name
    int codeReturn = loginControllerService.addedconnection(
      newUserIdEmail,
      userConnected,
      newUserBirthDate,
      newUserFirstName,
      newUserNameUser
    );
    assertEquals(206, codeReturn);
  }

  @Test
  public void testAddedConnectionAddBuddyAnotherNameUser() {
    when(usersService.getUser(newUserIdEmail)).thenReturn(newUser);
    when(users.getIdEmail()).thenReturn(userConnectedIdEmail);
    when(friendsService.getFriend(1, 2)).thenReturn(null);
    newUserNameUser = "otherNameUser"; // other name user
    int codeReturn = loginControllerService.addedconnection(
      newUserIdEmail,
      userConnected,
      newUserBirthDate,
      newUserFirstName,
      newUserNameUser
    );
    assertEquals(206, codeReturn);
  }

  @Test
  public void testSelectedConnectionPaid() {
    when(
      creationTransactionService.createTransaction(
        userConnected,
        "connections",
        "10",
        "description"
      )
    )
      .thenReturn(true);

    int codeReturn = loginControllerService.selectedConnection(
      userConnected,
      "connections",
      "10",
      "description"
    );
    assertEquals(201, codeReturn);
  }

  @Test
  public void testSelectedConnectionNoPaid() {
    when(
      creationTransactionService.createTransaction(
        userConnected,
        "connections",
        "10",
        "description"
      )
    )
      .thenReturn(false);

    int codeReturn = loginControllerService.selectedConnection(
      userConnected,
      "connections",
      "10",
      "description"
    );
    assertEquals(404, codeReturn);
  }
}
