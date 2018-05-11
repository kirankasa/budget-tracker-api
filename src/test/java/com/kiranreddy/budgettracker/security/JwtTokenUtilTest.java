package com.kiranreddy.budgettracker.security;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @Before
    public void setUp() throws Exception {
        jwtTokenUtil = new JwtTokenUtil("1234567890");
    }

    @Test
    public void getUsernameFromToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJraXJhbiIsImlhdCI6MTUyNTM2NTAxMn0." +
                "A41zPbqVo7dCifB53nw3VTJjEAkTtaNByWJ6J-1cllRq08IHpUOHiqSr5WNm5dBHQT7y8tg9ipjuMUPFrw4JbQ";
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
        Assertions.assertThat(usernameFromToken).isEqualTo("kiran");
    }

    @Test
    public void getIssuedAtDateFromToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJraXJhbiIsImlhdCI6MTUyNTM2NTAxMn0." +
                "A41zPbqVo7dCifB53nw3VTJjEAkTtaNByWJ6J-1cllRq08IHpUOHiqSr5WNm5dBHQT7y8tg9ipjuMUPFrw4JbQ";
        Date issuedAtDateFromToken = jwtTokenUtil.getIssuedAtDateFromToken(token);
        Assertions.assertThat(issuedAtDateFromToken).isNotNull();
    }


    @Test
    public void generateToken() {
        JwtUser principal = new JwtUser(1L, "kiran",
                "kiran", "reddy", "kiran@email.com", "", null, true);
        String token = jwtTokenUtil.generateToken(principal);
        Assertions.assertThat(token).isNotBlank();
    }

    @Test
    public void validateToken() {
        JwtUser principal = new JwtUser(1L, "kiran",
                "kiran", "reddy", "kiran@email.com", "", null, true);
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJraXJhbiIsImlhdCI6MTUyNTM2NTAxMn0." +
                "A41zPbqVo7dCifB53nw3VTJjEAkTtaNByWJ6J-1cllRq08IHpUOHiqSr5WNm5dBHQT7y8tg9ipjuMUPFrw4JbQ";
        Boolean isValid = jwtTokenUtil.validateToken(token, principal);
        Assertions.assertThat(isValid).isTrue();
    }
}