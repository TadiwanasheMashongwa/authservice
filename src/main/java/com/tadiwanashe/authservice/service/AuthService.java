package com.tadiwanashe.authservice.service;

import com.tadiwanashe.authservice.entity.RefreshToken;
import com.tadiwanashe.authservice.entity.User;
import com.tadiwanashe.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }
    public String refreshToken(String token) {
        RefreshToken refreshToken = refreshTokenService.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshTokenService.isExpired(refreshToken)) {
            throw new RuntimeException("Refresh token expired");
        }

        return jwtService.generateToken(refreshToken.getUser().getEmail());
    }

    public void register(String username, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public String[] login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String accessToken = jwtService.generateToken(email);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return new String[]{accessToken, refreshToken.getToken()};
    }
    public void logout(String token) {
        refreshTokenService.findByToken(token)
                .ifPresent(refreshToken ->
                        refreshTokenService.deleteByUser(refreshToken.getUser()));
    }
}