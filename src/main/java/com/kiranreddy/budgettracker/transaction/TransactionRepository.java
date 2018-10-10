package com.kiranreddy.budgettracker.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
	
	Page<Transaction> findByUserId(String userId, Pageable pageable);

}
