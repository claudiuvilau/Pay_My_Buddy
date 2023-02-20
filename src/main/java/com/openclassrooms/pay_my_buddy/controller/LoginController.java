package com.openclassrooms.pay_my_buddy.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;

@RestController
public class LoginController {
    @RolesAllowed("USER")
    @RequestMapping("/**")
    public String getUser() {
        return "Welcome User";
    }

    @RolesAllowed({ "USER", "ADMIN" })
    @RequestMapping("/admin")
    public String getAdmin() {
        return "Welcome Admin";
    }

    @RequestMapping("/*")
    public String getGithub(Principal user) {
        return "Welcome Github user " + user.getName();
    }

    @GetMapping("/persons")
    public String getEssai() {
        return "Essai du test";

    }
}
