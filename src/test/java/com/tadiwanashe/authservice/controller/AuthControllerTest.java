package com.tadiwanashe.authservice.controller;

import com.tadiwanashe.authservice.config.SecurityConfig;
import com.tadiwanashe.authservice.service.AuthService;
import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;
    @Test
    void shouldReturn201_whenRegistrationIsSuccessful() throws Exception {
        // Arrange
        String requestBody = """
            {
                "username": "tadiwanashe",
                "email": "tadi@example.com",
                "password": "password123"
            }
            """;

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

}