package com.pacifique.dining.authService.service;

import com.pacifique.dining.authService.HttpMethods.*;
import com.pacifique.dining.authService.models.Dorm;
import com.pacifique.dining.authService.models.Role;
import com.pacifique.dining.authService.models.User;
import com.pacifique.dining.authService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    // Helper method to generate access and refresh tokens
    private AuthenticationResponse generateTokenPair(User user) {
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public EmailVerificationToken register(RegisterRequest request) {
        // Check if the email already exists using findByEmail
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this email");
        }

        var user = User.builder()
                .email(request.getEmail())
                .mcneeseId(request.getMcneeseId())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .dorm(Dorm.valueOf(request.getDorm().toUpperCase()))
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // Generate a verification token
        EmailVerificationToken verificationToken = jwtService.generateEmailVerificationToken(user);

        // Send a welcome email to the newly registered user
        String subject = "Welcome to Rowdy's Dining Service!";
        String verificationLink = "http://localhost:8080/api/v1/auth/verify?token=" + verificationToken.getEmailVerificationToken();
        String text = "Hello " + user.getFirstname() + ",\n\nPlease verify your email by clicking the following link: " + verificationLink;
        emailService.sendEmail(user.getEmail(), subject, text);

        return verificationToken;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return generateTokenPair(user);
    }

    public AuthenticationResponse refreshToken(TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        String userEmail = jwtService.extractUserEmail(refreshToken);

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isValid = jwtService.isTokenValid(refreshToken, user);

        if (!isValid) {
            throw new RuntimeException("Invalid refresh token");
        }

        return generateTokenPair(user);
    }

    public AuthenticationResponse verifyEmail(String token) {
        String userEmail = jwtService.extractUserEmail(token);

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isValid = jwtService.isTokenValid(token, user);

        if (!isValid) {
            throw new RuntimeException("Invalid verification token");
        }

        // Set the user's Verified to true (indicating email is verified)
        user.setVerified(true);
        userRepository.save(user);

        return generateTokenPair(user);
    }
}
