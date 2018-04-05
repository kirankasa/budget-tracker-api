package com.kiranreddy.budgettracker.user;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.kiranreddy.budgettracker.user.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void userCreationTest() {
		User user = new User(1L, "userName", "first", "last", "email@email.com", "password");
		Assertions.assertThat(user.getId()).isEqualTo(1L);
		Assertions.assertThat(user.getFirstName()).isEqualTo("first");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	public void userMappingTest() {
		User user = new User(null, "userName", "first", "last", "email@email.com", "password");
		user.setPassword("password");
		testEntityManager.persistAndFlush(user);

		Assertions.assertThat(user.getId()).isNotNull();
		Assertions.assertThat(user.getFirstName()).isEqualTo("first");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}
}
