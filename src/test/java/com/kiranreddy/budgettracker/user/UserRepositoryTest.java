package com.kiranreddy.budgettracker.user;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void fetchUsersTest() {
		User user = new User(null, "userName", "first", "last", "email@email.com", "userId");
		userRepository.save(user);
		List<User> users = userRepository.findAll();

		Assertions.assertThat(users.iterator().next().getId()).isNotNull();
		Assertions.assertThat(users.iterator().next().getFirstName()).isEqualTo("first");
		Assertions.assertThat(users.iterator().next().getLastName()).isEqualTo("last");
		Assertions.assertThat(users.iterator().next().getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(users.iterator().next().getUserId()).isEqualTo("userId");
	}
}
