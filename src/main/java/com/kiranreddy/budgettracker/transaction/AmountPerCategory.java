package com.kiranreddy.budgettracker.transaction;

import org.springframework.data.annotation.Id;

public class AmountPerCategory {

    @Id
    private String category;
    private Integer totalAmount;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "AmountPerCategory{" +
                "category='" + category + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}

