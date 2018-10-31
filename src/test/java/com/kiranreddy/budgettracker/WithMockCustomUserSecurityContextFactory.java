package com.kiranreddy.budgettracker;


import com.kiranreddy.budgettracker.security.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication principal = new Authentication("id", "kiran@email.com");

        org.springframework.security.core.Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password",
                Collections.emptyList());
        context.setAuthentication(auth);
        return context;
    }
}