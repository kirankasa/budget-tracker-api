package com.kiranreddy.budgettracker.user;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void saveUserTest() {
		User user = new User(null, "userName", "first", "last", "email@email.com", "password");
		userRepository.save(user);

		Assertions.assertThat(user.getId()).isNotNull();
		Assertions.assertThat(user.getFirstName()).isEqualTo("first");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	public void fetchUsersTest() {
		User user = new User(null, "userName", "first", "last", "email@email.com", "password");
		userRepository.save(user);
		List<User> users = userRepository.findAll();
		Assertions.assertThat(users).hasSize(1);

		Assertions.assertThat(users.iterator().next().getId()).isNotNull();
		Assertions.assertThat(users.iterator().next().getFirstName()).isEqualTo("first");
		Assertions.assertThat(users.iterator().next().getLastName()).isEqualTo("last");
		Assertions.assertThat(users.iterator().next().getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(users.iterator().next().getPassword()).isEqualTo("password");
	}
}
