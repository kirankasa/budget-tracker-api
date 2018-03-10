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
		Assertions.assertThat(transactionCategory.getType()).isEqualTo("type");
	}

	@Test
	public void fetchTransactionCategoryTest() {
		TransactionCategory transactionCategory = new TransactionCategory(1L, "category", "type");
		transactionCategoryRepository.save(transactionCategory);
		List<TransactionCategory> transactionCategories = transactionCategoryRepository.findAll();
		Assertions.assertThat(transactionCategories).hasSize(1);

		Assertions.assertThat(transactionCategories.iterator().next().getId()).isNotNull().isPositive();
		Assertions.assertThat(transactionCategories.iterator().next().getCategory()).isEqualTo("category");
		Assertions.assertThat(transactionCategories.iterator().next().getType()).isEqualTo("type");
	}

	@Test
	public void findByTypeTest() {
		TransactionCategory transactionCategory1 = new TransactionCategory(1L, "category", "type1");
		transactionCategoryRepository.save(transactionCategory1);
		TransactionCategory transactionCategory2 = new TransactionCategory(2L, "category", "type2");
		transactionCategoryRepository.save(transactionCategory2);

		List<TransactionCategory> transactionCategories = transactionCategoryRepository.findByType("type1");
		Assertions.assertThat(transactionCategories).hasSize(1);

		Assertions.assertThat(transactionCategories.iterator().next().getId()).isNotNull().isPositive();
		Assertions.assertThat(transactionCategories.iterator().next().getCategory()).isEqualTo("category");
		Assertions.assertThat(transactionCategories.iterator().next().getType()).isEqualTo("type1");
	}

}
