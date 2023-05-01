package com.openclassrooms.pay_my_buddy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.openclassrooms.pay_my_buddy.configuration.SpringSecurityConfig;
import com.openclassrooms.pay_my_buddy.controller.LoginController;
import com.openclassrooms.pay_my_buddy.model.CostsDetailsTransactions;
import com.openclassrooms.pay_my_buddy.model.Roles;
import com.openclassrooms.pay_my_buddy.model.Users;
import com.openclassrooms.pay_my_buddy.service.UsersService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;

//@AutoConfigureMockMvc
//@SpringBootTest
//@WebMvcTest(controllers = LoginController.class)//
//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = LoginController.class)
//@WithMockUser(username = "mireille.benoit@hotmail.com", roles = "ADMIN")
//@WebMvcTest(controllers = LoginController.class)
//@ContextConfiguration(classes = SpringSecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    UsersService usersService;

    @MockBean
    Users users;

    @BeforeEach
    public void setup() {
        // LoginController controller = new LoginController();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        // mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    // @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
    @Test
    @WithMockUser(username = "mireille.benoit@hotmail.com", password = "2")
    public void testAfterLogin() throws Exception {
        List<CostsDetailsTransactions> listCostsUserToBuddy = new ArrayList<>();
        CostsDetailsTransactions costsDetailsTransactions = new CostsDetailsTransactions();
        costsDetailsTransactions.setAmount(999);
        listCostsUserToBuddy.add(costsDetailsTransactions);

        String dateString = "2023-01-01";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = formatter.parse(dateString);

        Roles roles;
        Users usersName;
        int roleId = 1; // 1 = user
        roles = new Roles();
        roles.setIdRoles(roleId);
        usersName = new Users();
        usersName.setIdEmail("newuser@hotmail.com");
        usersName.setPassword("newUserPassword");
        usersName.setFirstName("newUserFirstName");
        usersName.setNameUser("newUserLastName");
        usersName.setBirthDate(date);
        usersName.setRole(roles);

        // when(transactionsService.detailTransForUser(usersName,
        // false)).thenReturn(listCostsUserToBuddy);

        List<Users> list = new ArrayList<>();
        list.add(usersName);

        when(usersService.getUsers()).thenReturn(list);
        when(usersService.getUser("mireille.benoit@hotmail.com")).thenReturn(usersName);
        when(users.getFirstName()).thenReturn(usersName.getFirstName());
        when(users.getNameUser()).thenReturn(usersName.getNameUser());

        // UsersService userRepoFromContext = context.getBean(UsersService.class);
        // long userCount = userRepoFromContext.count();

        // when(userRepoFromContext.getUser("mireille.benoit@hotmail.com")).thenReturn(usersName);
        // mockMvc.perform(formLogin("/login").user("mireille.benoit@hotmail.com").password("2")).andExpect(authenticated());

        mockMvc.perform(get("/*")).andExpect(status().isOk());
        // mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk());
    }

    // @Test
    public void testRecupererNameUser() throws ParseException {

        String dateString = "2023-01-01";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = formatter.parse(dateString);

        Roles roles;
        Users usersName;
        int roleId = 1; // 1 = user
        roles = new Roles();
        roles.setIdRoles(roleId);
        usersName = new Users();
        usersName.setIdEmail("mireille.benoit@hotmail.com");
        usersName.setPassword("newUserPassword");
        usersName.setFirstName("newUserFirstName");
        usersName.setNameUser("newUserLastName");
        usersName.setBirthDate(date);
        usersName.setRole(roles);

        // when(usersService.getUser("mireille.benoit@hotmail.com")).thenReturn(usersName);
        // LoginController controller = new LoginController();
        // Principal principalUser = mock(Principal.class);

        // controller.recupererNameUser(principalUser);

        // Mockito.verify(principalUser).getName();

    }

}
