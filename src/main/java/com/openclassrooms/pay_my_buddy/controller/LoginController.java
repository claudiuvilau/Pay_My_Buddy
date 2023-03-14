package com.openclassrooms.pay_my_buddy.controller;

import java.security.Principal;
import java.util.Date;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.NameTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.TypeTransactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.FriendsService;
import com.openclassrooms.pay_my_buddy.service.NameTransactionsService;
import com.openclassrooms.pay_my_buddy.service.ServiceTransactionl;
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
    private ServiceTransactionl serviceTransactionl; // instance of object

    @RolesAllowed("USER")
    @RequestMapping("/*")
    public ModelAndView afterLogin(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) {

        modelAndView.setViewName("accueil.html");
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
    @RequestMapping("/transfer")
    public ModelAndView home() {
        modelAndView.setViewName("connexion.html");
        return modelAndView;
    }

    @RolesAllowed("USER")
    @RequestMapping("/addconnection")
    public ModelAndView addConnection(Model model, Principal user) {
        modelAndView.setViewName("accueil.html");
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

        modelAndView.setViewName("accueil.html");

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

        modelAndView.setViewName("accueil.html");
        modelAndView = modelHome(model, user);

        // details account
        List<CostsDetailsTransactions> listCostsUserToBuddy;
        // true = for all transactions without paied to buddy
        listCostsUserToBuddy = transactionsService.detailTransForUser(nameUser, true);
        // listCostsUserToBuddy.addAll(transactionsService.detailTransForUser(nameUser,
        // false));
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
        modelAndView.setViewName("accueil.html");
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
        modelAndView = modelHome(model, user);

        modelAndView.setViewName("accueil.html");
        modelAndView = modelHome(model, user);

        // date du jour de la transaction
        Date dateTransNow = new Date();

        // si nom transaction choisi est Verser => c'est 1
        if (request.getParameter("connections").equalsIgnoreCase("1")) {

            Transactions transaction = new Transactions();
            transaction.setDateTrans(dateTransNow);
            transaction.setInvoiced(false);
            transaction.setUser(nameUser.getIdUsers());

            CostsDetailsTransactions costsDetailsTransaction = new CostsDetailsTransactions();
            costsDetailsTransaction.setAmount(Double.parseDouble(request.getParameter("amount")));
            // to user => nameUser courent pour VERSER
            costsDetailsTransaction.setUsers(nameUser);
            costsDetailsTransaction.setTransactions(transaction);

            // type transaction
            TypeTransactions typeTransaction = new TypeTransactions();
            typeTransaction.setIdTypeTrans(1);

            costsDetailsTransaction.setTypeTransactions(typeTransaction);

            // nom transaction
            NameTransactions nameTransaction = new NameTransactions();
            nameTransaction.setIdNameTrans(Integer.parseInt(request.getParameter("connections")));

            costsDetailsTransaction.setNameTransactions(nameTransaction);

            serviceTransactionl.updateTableTransactionsAndCostsDetailsTransactions(transaction,
                    costsDetailsTransaction);
        }
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/detailTotalAmount");

        return new ModelAndView(redirectView);
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
