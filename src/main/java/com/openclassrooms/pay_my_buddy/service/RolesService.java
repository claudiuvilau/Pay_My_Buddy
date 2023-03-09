package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.pay_my_buddy.model.Roles;
import com.openclassrooms.pay_my_buddy.repository.RolesRepository;

@Service
public class RolesService {

    @Autowired
    RolesRepository rolesRepository;

    public Iterable<Roles> getRoles() {
        return rolesRepository.findAll();
    }

    public Optional<Roles> getRoleById(Integer id) {
        return rolesRepository.findById(id);
    }

    public Roles addRole(Roles roles) {
        return rolesRepository.save(roles);
    }

    public void deleteRole(Roles roles) {
        rolesRepository.delete(roles);
    }

    public void deleteRoleById(Integer id) {
        rolesRepository.deleteById(id);
    }

}
