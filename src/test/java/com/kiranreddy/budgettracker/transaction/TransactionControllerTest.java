package com.kiranreddy.budgettracker.transaction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiranreddy.budgettracker.WithMockCustomUser;
import com.kiranreddy.budgettracker.category.TransactionCategory;

@RunWith(SpringRunner.class)
@WebMvcTest({ TransactionController.class })
@WithMockCustomUser
public class TransactionControllerTest {

	@MockBean
	private TransactionService transactionService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void retrieveTransactionsTest() throws Exception {
		when(transactionService.retrieveTransactions(1L)).thenReturn(Arrays.asList(new Transaction(1L, "type", 100.00,
				new Date(), "note", new TransactionCategory(1L, "category", "type"))));
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].type").value("type"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].amount").value(100.00))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].date")
						.value(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].note").value("note"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].category.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].category.category").value("category"));
	}

	@Test
	public void retrieveTransactionTest() throws Exception {
		when(transactionService.findTransaction(1L)).thenReturn(new Transaction(1L, "type", 100.00, new Date(), "note",
				new TransactionCategory(1L, "category", "type")));
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("type"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.00))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date")
						.value(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.note").value("note"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category.category").value("category"));
	}

	@Test
	public void saveTransactionTest() throws Exception {
		Transaction transaction = new Transaction(null, "type", 100.00, new Date(), "note",
				new TransactionCategory(1L, "category", "type"));
		when(transactionService.saveTransaction(any())).thenReturn(new Transaction(1L, "type", 100.00, new Date(),
				"note", new TransactionCategory(1L, "category", "type")));
		mockMvc.perform(
				MockMvcRequestBuilders.post("/transactions").content(objectMapper.writeValueAsString(transaction))
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("type"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.00))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date")
						.value(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.note").value("note"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category.category").value("category"));
	}

	@Test
	public void updateTransactionTest() throws Exception {
		Transaction transaction = new Transaction(1L, "type", 100.00, new Date(), "note",
				new TransactionCategory(1L, "category", "type"));
		when(transactionService.updateTransaction(any(), any())).thenReturn(new Transaction(1L, "typeUpdated", 100.00,
				new Date(), "note", new TransactionCategory(1L, "category", "type")));
		mockMvc.perform(
				MockMvcRequestBuilders.put("/transactions/1").content(objectMapper.writeValueAsString(transaction))
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("typeUpdated"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.00))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date")
						.value(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.note").value("note"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category.category").value("category"));
	}

	@Test
	public void deletTransactionTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/1").with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		verify(transactionService, times(1)).deleteTransaction(1L);
	}

	@Test
	public void findInvalidTransactionTest() throws Exception {
		when(transactionService.findTransaction(1L))
				.thenThrow(new TransactionNotFoundException("Transaction not found"));
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/1"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
