package com.openclassrooms.pay_my_buddy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.openclassrooms.pay_my_buddy.configuration.SpringSecurityConfig;
import com.openclassrooms.pay_my_buddy.controller.LoginController;
import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.repository.UsersRepository;
import com.openclassrooms.pay_my_buddy.service.CostsDetailsTransactionsService;
import com.openclassrooms.pay_my_buddy.service.TransactionsService;
import com.openclassrooms.pay_my_buddy.service.UsersService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@WebMvcTest(controllers = LoginController.class)
//@ContextConfiguration(classes = { SpringSecurityConfig.class })
//@AutoConfigureMockMvc
@SpringBootTest(classes = LoginControllerTests.class)
@AutoConfigureMockMvc
public class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @MockBean
    private UsersService usersService;

    @BeforeEach
    public void setUp() {
        LoginController controller = new LoginController();
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testAddConnection() throws Exception {

        List<CostsDetailsTransactions> listCostsUserToBuddy = new ArrayList<>();
        CostsDetailsTransactions costsDetailsTransactions = new CostsDetailsTransactions();
        costsDetailsTransactions.setAmount(999);
        listCostsUserToBuddy.add(costsDetailsTransactions);
        Users usersName = new Users();
        usersName.setIdEmail("test@test.com");
        when(transactionsService.detailTransForUser(usersName, false)).thenReturn(listCostsUserToBuddy);
        when(usersService.getUser("test@test.com")).thenReturn(usersName);

        mockMvc.perform(get("/*")).andExpect(status().isOk());
    }

}
