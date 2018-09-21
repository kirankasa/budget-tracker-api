package com.kiranreddy.budgettracker.user;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

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
				.thenReturn(Arrays.asList(new User("id", "userName", "first", "last", "email@email.com", "password")));
		List<User> users = userService.retrieveUsers();
		Assertions.assertThat(users).hasSize(1);

		Assertions.assertThat(users.iterator().next().getId()).isEqualTo("id");
		Assertions.assertThat(users.iterator().next().getFirstName()).isEqualTo("first");
		Assertions.assertThat(users.iterator().next().getLastName()).isEqualTo("last");
		Assertions.assertThat(users.iterator().next().getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(users.iterator().next().getPassword()).isEqualTo("password");
	}

	@Test
	public void findValidUserTest() {
		when(userRepository.findById("id"))
				.thenReturn(Optional.of(new User("id", "userName", "first", "last", "email@email.com", "password")));

		User user = userService.findUser("id");
		Assertions.assertThat(user.getId()).isEqualTo("id");
		Assertions.assertThat(user.getFirstName()).isEqualTo("first");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	public void saveUserTest() {
		User userInput = new User(null, "userName", "first", "last", "email@email.com", "password");
		when(userRepository.save(userInput))
				.thenReturn(new User("id", "userName", "first", "last", "email@email.com", "password"));

		User user = userService.saveUser(userInput);
		Assertions.assertThat(user.getId()).isEqualTo("id");
		Assertions.assertThat(user.getFirstName()).isEqualTo("first");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	public void updateUserTest() {
		User userInput = new User("id", "userName", "first", "last", "email@email.com", "password");
		when(userRepository.findById("id")).thenReturn(Optional.of(userInput));

		when(userRepository.save(userInput))
				.thenReturn(new User("id", "userName", "firstUpdated", "last", "email@email.com", "password"));

		User user = userService.updateUser(userInput.getId(), userInput);
		Assertions.assertThat(user.getId()).isEqualTo("id");
		Assertions.assertThat(user.getFirstName()).isEqualTo("firstUpdated");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	public void updateInvalidUserTest() {
		when(userRepository.findById("id")).thenReturn(Optional.empty());
		thrown.expect(UserNotFoundException.class);
		userService.updateUser("id", null);
	}

	@Test
	public void findInValidUserTest() {
		when(userRepository.findById("id")).thenReturn(Optional.empty());
		thrown.expect(UserNotFoundException.class);
		userService.findUser("id");
	}

	@Test
	public void deleteUserTest() {
		User userInput = new User("id", "userName", "firstUpdated", "last", "email@email.com", "password");
		userService.deleteUser(userInput);
		verify(userRepository, times(1)).delete(userInput);
	}

	@Test
	public void deleteUserByIdTest() {
		User userInput = new User("id", "userName", "firstUpdated", "last", "email@email.com", "password");
		when(userRepository.findById("id")).thenReturn(Optional.of(userInput));
		userService.deleteUser("id");
		verify(userRepository, times(1)).findById("id");
		verify(userRepository, times(1)).delete(userInput);
	}
}
