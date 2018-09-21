package com.kiranreddy.budgettracker.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryService {

	private TransactionCategoryRepository transactionCategoryRepository;

	public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository) {
		this.transactionCategoryRepository = transactionCategoryRepository;
	}

	public List<TransactionCategory> retrieveTransactionCategories(String userId) {
		return transactionCategoryRepository.findByUserId(userId);
	}

	public TransactionCategory retrieveTransactionCategoryById(String transactionCategoryId) {
		return transactionCategoryRepository.findById(transactionCategoryId)
				.orElseThrow(() -> new TransactionCategoryNotFoundException(
						"Transaction Category Not found with id " + transactionCategoryId));
	}

	public TransactionCategory saveTransactionCategory(TransactionCategory transactionCategory) {
		return transactionCategoryRepository.save(transactionCategory);
	}

	public TransactionCategory updateTransactionCategory(TransactionCategory transactionCategoryInput,
			String transactionCategoryId) {
		retrieveTransactionCategoryById(transactionCategoryId);
		return saveTransactionCategory(transactionCategoryInput);
	}

}
