package com.bankapplication.bankapplication.Controller;

import com.bankapplication.bankapplication.Model.User;
import com.bankapplication.bankapplication.Service.UserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WithMockUser(username = "oakleyburns@gmail.com", password = "1234", roles={"USER,ADMIN"})
public class TestUserController {

    @Mock
    UserService userService;
    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testInsertUser() throws Exception {

        mockMvc.perform(
                post("/auth/api/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\": \"John\", \"lastName\": \"Last\", \"emailAddress\": \"johnlast@gmail.com\", \"password\": \"1234\"}")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService).saveUser(any(User.class));

    }


    @Test
    public void testOwnBalance() throws Exception {

        mockMvc.perform(
                get("/api/ownBalance"))
                .andExpect(status().isOk());

    }

    @Test
    public void testOwnInformation() throws Exception {

        mockMvc.perform(
                get("/api/ownInformation"))
                .andExpect(status().isOk());

    }

}
