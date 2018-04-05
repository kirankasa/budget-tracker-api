package com.kiranreddy.budgettracker;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.kiranreddy.budgettracker.security.JwtUser;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		JwtUser principal = new JwtUser(1L, "kiran", "kiran", "reddy", "kiran@email.com", "", null, true);
		Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password",
				principal.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}
}