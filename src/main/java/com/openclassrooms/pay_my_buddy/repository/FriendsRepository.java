package com.openclassrooms.pay_my_buddy.repository;

import org.springframework.data.repository.CrudRepository;

import com.openclassrooms.pay_my_buddy.model.Friends;

public interface FriendsRepository extends CrudRepository<Friends, Integer> {

}
