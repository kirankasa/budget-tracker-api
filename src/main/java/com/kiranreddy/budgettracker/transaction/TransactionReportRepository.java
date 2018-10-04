package com.kiranreddy.budgettracker.transaction;


import java.util.List;

public interface TransactionReportRepository {
      List<AmountPerCategory> getAmountPerCategory(String userId, String monthAndYear, String transactionType);
}
