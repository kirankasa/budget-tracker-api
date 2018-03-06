package com.kiranreddy.budgettracker.transaction;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transationRepository;
	
	@Test
	public void saveUserTest() {
		Date date =new Date();
		Transaction transaction = new Transaction(1L,"type",100.00,date, "note");
		transationRepository.save(transaction);
		
		Assertions.assertThat(transaction.getId()).isNotNull().isPositive();
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
	}
	
	@Test
	public void fetchUsersTest() {
		Date date =new Date();
		Transaction transaction = new Transaction(1L,"type",100.00,date, "note");
		transationRepository.save(transaction);
		List<Transaction> transactions = transationRepository.findAll();
		Assertions.assertThat(transactions).hasSize(1);
		
		Assertions.assertThat(transactions.iterator().next().getId()).isNotNull().isPositive();
		Assertions.assertThat(transactions.iterator().next().getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transactions.iterator().next().getType()).isEqualTo("type");
		Assertions.assertThat(transactions.iterator().next().getDate()).isEqualTo(date);
		Assertions.assertThat(transactions.iterator().next().getNote()).isEqualTo("note");
	}
}
