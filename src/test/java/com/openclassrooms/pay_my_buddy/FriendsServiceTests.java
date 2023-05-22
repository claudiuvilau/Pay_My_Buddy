package com.openclassrooms.pay_my_buddy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.repository.FriendsRepository;
import com.openclassrooms.pay_my_buddy.service.FriendsService;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class FriendsServiceTests {

  @Autowired
  private FriendsService friendsService;

  @MockBean
  Friends friends;

  @MockBean
  Users users;

  @MockBean
  FriendsRepository friendsRepository;

  @Test
  public void testGetFriend() {
    friends.setId(2);
    friends.setUsers(users);
    friends.setUsersIdUsers(1);

    Iterable<Friends> friendsList = new ArrayList<>();
    ((ArrayList<Friends>) friendsList).add(0, friends);

    when(friendsRepository.findAll()).thenReturn(friendsList);
    when(friends.getUsersIdUsers()).thenReturn(1);
    when(friends.getUsers()).thenReturn(users);
    when(friends.getUsers().getIdUsers()).thenReturn(2);

    Friends getFreind = friendsService.getFriend(1, 2);

    assertEquals(friends, getFreind);
  }

  @Test
  public void testGetFriendZeroFriends() {
    friends.setId(2);
    friends.setUsers(users);
    friends.setUsersIdUsers(1);

    when(friendsRepository.findAll()).thenReturn(null);
    when(friends.getUsersIdUsers()).thenReturn(1);
    when(friends.getUsers()).thenReturn(users);
    when(friends.getUsers().getIdUsers()).thenReturn(2);

    Friends getFreind = friendsService.getFriend(1, 2);

    assertEquals(null, getFreind);
  }

  @Test
  public void testGetFriendNotExist() {
    friends.setId(2);
    friends.setUsers(users);
    friends.setUsersIdUsers(1);

    Iterable<Friends> friendsList = new ArrayList<>();
    ((ArrayList<Friends>) friendsList).add(0, friends);

    when(friendsRepository.findAll()).thenReturn(friendsList);
    when(friends.getUsersIdUsers()).thenReturn(1);
    when(friends.getUsers()).thenReturn(users);
    when(friends.getUsers().getIdUsers()).thenReturn(1);

    Friends getFreind = friendsService.getFriend(1, 2);

    assertEquals(null, getFreind);
  }
}
