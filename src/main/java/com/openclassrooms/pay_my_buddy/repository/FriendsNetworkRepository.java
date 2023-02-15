package com.openclassrooms.pay_my_buddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.pay_my_buddy.model.FriendsNetwork;

@Repository
public interface FriendsNetworkRepository extends CrudRepository<FriendsNetwork, Integer> {

}
