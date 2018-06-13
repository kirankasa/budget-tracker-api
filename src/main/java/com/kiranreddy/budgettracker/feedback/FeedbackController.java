package com.kiranreddy.budgettracker.feedback;

import com.kiranreddy.budgettracker.security.JwtUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public String feedback(@AuthenticationPrincipal JwtUser user, @RequestBody Feedback feedback) {
        feedbackService.sendMail(feedback, user.getEmail());
        return "SUCCESS";
    }
}
