package com.kiranreddy.budgettracker.category;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TransactionCategoryTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void transactionCategoryCreationTest() {
		TransactionCategory transactionCategory =new TransactionCategory("id","category");
		Assertions.assertThat(transactionCategory.getId()).isEqualTo("id");
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}

	@Test
	public void transactionCategoryMappingTest() {
		TransactionCategory transactionCategory =new TransactionCategory(null,"category");
		mongoTemplate.save(transactionCategory);
		Assertions.assertThat(transactionCategory.getId()).isNotNull();
		Assertions.assertThat(transactionCategory.getCategory()).isEqualTo("category");
	}
}
