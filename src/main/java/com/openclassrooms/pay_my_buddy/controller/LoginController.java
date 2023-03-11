package com.openclassrooms.pay_my_buddy.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.FriendsService;
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

    @RolesAllowed("USER")
    @RequestMapping("/*")
    public ModelAndView afterLogin(Model model, Principal user) {

        modelAndView.setViewName("accueil.html");

        // récupérer ID email de la personne connectée et la personne connectée
        String idEmail = getUserInfo(user);
        Users nameUser = usersService.getUser(idEmail);
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
    @RequestMapping("/add_connection")
    public ModelAndView addConnection() {
        modelAndView.setViewName("add_connection.html");

        return modelAndView;
    }

    @RolesAllowed("USER")
    @PostMapping("/addedconnection")
    public ModelAndView addedConnection(Model model, Principal user, HttpServletRequest request,
            HttpServletResponse response) {

        // récupérer ID email de la personne connectée et la personne connectée
        String idEmail = getUserInfo(user);
        Users nameUser = usersService.getUser(idEmail);
        // récupérer ID de l'ami saisi saisi
        Users idUserBuddy = usersService.getUser(request.getParameter("email"));
        if (idUserBuddy != null) {
            boolean idFriend = false;
            idFriend = verifierIdEmail(idUserBuddy, request);
            if (idFriend) {
                Friends friend = new Friends();
                friend.setUsersIdUsers(nameUser.getIdUsers());
                friend.setUsers(idUserBuddy);
                friendsService.addFriends(friend);
                response.setStatus(201);
            } else {
                response.setStatus(204);
            }
        } else {
            response.setStatus(404);
        }

        return addedConnectionGet(model, request, response, nameUser);
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
    @GetMapping("/addedconnection")
    public ModelAndView addedConnectionGet(Model model, HttpServletRequest request, HttpServletResponse response,
            Users nameUser) {

        modelAndView.setViewName("addedconnection.html");
        model.addAttribute("addedConnection",
                "Vous venez d'ajouter : " + request.getParameter("prenom") + " (résultat : "
                        + HttpStatus.valueOf(response.getStatus()) + ")");

        List<Friends> friends = new ArrayList<>();

        friends.addAll(nameUser.getFriends());

        model.addAttribute("listBuddy", friends);

        return modelAndView;
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
