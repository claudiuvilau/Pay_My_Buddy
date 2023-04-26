package com.openclassrooms.pay_my_buddy;

import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.when;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.openclassrooms.pay_my_buddy.repository.UsersRepository;
import com.openclassrooms.pay_my_buddy.service.UsersService;

import jakarta.inject.Inject;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UsersServiceTests {

    @Autowired
    private UsersService usersService = new UsersService();

    @MockBean
    private UsersRepository usersRepository;

    @Autowired
    ApplicationContext context;

    @Test
    public void testGetUsers() {

        when(this.usersRepository.findAll()).thenReturn(anyIterable());

        // UsersRepository userRepoFromContext = context.getBean(UsersRepository.class);

        UsersRepository localMockRepository = Mockito.mock(UsersRepository.class);
        Mockito.when(localMockRepository.count()).thenReturn(111L);

        long userCount = localMockRepository.count();

        Mockito.verify(localMockRepository).count();

        usersService.getUsers();

    }

}
