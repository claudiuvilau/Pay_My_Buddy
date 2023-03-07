package com.openclassrooms.pay_my_buddy.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.UsersService;

import jakarta.annotation.security.RolesAllowed;

@RestController
public class LoginController {

    ModelAndView modelAndView = new ModelAndView();

    @Autowired
    private UsersService usersService; // instance of object

    @RolesAllowed("USER")
    @RequestMapping("/*")
    public ModelAndView afterLogin(Model model, Principal user) {
        modelAndView.setViewName("accueil.html");
        String idEmail = getUserInfo(user);
        Users nameUser = usersService.getUser(idEmail);
        model.addAttribute("getUser", nameUser.getFirstName() + " " + nameUser.getNameUser());

        // dates transactions
        // model.addAttribute("getTrans", nameUser.getTransactionsList());

        // list costs : Amount
        List<CostsDetailsTransactions> listCosts = new ArrayList<>();
        for (int i = 0; i < nameUser.getTransactionsList().size(); i++) {
            listCosts.addAll(nameUser.getTransactionsList().get(i).getCostsDetailsTransactionsList());
        }
        model.addAttribute("getCostsTrans", listCosts);

        return modelAndView;
    }

    @RolesAllowed("USER")
    @RequestMapping("/transfer")
    public ModelAndView home() {
        modelAndView.setViewName("connexion.html");
        return modelAndView;
    }

    @RolesAllowed({ "USER", "ADMIN" })
    @RequestMapping("/admin")
    public String getAdmin() {
        return "Welcome Admin";
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
