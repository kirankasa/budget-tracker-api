package com.kiranreddy.budgettracker.transaction;

import com.kiranreddy.budgettracker.user.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TransactionService.class })
public class TransactionServiceTest {

	@MockBean
	private TransactionRepository transactionRepository;

	@MockBean
	private TransactionReportRepository transactionReportRepository;

	@Autowired
	private TransactionService transactionService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void retrieveTransactionsTest() {
		Date date = new Date();
		when(transactionRepository.findByUserId("id")).thenReturn(Arrays.asList(
				new Transaction("id", "type", 100.00, date, "note","category")));
		List<Transaction> transactions = transactionService.retrieveTransactions("id");
		Assertions.assertThat(transactions).hasSize(1);

		Assertions.assertThat(transactions.iterator().next().getId()).isEqualTo("id");
		Assertions.assertThat(transactions.iterator().next().getType()).isEqualTo("type");
		Assertions.assertThat(transactions.iterator().next().getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transactions.iterator().next().getDate()).isEqualTo(date);
		Assertions.assertThat(transactions.iterator().next().getNote()).isEqualTo("note");
		Assertions.assertThat(transactions.iterator().next().getCategory()).isEqualTo("category");
	}

	@Test
	public void findValidTransactionTest() {
		Date date = new Date();
		when(transactionRepository.findById("id")).thenReturn(Optional.of(
				new Transaction("id", "type", 100.00, date, "note","category")));
		Transaction transaction = transactionService.findTransaction("id");

		Assertions.assertThat(transaction.getId()).isEqualTo("id");
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
	}

	@Test
	public void findInValidTransactionTest() {
		when(transactionRepository.findById("id")).thenReturn(Optional.empty());
		thrown.expect(UserNotFoundException.class);
		transactionService.findTransaction("id");
	}

	@Test
	public void deleteTransactionTest() {
		Date date = new Date();
		Transaction transaction = new Transaction("id", "type", 100.00, date, "note","category");
		transactionService.deleteTransaction(transaction);
		verify(transactionRepository, times(1)).delete(transaction);
	}

	@Test
	public void deleteTransactionByIdTest() {
		Date date = new Date();
		Transaction transaction = new Transaction("id", "type", 100.00, date, "note","category");
		when(transactionRepository.findById("id")).thenReturn(Optional.of(transaction));
		transactionService.deleteTransaction("id");
		verify(transactionRepository, times(1)).findById("id");
		verify(transactionRepository, times(1)).delete(transaction);
	}

	@Test
	public void saveTransactionTest() {
		Date date = new Date();
		Transaction transactionInput = new Transaction(null, "type", 100.00, date, "note","category");
		when(transactionRepository.save(transactionInput)).thenReturn(
				new Transaction("id", "type", 100.00, date, "note", "category"));

		Transaction transaction = transactionService.saveTransaction(transactionInput);
		Assertions.assertThat(transaction.getId()).isNotNull();
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
	}

	@Test
	public void updateTransactionTest() {
		Date date = new Date();
		Transaction transactionInput = new Transaction("id", "type", 100.00, date, "note","category");
		when(transactionRepository.findById("id")).thenReturn(Optional.of(transactionInput));
		when(transactionRepository.save(transactionInput)).thenReturn(
				new Transaction("id", "type", 100.00, date, "note","category"));

		Transaction transaction = transactionService.updateTransaction("id", transactionInput);
		Assertions.assertThat(transaction.getId()).isEqualTo("id");
		Assertions.assertThat(transaction.getType()).isEqualTo("type");
		Assertions.assertThat(transaction.getAmount()).isEqualTo(100.00);
		Assertions.assertThat(transaction.getDate()).isEqualTo(date);
		Assertions.assertThat(transaction.getNote()).isEqualTo("note");
	}

}
