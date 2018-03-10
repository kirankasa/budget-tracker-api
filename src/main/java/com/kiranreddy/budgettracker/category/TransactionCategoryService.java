package com.kiranreddy.budgettracker.category;

import java.util.List;

public class TransactionCategoryService {

	private TransactionCategoryRepository transactionCategoryRepository;

	public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository) {
		this.transactionCategoryRepository = transactionCategoryRepository;
	}

	public List<TransactionCategory> retrieveTransactionCategories() {
		return transactionCategoryRepository.findAll();
	}

	public TransactionCategory retrieveTransactionCategoryById(Long transactionCategoryId) {
		return transactionCategoryRepository.findById(transactionCategoryId)
				.orElseThrow(() -> new TransactionCategoryNotFoundException(
						"Transaction Category Not found with id " + transactionCategoryId));
	}

	public List<TransactionCategory> retrieveTransactionCategoriesByType(String type) {
		return transactionCategoryRepository.findByType(type);
	}

	public TransactionCategory saveTransactionCategory(TransactionCategory transactionCategory) {
		return transactionCategoryRepository.save(transactionCategory);
	}

	public TransactionCategory updateTransactionCategory(TransactionCategory transactionCategoryInput,
			Long transactionCategoryId) {
		retrieveTransactionCategoryById(transactionCategoryId);
		return saveTransactionCategory(transactionCategoryInput);
	}

}
