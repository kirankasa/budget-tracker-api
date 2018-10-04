package com.kiranreddy.budgettracker.transaction;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class TransactionReportRepositoryImpl implements TransactionReportRepository {

    private MongoTemplate mongoTemplate;

    public TransactionReportRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<AmountPerCategory> getAmountPerCategory(String userId, String monthAndYear, String transactionType) {
        ProjectionOperation projectMonthAndYear = project("date")
                .andExpression("date")
                .dateAsFormattedString("%m-%Y")
                .as("monthAndYear")
                .and("amount")
                .as("amount")
                .and("type")
                .as("type")
                .and("category")
                .as("category")
                .and("userId")
                .as("userId");

        GroupOperation groupByCategory = group("category")
                .sum("amount").as("amount");
        MatchOperation matchByMonthAndType = match(Criteria.where("monthAndYear").is(monthAndYear).and("type").is(transactionType).and("userId").is(userId));
        Aggregation aggregation = newAggregation(projectMonthAndYear, matchByMonthAndType, groupByCategory);
        AggregationResults<AmountPerCategory> amountPerCategories = mongoTemplate.aggregate(aggregation, "transaction", AmountPerCategory.class);
        return amountPerCategories.getMappedResults();
    }
}
