package com.kiranreddy.budgettracker.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {

	List<TransactionCategory> findByType(String type);

}
