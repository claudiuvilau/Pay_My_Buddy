package com.openclassrooms.pay_my_buddy.service;

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

    public Optional<Users> getUserById(Integer id) {
        return usersRepository.findById(id);
    }

}
