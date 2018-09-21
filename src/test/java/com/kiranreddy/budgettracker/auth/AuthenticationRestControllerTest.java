package com.kiranreddy.budgettracker.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiranreddy.budgettracker.WithMockCustomUser;
import com.kiranreddy.budgettracker.security.JwtAuthenticationRequest;
import com.kiranreddy.budgettracker.security.JwtTokenUtil;
import com.kiranreddy.budgettracker.security.JwtUser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthenticationRestController.class)
@WithMockCustomUser
public class AuthenticationRestControllerTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean(name = "jwtUserDetailsService")
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createAuthenticationToken() throws Exception {
        JwtUser principal = new JwtUser("id", "kiran",
                "kiran", "reddy", "kiran@email.com", "", null, true);
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJraXJhbiIsImlhdCI6MTUyNTM2NTAxMn0." +
                "A41zPbqVo7dCifB53nw3VTJjEAkTtaNByWJ6J-1cllRq08IHpUOHiqSr5WNm5dBHQT7y8tg9ipjuMUPFrw4JbQ";
        JwtAuthenticationRequest jwtAuthenticationRequest = new JwtAuthenticationRequest("kiran", "password");
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken("kiran", "password"));
        when(jwtUserDetailsService.loadUserByUsername("kiran")).thenReturn(principal);
        when(jwtTokenUtil.generateToken(any())).thenReturn(token);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth").content(objectMapper.writeValueAsString(jwtAuthenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(token));
    }

    @Test
    public void handleAuthenticationExceptionDisabledUser() throws Exception {
        JwtAuthenticationRequest jwtAuthenticationRequest
                = new JwtAuthenticationRequest("kiran", "password");
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid user"));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth").content(objectMapper.writeValueAsString(jwtAuthenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void handleAuthenticationExceptionBadCredentials() throws Exception {
        JwtAuthenticationRequest jwtAuthenticationRequest
                = new JwtAuthenticationRequest("kiran", "password");
        when(authenticationManager.authenticate(any()))
                .thenThrow(new DisabledException("Invalid user"));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/auth").content(objectMapper.writeValueAsString(jwtAuthenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void getAuthenticatedUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/auth/user"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}