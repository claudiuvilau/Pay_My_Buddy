package com.openclassrooms.pay_my_buddy.repository;

import java.util.Optional;

import com.openclassrooms.pay_my_buddy.model.Users;

public interface UsersServiceInterface {

    public UsersRepository getUsersRepository();

    public void setUsersRepository(UsersRepository usersRepository);

    public Iterable<Users> getUsers();

    public Users getUser(String idMail);

    public Optional<Users> getUserById(Integer id);

    public Users addUser(Users users);

    public void deleteUser(Users users);

    public void deleteUserById(Integer id);

}
