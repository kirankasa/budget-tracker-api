package com.kiranreddy.budgettracker.transaction;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.kiranreddy.budgettracker.category.TransactionCategory;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transationRepository;

	@Test
	@Sql(statements = { "insert into transaction_category(id, category) values('1','category')" })
	public void saveTransactionTest() {
		Date date = new Date();
		Transaction transaction = new Transaction(null, "type", 100.00, date, "note",
				new TransactionCategory(1L, "category", "type"));
		transationRepository.save(transaction);

		Assertions.assertThat(transaction.getId()).isNotNull().isPositive();
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
		Assertions.assertThat(transaction.getCategory().getCategory()).isEqualTo("category");
	}

	@Test
	@Sql(statements = { "insert into transaction_category(id,category) values('1','category')" })
	public void fetchTransactionsTest() {
		Date date = new Date();
		Transaction transaction = new Transaction(1L, "type", 100.00, date, "note",
				new TransactionCategory(1L, "category", "type"));
		transationRepository.save(transaction);
		List<Transaction> transactions = transationRepository.findAll();
		Assertions.assertThat(transactions).hasSize(1);

		Assertions.assertThat(transactions.iterator().next().getId()).isNotNull().isPositive();
		Assertions.assertThat(transactions.iterator().next().getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transactions.iterator().next().getType()).isEqualTo("type");
		Assertions.assertThat(transactions.iterator().next().getDate()).isEqualTo(date);
		Assertions.assertThat(transactions.iterator().next().getNote()).isEqualTo("note");
		Assertions.assertThat(transactions.iterator().next().getCategory().getCategory()).isEqualTo("category");
	}
}
