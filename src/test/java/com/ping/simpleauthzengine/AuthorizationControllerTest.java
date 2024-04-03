package com.ping.simpleauthzengine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationEngine authorizationEngine;

    @Test
    void test200IsReturnedWhenPermit() throws Exception {
        doReturn(Decision.PERMIT).when(authorizationEngine).evaluate(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/authorize")
                        .content("{\"something\":1}")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void test403IsReturnedWhenDeny() throws Exception {
        doReturn(Decision.DENY).when(authorizationEngine).evaluate(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/authorize")
                        .content("{\"something\":1}")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
