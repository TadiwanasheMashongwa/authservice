package com.tadiwanashe.authservice.repository;

import com.tadiwanashe.authservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    // Arrange
    @Test
    void shouldFindUserByEmail_whenUserExists() {
        // Arrange
        User user = new User();
        user.setUsername("tadiwanashe");
        user.setEmail("tadi@example.com");
        user.setPassword("hashedpassword123");
        userRepository.save(user);

        // Act
        Optional<User> found = userRepository.findByEmail("tadi@example.com");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("tadi@example.com");
        assertThat(found.get().getUsername()).isEqualTo("tadiwanashe");
    };
    @Test
    void shouldReturnEmpty_whenUserDoesNotExist() {
        // Act
        Optional<User> found = userRepository.findByEmail("ghost@example.com");

        // Assert
        assertThat(found).isEmpty();
    }


}
