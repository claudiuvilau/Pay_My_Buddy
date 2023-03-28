package com.openclassrooms.pay_my_buddy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.pay_my_buddy.controller.LoginController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PayMyBuddyApplicationTests {

	@Autowired
	private LoginController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
