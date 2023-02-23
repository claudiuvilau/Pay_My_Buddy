package com.openclassrooms.pay_my_buddy;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openclassrooms.pay_my_buddy.model.Friends;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.FriendsService;
import com.openclassrooms.pay_my_buddy.service.UsersService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private UsersService usersService; // instance of object

	@Autowired
	private FriendsService friendsService; // instance of object

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	// the result of implemantation of interface CommandLineRunner
	// it run everytime when the application start
	@Override
	@Transactional
	public void run(String... args) throws Exception {

		Iterable<Users> users = usersService.getUsers();
		users.forEach(user -> System.out.println(user.getNameUser()));

		int intID = 2;
		Optional<Users> optProduct = usersService.getUserById(intID);
		Users userId = optProduct.get();
		System.out.println("The first name with id  " + intID + " is " + userId.getFirstName() + " and he has : ");

		for (int i = 0; i < userId.getFriends().size(); i++) {
			int userBuddy = userId.getFriends().get(i).getBuddy();
			System.out.print(" Friend : " + userBuddy);
			Optional<Users> optUserBuddy = usersService.getUserById(userBuddy);
			Users userBuddyUser = optUserBuddy.get();
			System.out.println(" The name with id buddy " + userBuddy + " is " + userBuddyUser.getNameUser());

		}

		seeBuddyList(userId);

		// it will add the friends 1 for the users 2
		Friends newFriends = new Friends();
		newFriends.setUsersIdUsers(2);
		newFriends.setBuddy(1);

		newFriends = friendsService.addFriends(newFriends);

		seeBuddyList(userId);

	}

	private void seeBuddyList(Users userId) {
		userId.getFriends()
				.forEach(friend -> System.out
						.println(friend.getBuddy() + " Name buddy : " + friend.getUsers().getNameUser()));
		userId.getFriendsBuddy()
				.forEach(buddy -> System.out.println(" List buddy : " + buddy.getUsers().getNameUser()));

	}

}
