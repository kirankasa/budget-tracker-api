package com.kiranreddy.budgettracker.feedback;

public class Feedback {

    String message;
    String fromEmail;

    public Feedback() {
    }

    public Feedback(String message, String fromEmail) {
        this.message = message;
        this.fromEmail = fromEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }
}
