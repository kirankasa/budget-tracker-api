package com.kiranreddy.budgettracker.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiranreddy.budgettracker.WithMockCustomUser;
import com.kiranreddy.budgettracker.category.TransactionCategoryService;
import com.kiranreddy.budgettracker.security.JwtTokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@WebMvcTest({ UserController.class })
@WithMockCustomUser
public class UserControllerTest {

	@MockBean
	private UserService userService;

	@MockBean
	private TransactionCategoryService transactionCategoryService;

	@MockBean
	private UserDetailsService jwtUserDetailsService;

	@MockBean
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void retrieveUsersTest() throws Exception {
		when(userService.retrieveUsers())
				.thenReturn(Arrays.asList(new User("id", "userName", "first", "last", "email@email.com", "password")));
		mockMvc.perform(MockMvcRequestBuilders.get("/users")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].firstName").value("first"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].lastName").value("last"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].email").value("email@email.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].password").doesNotExist());
	}

	@Test
	public void findUserTest() throws Exception {
		when(userService.findUser("id"))
				.thenReturn(new User("id", "userName", "first", "last", "email@email.com", "password"));
		mockMvc.perform(MockMvcRequestBuilders.get("/users/id")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("first"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("last"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@email.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").doesNotExist());
	}

	@Test
	public void findInvalidUserTest() throws Exception {
		when(userService.findUser("id")).thenThrow(new UserNotFoundException("User not found"));
		mockMvc.perform(MockMvcRequestBuilders.get("/users/id")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void deleteUserTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/id").with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		verify(userService, times(1)).deleteUser("id");
	}

	@Test
	public void saveUserTest() throws Exception {
		User user = new User(null, "userName", "first", "last", "email@email.com", null);
		when(userService.saveUser(any()))
				.thenReturn(new User("id", "userName", "first", "last", "email@email.com", "password"));
		mockMvc.perform(MockMvcRequestBuilders.post("/users/register").content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("first"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("last"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@email.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").doesNotExist());

		verify(transactionCategoryService, times(1)).saveTransactionCategory(any());
	}

	@Test
	public void updateUserTest() throws Exception {
		User user = new User("id", "userName", "first", "last", "email@email.com", null);
		when(userService.updateUser(any(), any()))
				.thenReturn(new User("id", "userName", "first", "last", "email@email.com", "password"));
		mockMvc.perform(MockMvcRequestBuilders.put("/users/id").content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("first"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("last"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@email.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").doesNotExist());
	}
}
