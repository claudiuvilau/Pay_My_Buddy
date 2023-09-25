package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.configuration.SpringSecurityConfig;
import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.Roles;
import com.openclassrooms.pay_my_buddy.model.Users;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginControllerService {

  @Autowired
  private SpringSecurityConfig springSecurityConfig;

  @Autowired
  private CreationTransactionService creationTransactionService;

  @Autowired
  private UsersService usersService;

  @Autowired
  private FriendsService friendsService; // instance of object

  public LoginControllerService() {}

  public LoginControllerService(
    SpringSecurityConfig springSecurityConfig,
    CreationTransactionService creationTransactionService,
    UsersService usersService,
    FriendsService friendsService
  ) {
    this.springSecurityConfig = springSecurityConfig;
    this.creationTransactionService = creationTransactionService;
    this.usersService = usersService;
    this.friendsService = friendsService;
  }

  public FriendsService getFriendsService() {
    return this.friendsService;
  }

  public void setFriendsService(FriendsService friendsService) {
    this.friendsService = friendsService;
  }

  public int addNewUser(
    String newUserMail,
    String newUserPassword,
    String newUserFirstName,
    String newUserLastName,
    String newUserBirthDay
  ) throws ParseException {
    newUserMail = newUserMail.toLowerCase().trim();

    // verify if register is ok
    int status;
    if (usersService.getUser(newUserMail) == null) {
      newUserPassword =
        springSecurityConfig
          .bCryptPasswordEncoder()
          .encode(newUserPassword.trim());
      newUserFirstName = newUserFirstName.toLowerCase().trim();
      newUserLastName = newUserLastName.toLowerCase().toLowerCase().trim();
      SimpleDateFormat formatter = new SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.ENGLISH
      );
      Date newUserDateBirthDay = formatter.parse(newUserBirthDay);

      int roleId = 2; // 1 = admin; 2 = user
      Roles roles = new Roles();
      roles.setIdRoles(roleId);
      Users userNew = new Users();
      userNew.setIdEmail(newUserMail);
      userNew.setPassword(newUserPassword);
      userNew.setFirstName(newUserFirstName);
      userNew.setNameUser(newUserLastName);
      userNew.setBirthDate(newUserDateBirthDay);
      userNew.setRole(roles);

      usersService.addUser(userNew);

      status = 201;
    } else {
      status = 404;
    }
    return status;
  }

  public int addedconnection(
    String idUserBuddy,
    Users nameUser,
    String dateN,
    String firstName,
    String lastName
  ) {
    // récupérer ID de l'ami saisi
    Users userBuddy = usersService.getUser(idUserBuddy);
    int status = 0;
    if (userBuddy != null) {
      if (nameUser.getIdEmail().equalsIgnoreCase(idUserBuddy)) {
        // email of the user connected is typed
        status = 406;
      } else {
        Friends friendOfNameUser = friendsService.getFriend(
          nameUser.getIdUsers(),
          userBuddy.getIdUsers()
        );
        if (friendOfNameUser != null) {
          // connexion not created because this is already existing
          status = 304;
        } else {
          boolean idFriend = false;
          idFriend = verifyIdEmail(userBuddy, dateN, firstName, lastName);
          if (idFriend) {
            Friends friend = new Friends();
            friend.setUsersIdUsers(nameUser.getIdUsers());
            friend.setUsers(userBuddy);
            friendsService.addFriends(friend);
            status = 201;
          } else {
            status = 206;
          }
        }
      }
    } else {
      status = 404;
    }
    return status;
  }

  private boolean verifyIdEmail(
    Users userBuddy,
    String dateN,
    String firstName,
    String lastName
  ) {
    boolean ifFriendExist = false;

    // format date yyyy-MM-dd car ce format dans le formulaire dateN
    LocalDate date = userBuddy
      .getBirthDate()
      .toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
    DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String dateB = dtformat.format(date);

    if (
      dateB.equals(dateN) &&
      userBuddy.getFirstName().equalsIgnoreCase(firstName) &&
      userBuddy.getNameUser().equalsIgnoreCase(lastName)
    ) {
      ifFriendExist = true;
    }
    return ifFriendExist;
  }

  public int selectedConnection(
    Users nameUser,
    String typeTransConnection,
    String amount,
    String description
  ) {
    int setStatus;
    boolean addedTrans = false;
    addedTrans =
      creationTransactionService.createTransaction(
        nameUser,
        typeTransConnection,
        amount,
        description
      );
    if (addedTrans) {
      setStatus = 201;
    } else {
      setStatus = 404;
    }
    return setStatus;
  }

  /**
   * @return SpringSecurityConfig return the springSecurityConfig
   */
  public SpringSecurityConfig getSpringSecurityConfig() {
    return springSecurityConfig;
  }

  /**
   * @param springSecurityConfig the springSecurityConfig to set
   */
  public void setSpringSecurityConfig(
    SpringSecurityConfig springSecurityConfig
  ) {
    this.springSecurityConfig = springSecurityConfig;
  }

  /**
   * @return CreationTransactionService return the creationTransactionService
   */
  public CreationTransactionService getCreationTransactionService() {
    return creationTransactionService;
  }

  /**
   * @param creationTransactionService the creationTransactionService to set
   */
  public void setCreationTransactionService(
    CreationTransactionService creationTransactionService
  ) {
    this.creationTransactionService = creationTransactionService;
  }

  /**
   * @return UsersService return the usersService
   */
  public UsersService getUsersService() {
    return usersService;
  }

  /**
   * @param usersService the usersService to set
   */
  public void setUsersService(UsersService usersService) {
    this.usersService = usersService;
  }

  public List<Users> getAllUsers(List<Users> listUsers) {
    //get the users
    Iterable<Users> users = usersService.getUsers();
    users.forEach(listUsers::add);
    return listUsers;
  }
}
