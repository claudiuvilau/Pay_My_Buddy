package com.openclassrooms.pay_my_buddy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public Iterable<Users> getUsers() {
        return usersRepository.findAll();
    }

    public Users getUser(String idMail) {
        Iterable<Users> usersList = getUsers();
        for (Users users : usersList) {
            if (users.getIdEmail().equals(idMail)) {
                return users;
            }
        }
        return null;
    }

    public Optional<Users> getUserById(Integer id) {
        return usersRepository.findById(id);
    }

    public Users addUser(Users users) {
        return usersRepository.save(users);
    }

    public void deleteUser(Users users) {
        usersRepository.delete(users);
    }

    public void deleteUserById(Integer id) {
        usersRepository.deleteById(id);
    }

}
