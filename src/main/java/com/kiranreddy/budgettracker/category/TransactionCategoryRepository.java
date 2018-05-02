package com.kiranreddy.budgettracker.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {

	List<TransactionCategory> findByUserId(Long userId);

}
