package com.kiranreddy.budgettracker.transaction;

public interface TransactionRepositoryCustom {

    void updateTransactionCategoryForAllTransactions(String oldCategory, String newCategory, String userId);
}
