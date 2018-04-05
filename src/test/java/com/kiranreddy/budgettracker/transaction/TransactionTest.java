package com.kiranreddy.budgettracker.transaction;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.kiranreddy.budgettracker.category.TransactionCategory;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void transactionCreationTest() {
		Date date = new Date();
		Transaction transaction = new Transaction(1L, "type", 100.00, date, "note",
				new TransactionCategory(null, "category", "type"));

		Assertions.assertThat(transaction.getId()).isEqualTo(1L);
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
		Assertions.assertThat(transaction.getCategory().getCategory()).isEqualTo("category");
	}

	@Test
	@Sql(statements = { "insert into transaction_category(id,category) values('1','category')" })
	public void transactionMappingTest() {
		Date date = new Date();
		Transaction transaction = new Transaction(null, "type", 100.00, date, "note",
				new TransactionCategory(1L, "category", "type"));
		transaction = testEntityManager.persistAndFlush(transaction);

		Assertions.assertThat(transaction.getId()).isNotNull().isPositive();
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
		Assertions.assertThat(transaction.getCategory().getCategory()).isEqualTo("category");
	}
}
