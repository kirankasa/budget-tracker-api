package com.kiranreddy.budgettracker.transaction;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public TransactionRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void updateTransactionCategoryForAllTransactions(String oldCategory, String newCategory, String userId) {
        Query select = Query.query(Criteria.where("category").is(oldCategory).and("userId").is(userId));
        Update update = new Update();
        update.set("category", newCategory);
        mongoTemplate.updateMulti(select, update, Transaction.class);
    }
}
