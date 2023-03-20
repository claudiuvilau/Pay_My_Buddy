package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles")
    private int idRoles;

    @Column(name = "name_role")
    private String nameRole;

    @OneToMany
    @JoinColumn(name = "role_id")
    List<Users> usersList = new ArrayList<>();

    public int getIdRoles() {
        return this.idRoles;
    }

    public void setIdRoles(int idRoles) {
        this.idRoles = idRoles;
    }

    public String getNameRole() {
        return this.nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public List<Users> getUsersList() {
        return this.usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

}
