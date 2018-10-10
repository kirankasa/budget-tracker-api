package com.kiranreddy.budgettracker.category;

import com.kiranreddy.budgettracker.transaction.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryService {

    private final TransactionCategoryRepository transactionCategoryRepository;
    private final TransactionService transactionService;

    public TransactionCategoryService(TransactionCategoryRepository transactionCategoryRepository,
                                      TransactionService transactionService) {
        this.transactionCategoryRepository = transactionCategoryRepository;
        this.transactionService = transactionService;
    }

    List<TransactionCategory> retrieveTransactionCategories(String userId) {
        return transactionCategoryRepository.findByUserId(userId);
    }

    TransactionCategory retrieveTransactionCategoryById(String transactionCategoryId) {
        return transactionCategoryRepository.findById(transactionCategoryId)
                .orElseThrow(() -> new TransactionCategoryNotFoundException(
                        "Transaction Category Not found with id " + transactionCategoryId));
    }

    public TransactionCategory saveTransactionCategory(TransactionCategory transactionCategory) {
        return transactionCategoryRepository.save(transactionCategory);
    }

    TransactionCategory updateTransactionCategory(TransactionCategory transactionCategoryInput,
                                                         String transactionCategoryId) {
        TransactionCategory existingTransactionCategory = retrieveTransactionCategoryById(transactionCategoryId);
        transactionService.updateTransactionCategoryForAllTransactions(
                existingTransactionCategory.getCategory(),
                transactionCategoryInput.getCategory(),
                transactionCategoryInput.getUserId()
        );
        return saveTransactionCategory(transactionCategoryInput);
    }

}
