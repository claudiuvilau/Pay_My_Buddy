package com.openclassrooms.pay_my_buddy.service;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashPasswordService {

    private String mdp;

    public HashPasswordService() {
    }

    public HashPasswordService(String mdp) {
        this.mdp = mdp;
    }

    public String getMdp() {
        return this.mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String hashPassword(String pwHash) {

        String pwHashCrypt = BCrypt.hashpw(pwHash, BCrypt.gensalt());

        if (BCrypt.checkpw(pwHash, pwHashCrypt)) {
            System.out.println("Valide");
        } else {
            System.out.println("echec");
        }

        return pwHashCrypt;
    }

}
