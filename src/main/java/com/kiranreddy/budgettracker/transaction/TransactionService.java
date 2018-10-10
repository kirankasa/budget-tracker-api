package com.kiranreddy.budgettracker.transaction;

import com.kiranreddy.budgettracker.user.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    Page<Transaction> retrieveTransactions(String userId, int page, int limit) {
        return transactionRepository.findByUserId(userId, PageRequest.of(page, limit, Sort.by("salary")));
    }

    Transaction findTransaction(String transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        return optionalTransaction
                .orElseThrow(() -> new UserNotFoundException("No Transaction found with  id " + transactionId));
    }

    void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    void deleteTransaction(String transactionId) {
        Transaction transaction = findTransaction(transactionId);
        deleteTransaction(transaction);
    }

    Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    Transaction updateTransaction(String transactionId, Transaction transactionInput) {
        findTransaction(transactionId);
        return transactionRepository.save(transactionInput);
    }

    List<AmountPerCategory> getAmountPerCategory(String userId, String monthAndYear, String transactionType) {
        return transactionReportRepository.getAmountPerCategory(userId, monthAndYear, transactionType);
    }

    public void updateTransactionCategoryForAllTransactions(String oldCategory, String newCategory, String userId) {
        transactionRepositoryCustom.updateTransactionCategoryForAllTransactions(oldCategory, newCategory, userId);
    }

}
