package com.kiranreddy.budgettracker.feedback;

import com.kiranreddy.budgettracker.transaction.TransactionService;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FeedbackService.class})
public class FeedbackServiceTest {

    @MockBean
    private SendGrid sendGrid;

    @Autowired
    private FeedbackService feedbackService;
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void sendMail() throws IOException {
        Response response = new Response();
        response.setStatusCode(200);
        Mockito.when(sendGrid.api(ArgumentMatchers.any())).thenReturn(response);
        Feedback feedback = new Feedback("Test");
        int statusCode = feedbackService.sendMail(feedback, "test@test.com");
        Assertions.assertThat(statusCode).isEqualTo(200);
    }


    @Test
    public void sendMailError() throws IOException {
        Mockito.when(sendGrid.api(ArgumentMatchers.any())).thenThrow(new IOException());
        Feedback feedback = new Feedback("Test");
        thrown.expect(RuntimeException.class);
        feedbackService.sendMail(feedback, "test@test.com");
    }
}