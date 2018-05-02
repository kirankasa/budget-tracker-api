package com.kiranreddy.budgettracker.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	List<Transaction> findByUserId(Long userId);

}
