package com.kiranreddy.budgettracker.user;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserService.class })
public class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private UserService userService;

	@Test
	public void retrieveUsersTest() {
		when(userRepository.findAll())
				.thenReturn(Arrays.asList(new User(1L, "userName", "first", "last", "email@email.com", "password")));
		List<User> users = userService.retrieveUsers();
		Assertions.assertThat(users).hasSize(1);

		Assertions.assertThat(users.iterator().next().getId()).isEqualTo(1L);
		Assertions.assertThat(users.iterator().next().getFirstName()).isEqualTo("first");
		Assertions.assertThat(users.iterator().next().getLastName()).isEqualTo("last");
		Assertions.assertThat(users.iterator().next().getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(users.iterator().next().getPassword()).isEqualTo("password");
	}

	@Test
	public void findValidUserTest() {
		when(userRepository.findById(1L))
				.thenReturn(Optional.of(new User(1L, "userName", "first", "last", "email@email.com", "password")));

		User user = userService.findUser(1L);
		Assertions.assertThat(user.getId()).isEqualTo(1L);
		Assertions.assertThat(user.getFirstName()).isEqualTo("first");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	public void saveUserTest() {
		User userInput = new User(null, "userName", "first", "last", "email@email.com", "password");
		when(userRepository.save(userInput))
				.thenReturn(new User(1L, "userName", "first", "last", "email@email.com", "password"));

		User user = userService.saveUser(userInput);
		Assertions.assertThat(user.getId()).isEqualTo(1L);
		Assertions.assertThat(user.getFirstName()).isEqualTo("first");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	public void updateUserTest() {
		User userInput = new User(1L, "userName", "first", "last", "email@email.com", "password");
		when(userRepository.findById(1L)).thenReturn(Optional.of(userInput));

		when(userRepository.save(userInput))
				.thenReturn(new User(1L, "userName", "firstUpdated", "last", "email@email.com", "password"));

		User user = userService.updateUser(userInput.getId(), userInput);
		Assertions.assertThat(user.getId()).isEqualTo(1L);
		Assertions.assertThat(user.getFirstName()).isEqualTo("firstUpdated");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	public void findInValidUserTest() {
		when(userRepository.findById(2L)).thenReturn(Optional.empty());
		thrown.expect(UserNotFoundException.class);
		userService.findUser(2L);
	}

	@Test
	public void deleteUserTest() {
		User userInput = new User(1L, "userName", "firstUpdated", "last", "email@email.com", "password");
		userService.deleteUser(userInput);
		verify(userRepository, times(1)).delete(userInput);
	}

	@Test
	public void deleteUserByIdTest() {
		User userInput = new User(1L, "userName", "firstUpdated", "last", "email@email.com", "password");
		when(userRepository.findById(1L)).thenReturn(Optional.of(userInput));
		userService.deleteUser(1L);
		verify(userRepository, times(1)).findById(1L);
		verify(userRepository, times(1)).delete(userInput);
	}
}
