package com.kiranreddy.budgettracker.category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionCategoryRepository extends MongoRepository<TransactionCategory, String> {

	List<TransactionCategory> findByUserId(String userId);

}
