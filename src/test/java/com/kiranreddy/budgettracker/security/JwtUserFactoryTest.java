package com.kiranreddy.budgettracker.security;

import com.kiranreddy.budgettracker.user.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class JwtUserFactoryTest {

    @Test
    public void create() {
        JwtUser user = JwtUserFactory.create(new User("id", "kiran", "Kiran",
                "Reddy", "kiran@email.com", "123456"));
        Assertions.assertThat(user.getId()).isEqualTo("id");
        Assertions.assertThat(user.getUsername()).isEqualTo("kiran");
        Assertions.assertThat(user.getFirstName()).isEqualTo("Kiran");
        Assertions.assertThat(user.getLastName()).isEqualTo("Reddy");
        Assertions.assertThat(user.getEmail()).isEqualTo("kiran@email.com");
        Assertions.assertThat(user.getPassword()).isEqualTo("123456");
    }
}