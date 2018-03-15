package com.kiranreddy.budgettracker.category;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionCategoryTest {
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	
	@Test
	public void transactionCategoryCreationTest() {
		TransactionCategory transactionCategory =new TransactionCategory(1L,"category","type");
		Assertions.assertThat(transactionCategory.getId()).isEqualTo(1L);
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}

	@Test
	public void transactionCategoryMappingTest() {
		TransactionCategory transactionCategory =new TransactionCategory(null,"category","type");
		testEntityManager.persistAndFlush(transactionCategory);
		Assertions.assertThat(transactionCategory.getId()).isNotNull().isPositive();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}
}
