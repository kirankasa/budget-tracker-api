package com.kiranreddy.budgettracker.category;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionCategoryRepositoryTest {

	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;

	@Test
	public void saveTransactionCategoryTest() {
		TransactionCategory transactionCategory = new TransactionCategory(null, "category", "type");
		transactionCategoryRepository.save(transactionCategory);

		Assertions.assertThat(transactionCategory.getId()).isNotNull().isPositive();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}

	@Test
	public void fetchTransactionCategoryTest() {
		TransactionCategory transactionCategory = new TransactionCategory(1L, "category", "type");
		transactionCategoryRepository.save(transactionCategory);
		List<TransactionCategory> transactionCategories = transactionCategoryRepository.findAll();
		Assertions.assertThat(transactionCategories).hasSize(1);

		Assertions.assertThat(transactionCategories.iterator().next().getId()).isNotNull().isPositive();
		Assertions.assertThat(transactionCategories.iterator().next().getCategory()).isEqualTo("category");
	}
}
