package com.kiranreddy.budgettracker.user;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordTest {

	@Test
	public void test() {
		BCrypt.checkpw("password", "$2a$10$6yP5dF6/iBIqRySFt3Yi6.1wMwTpdFCwARJJIkWxnPP8/s0zsZgbq");
	}

}
