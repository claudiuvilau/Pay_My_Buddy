package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.pay_my_buddy.model.FriendsNetwork;
import com.openclassrooms.pay_my_buddy.repository.FriendsNetworkRepository;

@Service
public class FriendsNetworkService {

    @Autowired
    FriendsNetworkRepository friendsNetworkRepository;

    public Iterable<FriendsNetwork> getFriends() {
        return friendsNetworkRepository.findAll();
    }

    public Optional<FriendsNetwork> getFriendById(Integer id) {
        return friendsNetworkRepository.findById(id);
    }
}
