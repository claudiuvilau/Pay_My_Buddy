package com.openclassrooms.pay_my_buddy;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openclassrooms.pay_my_buddy.model.FriendsNetwork;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.FriendsNetworkService;
import com.openclassrooms.pay_my_buddy.service.UsersService;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private UsersService usersService; // instance of object

	@Autowired
	private FriendsNetworkService friendsNetworkService; // instance of object

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

		for (int i = 0; i < userId.getFriendsNetworks().size(); i++) {
			int userBuddy = userId.getFriendsNetworks().get(i).getBuddy();
			System.out.print(" Friend : " + userBuddy);
			Optional<Users> optUserBuddy = usersService.getUserById(userBuddy);
			Users userBuddyUser = optUserBuddy.get();
			System.out.println(" The name with id buddy " + userBuddy + " is " + userBuddyUser.getNameUser());

		}

		seeBuddyList(userId);

		// it will add the friends 1 for the users 2
		FriendsNetwork newFriendsNetwork = new FriendsNetwork();
		newFriendsNetwork.setUsersIdUsers(2);
		newFriendsNetwork.setBuddy(1);

		newFriendsNetwork = friendsNetworkService.addFriendsNetwork(newFriendsNetwork);

		seeBuddyList(userId);

	}

	private void seeBuddyList(Users userId) {
		userId.getFriendsNetworks()
				.forEach(friend -> System.out
						.println(friend.getBuddy() + " Name buddy : " + friend.getUsers().getNameUser()));
		userId.getFriendsBuddy()
				.forEach(buddy -> System.out.println(" List buddy : " + buddy.getUsers().getNameUser()));

	}

}
