package com.stortor.spring.web.auth;

import com.stortor.spring.web.auth.entity.User;
import com.stortor.spring.web.auth.repositories.UserRepository;
import com.stortor.spring.web.auth.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOneUserTest() {
        User user = new User();
        user.setUsername("Alice");
        user.setEmail("alice@gmail.com");

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findByUsername("Alice");
        User userAlice = userService.findByUsername("Alice").get();
        Assertions.assertNotNull(userAlice);
        Assertions.assertEquals("alice@gmail.com", userAlice.getEmail());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(ArgumentMatchers.eq("Alice"));
    }
}
