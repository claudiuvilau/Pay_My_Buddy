package com.openclassrooms.pay_my_buddy;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.UsersService;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private UsersService usersService; // instance of object

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	// the result of implemantation of interface CommandLineRunner
	// it run everytime when the application start
	@Override
	public void run(String... args) throws Exception {

		Iterable<Users> users = usersService.getUsers();
		users.forEach(user -> System.out.println(user.getNameUser()));

		Optional<Users> optProduct = usersService.getUserById(2);
		Users userId = optProduct.get();
		System.out.print(userId.getFirstName());

		userId.getFriendsNetworks().forEach(
				friend -> System.out.print(" Friend : " + friend.getBuddy()));
	}

}
