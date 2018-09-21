package com.kiranreddy.budgettracker.user;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UserTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void userCreationTest() {
		User user = new User("id", "userName", "first", "last", "email@email.com", "password");
		Assertions.assertThat(user.getId()).isEqualTo("id");
		Assertions.assertThat(user.getFirstName()).isEqualTo("first");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}

	@Test
	public void userMappingTest() {
		User user = new User(null, "userName", "first", "last", "email@email.com", "password");
		user.setPassword("password");
		mongoTemplate.save(user);

		Assertions.assertThat(user.getId()).isNotNull();
		Assertions.assertThat(user.getFirstName()).isEqualTo("first");
		Assertions.assertThat(user.getLastName()).isEqualTo("last");
		Assertions.assertThat(user.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(user.getPassword()).isEqualTo("password");
	}
}
