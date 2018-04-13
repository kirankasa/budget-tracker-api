package com.kiranreddy.budgettracker.category;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TransactionCategoryService {

	private TransactionCategoryRepository transactionCategoryRepository;

	public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository) {
		this.transactionCategoryRepository = transactionCategoryRepository;
	}

	public List<TransactionCategory> retrieveTransactionCategories(Long userId) {
		return transactionCategoryRepository.findByUserId(userId);
	}

	public TransactionCategory retrieveTransactionCategoryById(Long transactionCategoryId) {
		return transactionCategoryRepository.findById(transactionCategoryId)
				.orElseThrow(() -> new TransactionCategoryNotFoundException(
						"Transaction Category Not found with id " + transactionCategoryId));
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
