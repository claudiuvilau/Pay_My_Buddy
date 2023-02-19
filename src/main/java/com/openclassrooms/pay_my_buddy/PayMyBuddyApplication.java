package com.openclassrooms.pay_my_buddy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
		users.forEach(user -> System.out.println("Name user : " + user.getNameUser() + " and your buddy ...???"));

		int intID = 1;
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
		int usersIdUsers = 2;
		int buddy = 1;

		// delete Friend :
		newFriends = friendsService.getFriend(usersIdUsers, buddy);

		// friendsService.deleteFriendById(newFriends.getId());
		friendsService.deleteFriend(newFriends);
		newFriends = new Friends();
		newFriends.setUsersIdUsers(usersIdUsers);
		newFriends.setBuddy(buddy);

		// it will add a new user
		Users newUser = new Users();
		String idMail = "popa.pop@yahoo.com";

		newUser = usersService.getUser(idMail);

		// delete User :
		// usersService.deleteUser(newUser);
		// usersService.deleteUserById(newUser.getIdUsers());

		newUser = new Users();
		Date dateB = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = "01/09/1977";

		dateB = simpleDateFormat.parse(dateString);

		newUser.setBirthDate(dateB);
		newUser.setFirstName("POP");
		newUser.setIdEmail(idMail);
		newUser.setNameUser("POPA");

		// newUser = usersService.addUser(newUser);

		List<Friends> fList = (List<Friends>) friendsService.getFriends();
		if (fList != null) {
			boolean exist = false;
			for (int i = 0; i < fList.size(); i++) {
				if (fList.get(i).getUsersIdUsers() == newFriends.getUsersIdUsers()
						&& fList.get(i).getBuddy() == newFriends.getBuddy()) {
					exist = true;
					i = fList.size();
				}
			}
			if (!exist) {
				newFriends = friendsService.addFriends(newFriends);
			}
		}

		seeBuddyList(userId);

	}

	private void seeBuddyList(Users userId) {
		userId.getFriends()
				.forEach(friend -> System.out
						.println("ID buddy is : " + friend.getBuddy() + " Name buddy : "
								+ usersService.getUserById(friend.getBuddy()).get().getNameUser()));

	}

}
