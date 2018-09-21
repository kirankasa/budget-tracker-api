package com.kiranreddy.budgettracker.transaction;

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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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
		when(transactionService.retrieveTransactions("id")).thenReturn(Arrays.asList(new Transaction("id", "type", 100.00,
				new Date(), "note", "category")));
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].type").value("type"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].amount").value(100.00))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].date")
						.value(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].note").value("note"))
				.andExpect(MockMvcResultMatchers.jsonPath("@.[0].category").value("category"));
	}

	@Test
	public void retrieveTransactionTest() throws Exception {
		when(transactionService.findTransaction("id")).thenReturn(new Transaction("id", "type", 100.00, new Date(), "note","category"));
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/id")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("type"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.00))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date")
						.value(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.note").value("note"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category").value("category"));
	}

	@Test
	public void saveTransactionTest() throws Exception {
		Transaction transaction = new Transaction(null, "type", 100.00, new Date(), "note","category");
		when(transactionService.saveTransaction(any())).thenReturn(new Transaction("id", "type", 100.00, new Date(),
				"note", "category"));
		mockMvc.perform(
				MockMvcRequestBuilders.post("/transactions").content(objectMapper.writeValueAsString(transaction))
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("type"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.00))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date")
						.value(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.note").value("note"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category").value("category"));
	}

	@Test
	public void updateTransactionTest() throws Exception {
		Transaction transaction = new Transaction("id", "type", 100.00, new Date(), "note","category");
		when(transactionService.updateTransaction(any(), any())).thenReturn(new Transaction("id", "typeUpdated", 100.00,
				new Date(), "note", "category"));
		mockMvc.perform(
				MockMvcRequestBuilders.put("/transactions/1").content(objectMapper.writeValueAsString(transaction))
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("id"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("typeUpdated"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.00))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date")
						.value(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.note").value("note"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.category").value("category"));
	}

	@Test
	public void deletTransactionTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/transactions/id").with(csrf()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		verify(transactionService, times(1)).deleteTransaction("id");
	}

	@Test
	public void findInvalidTransactionTest() throws Exception {
		when(transactionService.findTransaction("id"))
				.thenThrow(new TransactionNotFoundException("Transaction not found"));
		mockMvc.perform(MockMvcRequestBuilders.get("/transactions/id"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
