package com.openclassrooms.pay_my_buddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.repository.FriendsRepository;

@Service
@Transactional
public class FriendsService {

    @Autowired
    FriendsRepository friendsRepository;

    public Iterable<Friends> getFriends() {
        return friendsRepository.findAll();
    }

    public Friends getFriend(int idUsers, int idBuddy) {
        Iterable<Friends> friendsList = getFriends();
        for (Friends friends : friendsList) {
            if (friends.getUsersIdUsers() == idUsers && friends.getUsers().getIdUsers() == idBuddy) {
                return friends;
            }
        }
        return null;
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
