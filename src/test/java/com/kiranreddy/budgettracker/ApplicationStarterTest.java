package com.kiranreddy.budgettracker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ApplicationStarterTest {

	@Test
	public void applicationStarts() {
		BudgetTrackerApplication.main(new String[] {});
	}

}
