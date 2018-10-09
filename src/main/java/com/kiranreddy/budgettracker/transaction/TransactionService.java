package com.kiranreddy.budgettracker.transaction;

import com.kiranreddy.budgettracker.user.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionReportRepository transactionReportRepository;
    private final TransactionRepositoryCustom transactionRepositoryCustom;

    public TransactionService(TransactionRepository transactionRepository,
                              TransactionReportRepository transactionReportRepository,
                              TransactionRepositoryCustom transactionRepositoryCustom) {
        this.transactionRepository = transactionRepository;
        this.transactionReportRepository = transactionReportRepository;
        this.transactionRepositoryCustom = transactionRepositoryCustom;
    }

    public List<Transaction> retrieveTransactions(String userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Transaction findTransaction(String transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        Transaction transaction = optionalTransaction
                .orElseThrow(() -> new UserNotFoundException("No Transaction found with  id " + transactionId));
        return transaction;
    }

    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    public void deleteTransaction(String transactionId) {
        Transaction transaction = findTransaction(transactionId);
        deleteTransaction(transaction);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(String transactionId, Transaction transactionInput) {
        findTransaction(transactionId);
        return transactionRepository.save(transactionInput);
    }

    List<AmountPerCategory> getAmountPerCategory(String userId, String monthAndYear, String transactionType) {
        return transactionReportRepository.getAmountPerCategory(userId, monthAndYear, transactionType);
    }

    public void updateTransactionCategoryForAllTransactions(String oldCategory, String newCategory, String userId) {
        transactionRepositoryCustom.updateTransactionCategoryForAllTransactions(oldCategory,newCategory,userId);
    }

}
