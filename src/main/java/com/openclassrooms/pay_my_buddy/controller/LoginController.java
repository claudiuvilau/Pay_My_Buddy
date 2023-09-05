package com.openclassrooms.pay_my_buddy.controller;

import com.openclassrooms.pay_my_buddy.configuration.SpringSecurityConfig;
import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.NameTransactions;
import com.openclassrooms.pay_my_buddy.model.RequestClass;
import com.openclassrooms.pay_my_buddy.model.Roles;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.repository.UsersServiceInterface;
import com.openclassrooms.pay_my_buddy.service.FriendsService;
import com.openclassrooms.pay_my_buddy.service.LoginControllerService;
import com.openclassrooms.pay_my_buddy.service.NameTransactionsService;
import com.openclassrooms.pay_my_buddy.service.SetGetStatusModelAndView;
import com.openclassrooms.pay_my_buddy.service.TransactionsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class LoginController {

  ModelAndView modelAndView = new ModelAndView();

  @Autowired
  private UsersServiceInterface usersService; // instance of object

  @Autowired
  private FriendsService friendsService; // instance of object

  @Autowired
  private TransactionsService transactionsService; // instance of object

  @Autowired
  private NameTransactionsService nameTransactionsService; // instance of object

  @Autowired
  Users userNew;

  @Autowired
  Roles roles;

  @Autowired
  SpringSecurityConfig springSecurityConfig;

  @Autowired
  private RequestClass requestClass;

  @Autowired
  private LoginControllerService loginControllerService;

  @Autowired
  private SetGetStatusModelAndView setGetStatusModelAndView;

  private static final String PAGE_ACCUEIL = "accueil.html";
  private static final String PRENOM = "prenom";
  private static final String AMOUNT = "amount";
  private static final String CONNECTIONS = "connections";

  public LoginController() {}

  public LoginController(
    ModelAndView modelAndView,
    UsersServiceInterface usersService,
    FriendsService friendsService,
    TransactionsService transactionsService,
    NameTransactionsService nameTransactionsService,
    Users userNew,
    Roles roles,
    SpringSecurityConfig springSecurityConfig
  ) {
    this.modelAndView = modelAndView;
    this.usersService = usersService;
    this.friendsService = friendsService;
    this.transactionsService = transactionsService;
    this.nameTransactionsService = nameTransactionsService;
    this.userNew = userNew;
    this.roles = roles;
    this.springSecurityConfig = springSecurityConfig;
  }

  public ModelAndView getModelAndView() {
    return modelAndView;
  }

  public void setModelAndView(ModelAndView modelAndView) {
    this.modelAndView = modelAndView;
  }

  public UsersServiceInterface getUsersService() {
    return usersService;
  }

  public void setUsersService(UsersServiceInterface usersService) {
    this.usersService = usersService;
  }

  public FriendsService getFriendsService() {
    return friendsService;
  }

  public void setFriendsService(FriendsService friendsService) {
    this.friendsService = friendsService;
  }

  public TransactionsService getTransactionsService() {
    return transactionsService;
  }

  public void setTransactionsService(TransactionsService transactionsService) {
    this.transactionsService = transactionsService;
  }

  public NameTransactionsService getNameTransactionsService() {
    return nameTransactionsService;
  }

  public void setNameTransactionsService(
    NameTransactionsService nameTransactionsService
  ) {
    this.nameTransactionsService = nameTransactionsService;
  }

  public Users getUserNew() {
    return userNew;
  }

  public void setUserNew(Users userNew) {
    this.userNew = userNew;
  }

  public Roles getRoles() {
    return roles;
  }

  public void setRoles(Roles roles) {
    this.roles = roles;
  }

  public SpringSecurityConfig getSpringSecurityConfig() {
    return springSecurityConfig;
  }

  public void setSpringSecurityConfig(
    SpringSecurityConfig springSecurityConfig
  ) {
    this.springSecurityConfig = springSecurityConfig;
  }

  @Secured("USER")
  @GetMapping("/*")
  public ModelAndView afterLogin(
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    modelAndView.setViewName(PAGE_ACCUEIL);
    modelAndView = modelHome(model, user);

    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());
    return modelAndView;
  }

  @Secured("USER")
  @GetMapping("/addconnection")
  public ModelAndView addConnection(
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    modelAndView.setViewName(PAGE_ACCUEIL);
    modelAndView = modelHome(model, user);

    model.addAttribute("addConn", true);

    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());
    return modelAndView;
  }

  @Secured("USER")
  @PostMapping("/addedconnection")
  public ModelAndView addedConnection(
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    requestClass.setRequest(request);

    // récupérer ID email de la personne connectée et la personne connectée
    Users nameUser = recupererNameUser(user);

    modelAndView.setViewName(PAGE_ACCUEIL);

    // récupérer ID de l'ami saisi
    String idUserBuddy = requestClass.requestParameter("email");

    String dateN = requestClass.requestParameter("dateDeNaissance");

    String firstName = requestClass.requestParameter(PRENOM);

    String lastName = requestClass.requestParameter("nom");

    int statusAddedConnection = loginControllerService.addedconnection(
      idUserBuddy,
      nameUser,
      dateN,
      firstName,
      lastName
    );

    setGetStatusModelAndView.setSetStatus(statusAddedConnection);
    setGetStatusModelAndView.setHttpStatus(
      HttpStatus.valueOf(statusAddedConnection)
    );

    String msgResultat;

    if (statusAddedConnection == 201) {
      msgResultat =
        "Vous venez d'ajouter : " +
        requestClass.requestParameter(PRENOM) +
        " (résultat : " +
        setGetStatusModelAndView.getSetStatus() +
        ")";
    } else if (statusAddedConnection == 206) {
      msgResultat =
        "Les données saisies sont erronées. Nous ne pouvons pas vous connecter avec la personne saisie.";
    } else if (statusAddedConnection == 304) {
      msgResultat =
        "Vous êtes déjà connecté avec la personne dont l'adresse e-mail vous avez siasi : " +
        idUserBuddy;
    } else if (statusAddedConnection == 404) {
      msgResultat = "Il n'y a aucun utilisateur avec l'adresse email saisie.";
    } else if (statusAddedConnection == 406) {
      msgResultat =
        "Vous avez saisi votre adresse email. Pour chercher l'un de vos amis il faut saisir son adresse email et ses données.";
    } else {
      msgResultat =
        "Error " +
        setGetStatusModelAndView.getSetStatus() +
        " - " +
        setGetStatusModelAndView.getHttpStatus();
    }

    model.addAttribute("addedConnection", msgResultat);
    List<Friends> friends = new ArrayList<>();

    friends.addAll(nameUser.getFriends());

    model.addAttribute("listBuddy", friends);

    model.addAttribute("addedConn", friends);

    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());
    return modelAndView;
  }

  @Secured("USER")
  @RequestMapping("/detailTotalAmount")
  public ModelAndView detailTotalAmount(
    @ModelAttribute("data") String data,
    @ModelAttribute("dataIdMail") String dataIdMail,
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    String myDetailOrOtherUserDetail;
    Users nameUser;
    // data 9999 = ADMIN
    if (!data.equals("") && Integer.parseInt(data) == 9999) {
      // recupérer User
      nameUser = usersService.getUser(dataIdMail);
      myDetailOrOtherUserDetail =
        "Account details of " +
        nameUser.getNameUser() +
        " " +
        nameUser.getFirstName();
    } else {
      // récupérer ID email de la personne connectée et la personne connectée
      nameUser = recupererNameUser(user);
      myDetailOrOtherUserDetail = "My Details of Account";
    }

    modelAndView.setViewName(PAGE_ACCUEIL);
    modelAndView = modelHome(model, user);

    modelAndView = addAttibuteDetailTotalAmount(nameUser, model);

    model.addAttribute("addDetailSolde", true);
    model.addAttribute("myDetailOrOtherUserDetail", myDetailOrOtherUserDetail);

    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());
    return modelAndView;
  }

  private ModelAndView addAttibuteDetailTotalAmount(
    Users nameUser,
    Model model
  ) {
    // details account
    List<CostsDetailsTransactions> listCostsUserToBuddy;
    // true = for all transactions without paied to buddy
    listCostsUserToBuddy =
      transactionsService.detailTransForUser(nameUser, true);

    if (listCostsUserToBuddy == null) {
      int setStatus = 204;
      setGetStatusModelAndView.setSetStatus(setStatus);
      setGetStatusModelAndView.setHttpStatus(HttpStatus.NO_CONTENT);
      return modelAndView;
    }

    // sort DESC
    Comparator<CostsDetailsTransactions> comparator = (c1, c2) ->
      Integer.compare(c2.getId(), c1.getId());
    Collections.sort(listCostsUserToBuddy, comparator);

    model.addAttribute("allTrans", listCostsUserToBuddy);

    return modelAndView;
  }

  @Secured("USER")
  @GetMapping("/selectconnection")
  public ModelAndView selectConnection(
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    // récupérer ID email de la personne connectée et la personne connectée
    Users nameUser = recupererNameUser(user);
    modelAndView.setViewName(PAGE_ACCUEIL);
    modelAndView = modelHome(model, user);

    model.addAttribute("selectConn", true);

    modelAndView = addAttributeSelectConnection(nameUser, model, request);

    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());
    return modelAndView;
  }

  private ModelAndView addAttributeSelectConnection(
    Users nameUser,
    Model model,
    HttpServletRequest request
  ) {
    requestClass.setRequest(request);

    List<Friends> listFriends = new ArrayList<>();
    listFriends.addAll(nameUser.getFriends());

    model.addAttribute("listBuddy", listFriends);

    List<NameTransactions> listNameTransactions = new ArrayList<>();

    // récupérer seuelemnt les ID 1 et 2 name transaction : Verser (sur son compte
    // api) et Transférer (sur le compte bancaire)

    for (int i = 1; i < 3; i++) {
      Optional<NameTransactions> nameTransactionsOpt = nameTransactionsService.getNameTransactionById(
        i
      );
      if (nameTransactionsOpt.isPresent()) {
        NameTransactions nameTrans = nameTransactionsOpt.get();
        listNameTransactions.add(nameTrans);
      }
    }
    model.addAttribute("listNameTransactions", listNameTransactions);

    double valueAmount = 0;
    String selectedOption = "--";
    String valueSelectedOption = "";
    if (requestClass.requestParameter(AMOUNT) != null) {
      valueAmount = Double.parseDouble(requestClass.requestParameter(AMOUNT));
      selectedOption =
        usersService
          .getUser(requestClass.requestParameter(CONNECTIONS))
          .getFirstName();
      valueSelectedOption = requestClass.requestParameter(CONNECTIONS);
    }
    model.addAttribute("selectedOption", selectedOption);
    model.addAttribute("valueSelectedOption", valueSelectedOption);
    model.addAttribute("valueOfAmount", valueAmount);

    return modelAndView;
  }

  @Secured("USER")
  @PostMapping("/paid")
  public ModelAndView selectedConnection(
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response
    //RedirectAttributes redirectAttributes
  ) {
    // récupérer ID email de la personne connectée et la personne connectée
    Users nameUser = recupererNameUser(user);
    modelAndView.setViewName(PAGE_ACCUEIL);
    modelAndView = modelHome(model, user);

    requestClass.setRequest(request);
    String typeTransConnection = requestClass.requestParameter(CONNECTIONS);
    String amount = requestClass.requestParameter(AMOUNT);
    String description = requestClass.requestParameter("description");

    if (typeTransConnection.contains("@") && description == null) {
      selectConnection(model, user, request, response);
      return modelAndView;
    }

    int status = loginControllerService.selectedConnection(
      nameUser,
      typeTransConnection,
      amount,
      description
    );

    setGetStatusModelAndView.setSetStatus(status);
    setGetStatusModelAndView.setHttpStatus(HttpStatus.valueOf(status));

    if (status == 201) {
      RedirectView redirectView = new RedirectView();
      int statusRedirect = 302;
      redirectView.setStatusCode(HttpStatusCode.valueOf(statusRedirect));
      //redirectAttributes.addFlashAttribute("setStatus", String.valueOf(201));
      redirectView.setUrl("/detailTotalAmount");
      return new ModelAndView(redirectView);
    }
    if (status == 404) {
      model.addAttribute(
        "addedConnection",
        "Votre transaction n'a pas abouti !"
      );
    }

    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());
    return modelAndView;
  }

  @Secured("USER")
  @GetMapping("/login")
  public ModelAndView login(
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    modelAndView.setViewName("login.html");

    setGetStatusModelAndView.setSetStatus(response.getStatus());
    setGetStatusModelAndView.setHttpStatus(
      HttpStatus.valueOf(setGetStatusModelAndView.getSetStatus())
    );
    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());
    return modelAndView;
  }

  @Secured("USER")
  @PostMapping("/register")
  public ModelAndView registerNewUser(
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response
  ) throws ParseException {
    requestClass.setRequest(request);

    // récupérer ID email
    String newUserMail = requestClass.requestParameter("username");
    // récupérer le password
    String newUserPassword = requestClass.requestParameter("password");
    // récupérer le prénom
    String newUserFirstName = requestClass.requestParameter(PRENOM);
    newUserFirstName = makeUpperCaseFirstLetter(newUserFirstName);
    // récuperer le nom
    String newUserLastName = requestClass.requestParameter("nom");
    // récuperer la date de naissance
    String newUserBirthDay = requestClass.requestParameter("dateDeNaissance");

    int status = loginControllerService.addNewUser(
      newUserMail,
      newUserPassword,
      newUserFirstName,
      newUserLastName,
      newUserBirthDay
    );

    setGetStatusModelAndView.setSetStatus(status);
    setGetStatusModelAndView.setHttpStatus(HttpStatus.valueOf(status));

    String msg;
    if (status == 201) {
      msg =
        userNew.getFirstName() +
        ", vous vous êtes bien enregistré avec l'adresse email (username) : " +
        userNew.getIdEmail() +
        " (" +
        setGetStatusModelAndView.getSetStatus() +
        ")";
    } else {
      msg = "err !";
    }

    modelAndView.setViewName("login.html");
    model.addAttribute("newUser", msg);

    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());
    return modelAndView;
  }

  private String makeUpperCaseFirstLetter(String newUserFirstName) {
    newUserFirstName =
      newUserFirstName.substring(0, 1).toUpperCase() +
      newUserFirstName.substring(1);
    return newUserFirstName;
  }

  @Secured({ "USER", "ADMIN" })
  @GetMapping("/admin")
  public ModelAndView getAdmin(
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    requestClass.setRequest(request);
    // récupérer ID email de la personne connectée et la personne connectée
    String idEmail = getUserInfo(user);
    Users nameUser = usersService.getUser(idEmail);

    modelAndView = addAttributeAdmin(nameUser, model);

    modelAndView.setViewName("admin.html");

    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());
    return modelAndView;
  }

  @Secured({ "USER", "ADMIN" })
  @PostMapping("/admin")
  public ModelAndView usersAccounts(
    Model model,
    Principal user,
    HttpServletRequest request,
    HttpServletResponse response,
    RedirectAttributes redirectAttributes
  ) {
    requestClass.setRequest(request);
    // récupérer ID email de la personne connectée et la personne connectée
    String idEmail = requestClass.requestParameter("usersaccounts");
    Users nameUser = usersService.getUser(idEmail);

    addAttibuteDetailTotalAmount(nameUser, model);

    modelAndView.setStatus(setGetStatusModelAndView.getHttpStatus());
    response.setStatus(setGetStatusModelAndView.getSetStatus());

    RedirectView redirectView = new RedirectView();
    int statusRedirect = 302;
    redirectView.setStatusCode(HttpStatusCode.valueOf(statusRedirect));
    redirectAttributes.addFlashAttribute("dataIdMail", idEmail);
    // data 9999 => ADMIN
    redirectAttributes.addFlashAttribute("data", 9999);
    redirectView.setUrl("/detailTotalAmount");
    return new ModelAndView(redirectView);
  }

  private ModelAndView addAttributeAdmin(Users nameUser, Model model) {
    model.addAttribute(
      "getUser",
      "Welcome Admin : " +
      nameUser.getFirstName() +
      " " +
      nameUser.getNameUser()
    );

    List<Users> listUsers = new ArrayList<>();
    listUsers = loginControllerService.getAllUsers(listUsers);
    //sort ASC
    listUsers.sort(Comparator.comparing(Users::getNameUser));

    model.addAttribute("listSelecteOption", listUsers);

    int setStatus = 200;
    setGetStatusModelAndView.setSetStatus(setStatus);
    setGetStatusModelAndView.setHttpStatus(HttpStatus.OK);

    return modelAndView;
  }

  private ModelAndView modelHome(Model model, Principal user) {
    // récupérer ID email de la personne connectée et la personne connectée
    Users nameUser = recupererNameUser(user);
    if (nameUser == null) {
      int setStatus = 204;
      setGetStatusModelAndView.setSetStatus(setStatus);
      setGetStatusModelAndView.setHttpStatus(HttpStatus.NO_CONTENT);
      return modelAndView;
    } else {
      int setStatus = 200;
      setGetStatusModelAndView.setSetStatus(setStatus);
      setGetStatusModelAndView.setHttpStatus(HttpStatus.OK);
    }
    model.addAttribute(
      "getUser",
      nameUser.getFirstName() + " " + nameUser.getNameUser()
    );

    List<CostsDetailsTransactions> listCostsUserToBuddy;
    // true = for all transactions without paied to buddy
    listCostsUserToBuddy =
      transactionsService.detailTransForUser(nameUser, false);
    if (listCostsUserToBuddy == null) {
      int setStatus = 204;
      setGetStatusModelAndView.setSetStatus(setStatus);
      setGetStatusModelAndView.setHttpStatus(HttpStatus.NO_CONTENT);
      return modelAndView;
    } else {
      int setStatus = 200;
      setGetStatusModelAndView.setSetStatus(setStatus);
      setGetStatusModelAndView.setHttpStatus(HttpStatus.OK);
    }

    // sort DESC
    Comparator<CostsDetailsTransactions> comparator = (c1, c2) ->
      Integer.compare(c2.getId(), c1.getId());
    Collections.sort(listCostsUserToBuddy, comparator);

    double debit;
    debit = transactionsService.debit(nameUser);
    double credit;
    credit = transactionsService.credit(nameUser);

    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(2);

    model.addAttribute("debitCredit", nf.format(credit - debit));
    model.addAttribute("getCostsTrans", listCostsUserToBuddy);

    return modelAndView;
  }

  private Users recupererNameUser(Principal user) {
    String idEmail = getUserInfo(user);
    return usersService.getUser(idEmail);
  }

  public String getUserInfo(Principal user) {
    StringBuilder userInfo = new StringBuilder();
    if (user instanceof UsernamePasswordAuthenticationToken) {
      userInfo.append(getUsernamePasswordLoginInfo(user));
    }
    return userInfo.toString();
  }

  private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
    StringBuffer usernameInfo = new StringBuffer();

    UsernamePasswordAuthenticationToken token =
      ((UsernamePasswordAuthenticationToken) user);
    if (token.isAuthenticated()) {
      User u = (User) token.getPrincipal();
      usernameInfo.append(u.getUsername());
    } else {
      usernameInfo.append("NA");
    }
    return usernameInfo;
  }
}
