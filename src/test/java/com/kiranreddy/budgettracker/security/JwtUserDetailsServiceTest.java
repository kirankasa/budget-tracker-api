package com.kiranreddy.budgettracker.security;

import com.kiranreddy.budgettracker.user.User;
import com.kiranreddy.budgettracker.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JwtUserDetailsService.class)
public class JwtUserDetailsServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void loadUserByUsername() {
        when(userRepository.findByUserName("kiran")).thenReturn(new User(1L, "kiran",
                "Kiran", "Reddy", "kiran@email.com", "123456"));
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("kiran");
        Assertions.assertThat(userDetails).isNotNull();
        JwtUser user = (JwtUser) userDetails;
        Assertions.assertThat(user.getId()).isEqualTo(1L);
        Assertions.assertThat(user.getUsername()).isEqualTo("kiran");
        Assertions.assertThat(user.getFirstName()).isEqualTo("Kiran");
        Assertions.assertThat(user.getLastName()).isEqualTo("Reddy");
        Assertions.assertThat(user.getEmail()).isEqualTo("kiran@email.com");
        Assertions.assertThat(user.getPassword()).isEqualTo("123456");
    }

    @Test
    public void loadUserByUsernameInvalidUser() {
        when(userRepository.findByUserName("kiran")).thenReturn(null);
        thrown.expect(UsernameNotFoundException.class);
        jwtUserDetailsService.loadUserByUsername("kiran");
    }
}