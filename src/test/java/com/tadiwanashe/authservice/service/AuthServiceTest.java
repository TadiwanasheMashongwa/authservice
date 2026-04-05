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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    @Mock
    private JwtService jwtService;

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
    @Test
    void shouldSaveUser_whenRegistrationIsValid() {
        // Arrange
        when(userRepository.findByEmail("tadi@example.com"))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123"))
                .thenReturn("hashedpassword123");

        // Act
        authService.register("tadiwanashe", "tadi@example.com", "password123");

        // Assert
        verify(userRepository).save(any(User.class));
    }
    @Test
    void shouldReturnToken_whenLoginIsSuccessful() {
        // Arrange
        User user = new User();
        user.setEmail("tadi@example.com");
        user.setPassword("hashedpassword123");

        when(userRepository.findByEmail("tadi@example.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashedpassword123"))
                .thenReturn(true);
        when(jwtService.generateToken("tadi@example.com"))
                .thenReturn("mocked.jwt.token");

        // Act
        String token = authService.login("tadi@example.com", "password123");

        // Assert
        assertThat(token).isEqualTo("mocked.jwt.token");
    }

}