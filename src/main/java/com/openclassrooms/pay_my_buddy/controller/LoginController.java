package com.openclassrooms.pay_my_buddy.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.TransactionsService;
import com.openclassrooms.pay_my_buddy.service.UsersService;

import jakarta.annotation.security.RolesAllowed;

@RestController
public class LoginController {

    ModelAndView modelAndView = new ModelAndView();

    @Autowired
    private UsersService usersService; // instance of object

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
    @RequestMapping("/addedconnection")
    public ModelAndView addedConnection(Model model, Principal user, @RequestParam(required = true) String nom,
            @RequestParam(required = true) String prenom, @RequestParam(required = true) Date dateDeNaissance,
            @RequestParam(required = true) String email) {

        // récupérer ID email de la personne connectée et la personne connectée
        String idEmail = getUserInfo(user);
        Users nameUser = usersService.getUser(idEmail);
        model.addAttribute("getUser", nameUser.getFirstName() + " " + nameUser.getNameUser());

        modelAndView.setViewName("addedconnection.html");

        model.addAttribute("addedConnection", "Vous venez d'ajouter : " + email);

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
