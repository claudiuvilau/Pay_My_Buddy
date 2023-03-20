package com.openclassrooms.pay_my_buddy.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.openclassrooms.pay_my_buddy.configuration.SpringSecurityConfig;
import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.NameTransactions;
import com.openclassrooms.pay_my_buddy.model.Roles;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.CreationTransactionService;
import com.openclassrooms.pay_my_buddy.service.FriendsService;
import com.openclassrooms.pay_my_buddy.service.NameTransactionsService;
import com.openclassrooms.pay_my_buddy.service.TransactionsService;
import com.openclassrooms.pay_my_buddy.service.UsersService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    ModelAndView modelAndView = new ModelAndView();

    @Autowired
    private UsersService usersService; // instance of object

    @Autowired
    private FriendsService friendsService; // instance of object

    @Autowired
    private TransactionsService transactionsService; // instance of object

    @Autowired
    private NameTransactionsService nameTransactionsService; // instance of object

    @Autowired
    private CreationTransactionService creationTransactionService;

    @Autowired
    Users userNew;

    @Autowired
    Roles roles;

    @Autowired
    SpringSecurityConfig springSecurityConfig;

    private static final String PAGE_ACCUEIL = "accueil.html";

    @RolesAllowed("USER")
    @RequestMapping("/*")
    public ModelAndView afterLogin(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) {

        modelAndView.setViewName(PAGE_ACCUEIL);
        modelAndView = modelHome(model, user);

        return modelAndView;
    }

    private ModelAndView modelHome(Model model, Principal user) {

        // récupérer ID email de la personne connectée et la personne connectée
        Users nameUser = recupererNameUser(user);
        model.addAttribute("getUser", nameUser.getFirstName() + " " + nameUser.getNameUser());

        List<CostsDetailsTransactions> listCostsUserToBuddy;
        // true = for all transactions without paied to buddy
        listCostsUserToBuddy = transactionsService.detailTransForUser(nameUser, false);

        double debit;
        debit = transactionsService.debit(nameUser);
        double credit;
        credit = transactionsService.credit(nameUser);

        model.addAttribute("debitCredit", credit - debit);
        model.addAttribute("getCostsTrans", listCostsUserToBuddy);

        return modelAndView;
    }

    @RolesAllowed("USER")
    @RequestMapping("/addconnection")
    public ModelAndView addConnection(Model model, Principal user) {
        modelAndView.setViewName(PAGE_ACCUEIL);
        modelAndView = modelHome(model, user);

        model.addAttribute("addConn", true);

        return modelAndView;
    }

    @RolesAllowed("USER")
    @PostMapping("/addedconnection")
    public ModelAndView addedConnection(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) {

        // récupérer ID email de la personne connectée et la personne connectée
        Users nameUser = recupererNameUser(user);

        modelAndView.setViewName(PAGE_ACCUEIL);

        String msgResultat = "";

        // récupérer ID de l'ami saisi saisi
        Users idUserBuddy = usersService.getUser(request.getParameter("email"));
        if (idUserBuddy != null) {
            if (nameUser.getIdEmail().equalsIgnoreCase(idUserBuddy.getIdEmail())) {
                msgResultat = "Vous avez siasi votre adresse email. Pour chercher l'un de vos amis il faut saisir son adresse email et ses données.";
            } else {

                Friends friendOfNameUser = friendsService.getFriend(nameUser.getIdUsers(), idUserBuddy.getIdUsers());
                if (friendOfNameUser != null) {
                    msgResultat = "Vous êtes déjà connecté avec " + idUserBuddy.getFirstName() + " "
                            + idUserBuddy.getNameUser();
                } else {
                    boolean idFriend = false;
                    idFriend = verifierIdEmail(idUserBuddy, request);
                    if (idFriend) {
                        Friends friend = new Friends();
                        friend.setUsersIdUsers(nameUser.getIdUsers());
                        friend.setUsers(idUserBuddy);
                        friendsService.addFriends(friend);
                        response.setStatus(201);
                        msgResultat = "Vous venez d'ajouter : " + request.getParameter("prenom") + " (résultat : "
                                + HttpStatus.valueOf(response.getStatus()) + ")";
                    } else {
                        msgResultat = "Les données saiseis sont erronées. Nous ne pouvons pas vous connecter avec la personne saisie.";
                    }
                }
            }
        } else {
            msgResultat = "Il n'y a aucun utilisateur avec l'adresse email saisie.";
        }

        model.addAttribute("addedConnection", msgResultat);
        List<Friends> friends = new ArrayList<>();

        friends.addAll(nameUser.getFriends());

        model.addAttribute("listBuddy", friends);

        model.addAttribute("addedConn", friends);

        return modelAndView;
    }

    @RolesAllowed("USER")
    @RequestMapping("/detailTotalAmount")
    public ModelAndView detailTotalAmount(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) {

        // récupérer ID email de la personne connectée et la personne connectée
        Users nameUser = recupererNameUser(user);

        modelAndView.setViewName(PAGE_ACCUEIL);
        modelAndView = modelHome(model, user);

        // details account
        List<CostsDetailsTransactions> listCostsUserToBuddy;
        // true = for all transactions without paied to buddy
        listCostsUserToBuddy = transactionsService.detailTransForUser(nameUser, true);
        model.addAttribute("addDetailSolde", true);
        model.addAttribute("allTrans", listCostsUserToBuddy);

        return modelAndView;
    }

    private boolean verifierIdEmail(Users idUserBuddy, HttpServletRequest request) {

        boolean ifFriendExist = false;

        // format date yyyy-MM-dd car ce format dans le formulaire dateN
        LocalDate date = idUserBuddy.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateB = dtformat.format(date);
        String dateN = request.getParameter("dateDeNaissance");
        if (dateB.equals(dateN)
                && idUserBuddy.getFirstName().equalsIgnoreCase(request.getParameter("prenom"))
                && idUserBuddy.getNameUser().equalsIgnoreCase(request.getParameter("nom"))) {

            ifFriendExist = true;
        }
        return ifFriendExist;
    }

    @RolesAllowed("USER")
    @GetMapping("/selectconnection")
    public ModelAndView selectConnection(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) {

        // récupérer ID email de la personne connectée et la personne connectée
        Users nameUser = recupererNameUser(user);
        modelAndView.setViewName(PAGE_ACCUEIL);
        modelAndView = modelHome(model, user);

        model.addAttribute("selectConn", true);

        List<Friends> listFriends = new ArrayList<>();
        listFriends.addAll(nameUser.getFriends());
        model.addAttribute("listBuddy", listFriends);

        List<NameTransactions> listNameTransactions = new ArrayList<>();

        // récupérer seuelemnt les ID 1 et 2 name transaction : Verser (sur son compte
        // api) et Transférer (sur le compte bancaire)

        for (int i = 1; i < 3; i++) {
            Optional<NameTransactions> nameTransactionsOpt = nameTransactionsService.getNameTransactionById(i);
            if (nameTransactionsOpt.isPresent()) {
                NameTransactions nameTrans = nameTransactionsOpt.get();
                listNameTransactions.add(nameTrans);
            }
        }
        model.addAttribute("listNameTransactions", listNameTransactions);

        return modelAndView;
    }

    @RolesAllowed("USER")
    @PostMapping("/paid")
    public ModelAndView selectedConnection(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) {

        // récupérer ID email de la personne connectée et la personne connectée
        Users nameUser = recupererNameUser(user);
        modelAndView.setViewName(PAGE_ACCUEIL);
        modelAndView = modelHome(model, user);

        String typeTransConnection = request.getParameter("connections");
        String amount = request.getParameter("amount");

        creationTransactionService.createTransaction(nameUser, typeTransConnection, amount);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/detailTotalAmount");

        return new ModelAndView(redirectView);
    }

    @RolesAllowed("USER")
    @GetMapping("/login")
    public ModelAndView login(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) {

        modelAndView.setViewName("login.html");

        return modelAndView;
    }

    @RolesAllowed("USER")
    @PostMapping("/login")
    public ModelAndView loginPost(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) {

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/*");

        return new ModelAndView(redirectView);
    }

    @RolesAllowed("USER")
    @PostMapping("/register")
    public ModelAndView registerNewUser(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) throws ParseException {

        // récupérer ID email
        String newUserMail = request.getParameter("username").toLowerCase().trim();
        String newUserPassword = springSecurityConfig.bCryptPasswordEncoder()
                .encode(request.getParameter("password").trim());
        String newUserFirstName = request.getParameter("prenom").toLowerCase().trim();
        newUserFirstName = makeUpperCaseFirstLetter(newUserFirstName);
        String newUserLastName = request.getParameter("nom").toUpperCase().trim();

        String newUserBirthDay = request.getParameter("dateDeNaissance");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newUserDateBirthDay = formatter.parse(newUserBirthDay);

        int roleId = 1; // 1 = user
        roles = new Roles();
        roles.setIdRoles(roleId);
        userNew = new Users();
        userNew.setIdEmail(newUserMail);
        userNew.setPassword(newUserPassword);
        userNew.setFirstName(newUserFirstName);
        userNew.setNameUser(newUserLastName);
        userNew.setBirthDate(newUserDateBirthDay);
        userNew.setRole(roles);

        usersService.addUser(userNew);

        // verify if register is ok
        String msg = "err !";
        if (usersService.getUser(newUserMail) != null) {
            msg = userNew.getFirstName() + ", vous vous êtes bien enregistré avec l'adresse email (username) : "
                    + userNew.getIdEmail() + " (" + HttpStatus.valueOf(response.getStatus()) + ")";
        }

        modelAndView.setViewName("login.html");
        model.addAttribute("newUser", msg);

        return modelAndView;
    }

    private String makeUpperCaseFirstLetter(String newUserFirstName) {
        newUserFirstName = newUserFirstName.substring(0, 1).toUpperCase() + newUserFirstName.substring(1);
        return newUserFirstName;
    }

    @RolesAllowed({ "USER", "ADMIN" })
    @RequestMapping("/admin")
    public ModelAndView getAdmin(Model model, Principal user) {

        modelAndView.setViewName("admin.html");

        // récupérer ID email de la personne connectée et la personne connectée
        String idEmail = getUserInfo(user);
        Users nameUser = usersService.getUser(idEmail);
        model.addAttribute("getUser", "Welcome Admin : " + nameUser.getFirstName() + " " + nameUser.getNameUser());

        // listes transactions
        List<CostsDetailsTransactions> listCostsUser;
        // true = for all transactions without paied to buddy
        listCostsUser = transactionsService.detailTransForUser(nameUser, true);
        model.addAttribute("getCostsTrans", listCostsUser);

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

        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
        if (token.isAuthenticated()) {
            User u = (User) token.getPrincipal();
            usernameInfo.append(u.getUsername());
        } else {
            usernameInfo.append("NA");
        }
        return usernameInfo;
    }

}
