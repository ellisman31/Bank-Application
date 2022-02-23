package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WithMockUser(username = "oakleyburns@gmail.com", password = "1234", roles={"USER,ADMIN"})
public class TestTransactionController {

    @Mock
    TransactionService transactionService;
    @InjectMocks
    TransactionController transactionController;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    @WithUserDetails("oakleyburns@gmail.com")
    public void testDoTransaction() throws Exception {
        mockMvc.perform(
                        put("/api/doTransaction")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"transactionType\": \"DEPOSIT\", \"transactionMoney\": \"3000\"}")
                                .with(csrf()))
                .andExpect(status().isOk());
    }

}
