package com.openclassrooms.pay_my_buddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.pay_my_buddy.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {

}