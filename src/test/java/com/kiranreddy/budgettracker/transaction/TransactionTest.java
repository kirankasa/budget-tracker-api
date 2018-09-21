package com.kiranreddy.budgettracker.transaction;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TransactionTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void transactionCreationTest() {
		Date date = new Date();
		Transaction transaction = new Transaction("id", "type", 100.00, date, "note","category");

		Assertions.assertThat(transaction.getId()).isEqualTo("id");
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
		Assertions.assertThat(transaction.getCategory()).isEqualTo("category");
	}

	@Test
	public void transactionMappingTest() {
		Date date = new Date();
		Transaction transaction = new Transaction(null, "type", 100.00, date, "note","category");
		mongoTemplate.save(transaction);

		Assertions.assertThat(transaction.getId()).isNotNull();
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
		Assertions.assertThat(transaction.getCategory()).isEqualTo("category");
	}
}
