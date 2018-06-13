package com.kiranreddy.budgettracker.feedback;

import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);

    private SendGrid sendGrid;

    public FeedbackService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public int sendMail(Feedback feedback, String fromEmail) {
        Email from = new Email(fromEmail);
        String subject = "Expense tracker feedback";
        Email to = new Email("kiranreddy2004@gmail.com");
        Content content = new Content("text/plain", feedback.getMessage());
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        try {
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            LOGGER.info("SendGrid  response status {}", response.getStatusCode());
            return response.getStatusCode();
        } catch (IOException e) {
            LOGGER.error("Exception occurred while sending mail ", e);
            throw new RuntimeException("Exception occurred while sending email");
        }

    }
}
