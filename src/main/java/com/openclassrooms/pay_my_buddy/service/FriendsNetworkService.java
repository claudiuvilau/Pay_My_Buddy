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

    public FriendsNetwork getFriend(Integer usersIdUsers, Integer buddy) {
        Iterable<FriendsNetwork> friendsList = getFriends();
        for (FriendsNetwork friends : friendsList) {
            if (friends.getUsersIdUsers() == usersIdUsers && friends.getBuddy() == buddy) {
                return friends;
            }
        }
        return null;
    }

    public Optional<FriendsNetwork> getFriendById(Integer id) {
        return friendsNetworkRepository.findById(id);
    }

    public FriendsNetwork addFriendsNetwork(FriendsNetwork friendsNetwork) {
        return friendsNetworkRepository.save(friendsNetwork);
    }

    public void deleteFriend(FriendsNetwork friendsNetwork) {
        friendsNetworkRepository.delete(friendsNetwork);
    }

    public void deleteFriendById(Integer id) {
        friendsNetworkRepository.deleteById(id);
    }

}
