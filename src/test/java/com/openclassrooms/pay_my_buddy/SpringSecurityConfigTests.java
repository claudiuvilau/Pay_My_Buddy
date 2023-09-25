package com.openclassrooms.pay_my_buddy;

import static org.junit.Assert.assertEquals;

import com.openclassrooms.pay_my_buddy.configuration.SpringSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SpringSecurityConfigTests {

  @Autowired
  SpringSecurityConfig securityConfig;

  @Test
  public void testDataSourceProfileTest() {
    DriverManagerDataSource ds = (DriverManagerDataSource) securityConfig.dataSource();

    assertEquals(
      "jdbc:mysql://localhost:3306/paymybuddytest?serverTimezone=UTC",
      ds.getUrl()
    );
  }
}
