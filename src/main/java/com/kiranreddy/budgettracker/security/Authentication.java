package com.kiranreddy.budgettracker.security;

public class Authentication {

    private String userId;
    private String email;

    public Authentication(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
