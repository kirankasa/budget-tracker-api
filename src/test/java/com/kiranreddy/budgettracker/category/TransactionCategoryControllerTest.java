package com.kiranreddy.budgettracker.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiranreddy.budgettracker.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = { TransactionCategoryController.class })
@WithMockCustomUser
public class TransactionCategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionCategoryService transactionCategoryService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void retrieveCategoriesTest() throws Exception {
		when(transactionCategoryService.retrieveTransactionCategories("id"))
				.thenReturn(Arrays.asList(new TransactionCategory("id", "category")));
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/categories"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].category").value("category"));
	}

	@Test
	public void retrieveValidCategoryTest() throws Exception {
		when(transactionCategoryService.retrieveTransactionCategoryById("id"))
				.thenReturn(new TransactionCategory("id", "category"));
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/categories/id"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category").value("category"));
	}

	@Test
	public void retrieveInValidCategoryTest() throws Exception {
		when(transactionCategoryService.retrieveTransactionCategoryById("id"))
				.thenThrow(new TransactionCategoryNotFoundException("Transaction category not found"));
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/categories/id"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void saveCategoryTest() throws Exception {
		when(transactionCategoryService.saveTransactionCategory(any()))
				.thenReturn(new TransactionCategory("id", "category"));
		mockMvc.perform(MockMvcRequestBuilders.post("/transactions/categories")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(new TransactionCategory(null, "category")))
				.with(csrf())).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category").value("category"));
	}

	@Test
	public void updateCategoryTest() throws Exception {
		when(transactionCategoryService.updateTransactionCategory(any(), any()))
				.thenReturn(new TransactionCategory("id", "category"));
		mockMvc.perform(MockMvcRequestBuilders.put("/transactions/categories/id")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(objectMapper.writeValueAsString(new TransactionCategory("id", "category"))).with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category").value("category"));
	}
}
