package com.kiranreddy.budgettracker.category;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TransactionCategoryRepositoryTest {

	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;

	@Test
	public void saveTransactionCategoryTest() {
		TransactionCategory transactionCategory = new TransactionCategory(null, "category");
		transactionCategoryRepository.save(transactionCategory);

		Assertions.assertThat(transactionCategory.getId()).isNotNull();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}

	@Test
	public void fetchTransactionCategoryTest() {
		TransactionCategory transactionCategory = new TransactionCategory("id", "category");
		transactionCategoryRepository.save(transactionCategory);
		List<TransactionCategory> transactionCategories = transactionCategoryRepository.findAll();

		Assertions.assertThat(transactionCategories.iterator().next().getId()).isNotNull();
		Assertions.assertThat(transactionCategories.iterator().next().getCategory()).isEqualTo("category");
	}
}
