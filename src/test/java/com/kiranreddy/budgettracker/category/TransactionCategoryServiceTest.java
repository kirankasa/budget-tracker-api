package com.kiranreddy.budgettracker.category;

import com.kiranreddy.budgettracker.transaction.TransactionService;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TransactionCategoryService.class })
public class TransactionCategoryServiceTest {

	@MockBean
	private TransactionCategoryRepository transactionCategoryRepository;

	@MockBean
	private TransactionService transactionService;

	@Autowired
	private TransactionCategoryService transactionCategoryService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void retrieveTransactionCategoriesTest() {
		when(transactionCategoryRepository.findByUserId("id"))
				.thenReturn(Arrays.asList(new TransactionCategory("id", "category")));
		List<TransactionCategory> transactionCategories = transactionCategoryService.retrieveTransactionCategories("id");
		Assertions.assertThat(transactionCategories).hasSize(1);

		Assertions.assertThat(transactionCategories.iterator().next().getId()).isNotNull();
		Assertions.assertThat(transactionCategories.iterator().next().getCategory()).isEqualTo("category");
	}

	@Test
	public void retrieveValidTransactionCategoryTest() {
		when(transactionCategoryRepository.findById("id"))
				.thenReturn(Optional.of(new TransactionCategory("id", "category")));
		TransactionCategory transactionCategory = transactionCategoryService.retrieveTransactionCategoryById("id");

		Assertions.assertThat(transactionCategory.getId()).isNotNull();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}

	@Test
	public void retrieveInValidTransactionCategoryTest() {
		when(transactionCategoryRepository.findById("id")).thenReturn(Optional.empty());
		thrown.expect(TransactionCategoryNotFoundException.class);
		transactionCategoryService.retrieveTransactionCategoryById("id");
	}

	@Test
	public void saveCategoryTest() {
		TransactionCategory transactionCategoryInput = new TransactionCategory(null, "category");
		when(transactionCategoryRepository.save(transactionCategoryInput))
				.thenReturn(new TransactionCategory("id", "category"));
		TransactionCategory transactionCategory = transactionCategoryService
				.saveTransactionCategory(transactionCategoryInput);
		Assertions.assertThat(transactionCategory.getId()).isNotNull();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}

	@Test
	public void updateCategoryTest() {
		TransactionCategory transactionCategoryInput = new TransactionCategory("id", "categoryupdate");
		when(transactionCategoryRepository.findById("id")).thenReturn(Optional.of(transactionCategoryInput));
		when(transactionCategoryRepository.save(transactionCategoryInput))
				.thenReturn(new TransactionCategory("id", "categoryupdate"));
		TransactionCategory transactionCategory = transactionCategoryService
				.updateTransactionCategory(transactionCategoryInput, "id");
		Assertions.assertThat(transactionCategory.getId()).isNotNull();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("categoryupdate");
	}

}
