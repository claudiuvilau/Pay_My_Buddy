package com.openclassrooms.pay_my_buddy;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.openclassrooms.pay_my_buddy.controller.LoginController;

@SpringBootTest
@AutoConfigureMockMvc // sans cela => msg fail to load application context
class PayMyBuddyApplicationTests {

	@Autowired
	private LoginController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}