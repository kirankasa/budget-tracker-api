package com.kiranreddy.budgettracker.transaction;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kiranreddy.budgettracker.user.UserNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TransactionService.class })
public class TransactionServiceTest {

	@MockBean
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionService transactionService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void retrieveTransactionsTest() {
		Date date = new Date();
		when(transactionRepository.findAll())
				.thenReturn(Arrays.asList(new Transaction(1L, "type", 100.00, date, "note")));
		List<Transaction> transactions = transactionService.retrieveTransactions();
		Assertions.assertThat(transactions).hasSize(1);

		Assertions.assertThat(transactions.iterator().next().getId()).isEqualTo(1L);
		Assertions.assertThat(transactions.iterator().next().getType()).isEqualTo("type");
		Assertions.assertThat(transactions.iterator().next().getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transactions.iterator().next().getDate()).isEqualTo(date);
		Assertions.assertThat(transactions.iterator().next().getNote()).isEqualTo("note");
	}

	@Test
	public void findValidTransactionTest() {
		Date date = new Date();
		when(transactionRepository.findById(1L))
				.thenReturn(Optional.of(new Transaction(1L, "type", 100.00, date, "note")));
		Transaction transaction = transactionService.findTransaction(1L);

		Assertions.assertThat(transaction.getId()).isEqualTo(1L);
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
	}

	@Test
	public void findInValidTransactionTest() {
		when(transactionRepository.findById(1L)).thenReturn(Optional.empty());
		thrown.expect(UserNotFoundException.class);
		transactionService.findTransaction(1L);
	}

	@Test
	public void deleteTransactionTest() {
		Date date = new Date();
		Transaction transaction = new Transaction(1L, "type", 100.00, date, "note");
		transactionService.deleteTransaction(transaction);
		verify(transactionRepository, times(1)).delete(transaction);
	}

	@Test
	public void saveTransactionTest() {
		Date date = new Date();
		Transaction transactionInput = new Transaction(null, "type", 100.00, date, "note");
		when(transactionRepository.save(transactionInput))
				.thenReturn(new Transaction(1L, "type", 100.00, date, "note"));

		Transaction transaction = transactionService.saveTransaction(transactionInput);
		Assertions.assertThat(transaction.getId()).isNotNull().isPositive();
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
	}

	@Test
	public void updateTransactionTest() {
		Date date = new Date();
		Transaction transactionInput = new Transaction(1L, "type", 100.00, date, "note");
		when(transactionRepository.findById(1L))
		.thenReturn(Optional.of(transactionInput));
		when(transactionRepository.save(transactionInput))
				.thenReturn(new Transaction(1L, "type", 100.00, date, "note"));

		Transaction transaction = transactionService.updateTransaction(1L, transactionInput);
		Assertions.assertThat(transaction.getId()).isEqualTo(1L);
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
	}

}