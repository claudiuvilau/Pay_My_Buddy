package com.openclassrooms.pay_my_buddy.configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(authorize ->
        authorize
          .requestMatchers("/user")
          .hasAuthority("USER")
          .requestMatchers("/admin")
          .hasAuthority("ADMIN")
          .requestMatchers("/register")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .formLogin()
      .loginPage("/login")
      .permitAll()
      .and()
      .logout()
      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
      .logoutSuccessUrl("/login")
      .deleteCookies("JSESSIONID")
      .invalidateHttpSession(true);

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Profile("test")
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();

    ds.setDriverClassName("org.hibernate.dialect.MySQLDialect");
    ds.setUrl("jdbc:mysql://localhost:3306/paymybuddytest?serverTimezone=UTC");
    ds.setUsername("root");
    ds.setPassword("Parolamea1234*");

    return ds;
  }
}
