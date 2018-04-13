package com.kiranreddy.budgettracker.category;

import static org.mockito.Mockito.when;

import java.util.Arrays;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TransactionCategoryService.class })
public class TransactionCategoryServiceTest {

	@MockBean
	private TransactionCategoryRepository transactionCategoryRepository;

	@Autowired
	private TransactionCategoryService transactionCategoryService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void retrieveTransactionCategoriesTest() {
		when(transactionCategoryRepository.findByUserId(1L))
				.thenReturn(Arrays.asList(new TransactionCategory(1L, "category", "type")));
		List<TransactionCategory> transactionCategories = transactionCategoryService.retrieveTransactionCategories(1L);
		Assertions.assertThat(transactionCategories).hasSize(1);

		Assertions.assertThat(transactionCategories.iterator().next().getId()).isNotNull().isPositive();
		Assertions.assertThat(transactionCategories.iterator().next().getCategory()).isEqualTo("category");
	}

	@Test
	public void retrieveValidTransactionCategoryTest() {
		when(transactionCategoryRepository.findById(1L))
				.thenReturn(Optional.of(new TransactionCategory(1L, "category", "type")));
		TransactionCategory transactionCategory = transactionCategoryService.retrieveTransactionCategoryById(1L);

		Assertions.assertThat(transactionCategory.getId()).isNotNull().isPositive();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}

	@Test
	public void retrieveInValidTransactionCategoryTest() {
		when(transactionCategoryRepository.findById(1L)).thenReturn(Optional.empty());
		thrown.expect(TransactionCategoryNotFoundException.class);
		transactionCategoryService.retrieveTransactionCategoryById(1L);
	}

	@Test
	public void saveCategoryTest() {
		TransactionCategory transactionCategoryInput = new TransactionCategory(null, "category", "type");
		when(transactionCategoryRepository.save(transactionCategoryInput))
				.thenReturn(new TransactionCategory(1L, "category", "type"));
		TransactionCategory transactionCategory = transactionCategoryService
				.saveTransactionCategory(transactionCategoryInput);
		Assertions.assertThat(transactionCategory.getId()).isNotNull().isPositive();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}

	@Test
	public void updateCategoryTest() {
		TransactionCategory transactionCategoryInput = new TransactionCategory(1L, "categoryupdate", "type");
		when(transactionCategoryRepository.findById(1L)).thenReturn(Optional.of(transactionCategoryInput));
		when(transactionCategoryRepository.save(transactionCategoryInput))
				.thenReturn(new TransactionCategory(1L, "categoryupdate", "type"));
		TransactionCategory transactionCategory = transactionCategoryService
				.updateTransactionCategory(transactionCategoryInput, 1L);
		Assertions.assertThat(transactionCategory.getId()).isNotNull().isPositive();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("categoryupdate");
	}

}
