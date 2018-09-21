package com.kiranreddy.budgettracker.transaction;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transationRepository;

	@Test
	public void saveTransactionTest() {
		Date date = new Date();
		Transaction transaction = new Transaction(null, "type", 100.00, date, "note","category");
		transationRepository.save(transaction);

		Assertions.assertThat(transaction.getId()).isNotNull();
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
		Assertions.assertThat(transaction.getCategory()).isEqualTo("category");
	}

	@Test
	public void fetchTransactionsTest() {
		Date date = new Date();
		Transaction transaction = new Transaction("id", "type", 100.00, date, "note","category");
		transationRepository.save(transaction);
		List<Transaction> transactions = transationRepository.findAll();

		Assertions.assertThat(transactions.iterator().next().getId()).isNotNull();
		Assertions.assertThat(transactions.iterator().next().getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transactions.iterator().next().getType()).isEqualTo("type");
		Assertions.assertThat(transactions.iterator().next().getDate()).isEqualToIgnoringSeconds(date);
		Assertions.assertThat(transactions.iterator().next().getNote()).isEqualTo("note");
		Assertions.assertThat(transactions.iterator().next().getCategory()).isEqualTo("category");
	}
}
