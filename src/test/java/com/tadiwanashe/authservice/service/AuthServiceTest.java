package com.tadiwanashe.authservice.service;

import com.tadiwanashe.authservice.entity.User;
import com.tadiwanashe.authservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldThrowException_whenEmailAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail("tadi@example.com"))
                .thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                authService.register("tadiwanashe", "tadi@example.com", "password123"));
    }
    @Test
    void shouldHashPassword_whenRegisteringNewUser() {
        // Arrange
        when(userRepository.findByEmail("tadi@example.com"))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123"))
                .thenReturn("hashedpassword123");
        // Act
        authService.register("tadiwanashe", "tadi@example.com", "password123");
        // Assert
        verify(passwordEncoder).encode("password123");

    }

}