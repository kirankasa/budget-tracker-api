package com.kiranreddy.budgettracker.feedback;

import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FeedbackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackService.class);

    public FeedbackService() {
    }

    public void sendMail(Feedback feedback) {
        String fromMail = feedback.getFromEmail() != null ? feedback.getFromEmail() : "kiranreddy2004@gmail.com";
        Email from = new Email(fromMail);
        String subject = "Expense tracker feedback";
        Email to = new Email("kiranreddy2004@gmail.com");
        Content content = new Content("text/plain", feedback.getMessage());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        try {
            request.setBody(mail.build());
            Response response = sg.api(request);
            LOGGER.info("Sendgrid  response status {}", response.getStatusCode());
        } catch (IOException e) {
            LOGGER.error("Exception occurred while sending mail ", e);
        }

    }
}
