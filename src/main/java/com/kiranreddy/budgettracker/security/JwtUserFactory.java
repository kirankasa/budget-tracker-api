package com.kiranreddy.budgettracker.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.kiranreddy.budgettracker.user.User;

public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getPassword(), mapToGrantedAuthorities(Arrays.asList("ROLE_USER")), true);
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority))
				.collect(Collectors.toList());
	}
}