package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.repository.FriendsRepository;

@Service
public class FriendsService {

    @Autowired
    FriendsRepository friendsRepository;

    public Iterable<Friends> getFriends() {
        return friendsRepository.findAll();
    }

    public Optional<Friends> getFriendById(Integer id) {
        return friendsRepository.findById(id);
    }

    public Friends addFriends(Friends friends) {
        return friendsRepository.save(friends);
    }

    public void deleteFriend(Friends friends) {
        friendsRepository.delete(friends);
    }

    public void deleteFriendById(Integer id) {
        friendsRepository.deleteById(id);
    }

}
