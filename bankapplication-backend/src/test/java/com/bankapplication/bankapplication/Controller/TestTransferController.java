package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Service.TransferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TestTransferController {

    @Mock
    TransferService transferService;
    @InjectMocks
    TransferController transferController;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
    }

    @Test
    @WithUserDetails("oakleyburns@gmail.com")
    public void testTransferMoney() throws Exception {

        mockMvc.perform(
                        post("/api/transferMoney")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"transferMoney\": \"0\", \"transferReceiverEmail\": \"chrismay@gmail.com\"}")
                                .with(csrf()))
                .andExpect(status().isOk());
    }

}
