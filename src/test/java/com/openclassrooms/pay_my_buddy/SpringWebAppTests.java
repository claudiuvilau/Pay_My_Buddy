package com.openclassrooms.pay_my_buddy;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringWebAppTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  public void setup() {
    mockMvc =
      MockMvcBuilders
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
    mockMvc
      .perform(
        formLogin("/login").user("mireille.benoit@hotmail.com").password("2")
      )
      .andExpect(authenticated());
  }

  @Test
  public void userLoginFailed() throws Exception {
    mockMvc
      .perform(
        formLogin("/login")
          .user("mireille.benoit@hotmail.com")
          .password("wrongpassword")
      )
      .andExpect(unauthenticated());
  }

  @Test
  public void userLoginTestUserNameFieldEmpty() throws Exception {
    mockMvc
      .perform(formLogin("/login").user("").password("2"))
      .andExpect(unauthenticated());
  }

  @Test
  public void userLoginTestUserNameNull() throws Exception {
    mockMvc
      .perform(formLogin("/login").user("null.null@hotmail.com").password("2"))
      .andExpect(unauthenticated());
  }
}
