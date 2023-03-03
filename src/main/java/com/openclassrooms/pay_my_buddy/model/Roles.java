package com.openclassrooms.pay_my_buddy.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles")
    private int idRoles;

    @Column(name = "role")
    private int role;

    @Column(name = "name_role")
    private int nameRole;

    @OneToMany
    @JoinColumn(name = "role_id")
    List<Users> usersList = new ArrayList<>();

    public int getIdRoles() {
        return this.idRoles;
    }

    public void setIdRoles(int idRoles) {
        this.idRoles = idRoles;
    }

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getNameRole() {
        return this.nameRole;
    }

    public void setNameRole(int nameRole) {
        this.nameRole = nameRole;
    }

}
