package com.openclassrooms.pay_my_buddy;

import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.pay_my_buddy.controller.LoginController;
import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Descriptions;
import com.openclassrooms.pay_my_buddy.model.Transactions;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.TransactionsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SpringWebAppTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TransactionsService transactionsService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void userLoginTest() throws Exception {
        mockMvc.perform(formLogin("/login").user("mireille.benoit@hotmail.com")
                .password("2")).andExpect(authenticated());
    }

    @Test
    public void userLoginFailed() throws Exception {
        mockMvc.perform(formLogin("/login").user("mireille.benoit@hotmail.com").password("wrongpassword"))
                .andExpect(unauthenticated());
    }

}
