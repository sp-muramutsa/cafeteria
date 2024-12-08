package com.pacifique.dining.authService.service;

import com.pacifique.dining.authService.http.*;
import com.pacifique.dining.authService.entity.Dorm;
import com.pacifique.dining.authService.entity.Role;
import com.pacifique.dining.authService.entity.User;
import com.pacifique.dining.authService.repository.UserRepository;
import com.pacifique.dining.authService.util.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    // Helper method to generate access and refresh tokens
    public AuthenticationResponse generateTokenPair(User user) {
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

        // Validate email
        Validators.validatePassword(request.getPassword());

        var user = User.builder()
                .email(request.getEmail())
                .mcneeseId(request.getMcneeseId())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .dorm(request.getDorm() != null ? Dorm.valueOf(request.getDorm()) : null)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? Role.valueOf(request.getRole()) : Role.ROLE_STUDENT)
                .build();

        userRepository.save(user);

        // Generate a verification token
        EmailVerificationToken verificationToken = jwtService.generateEmailVerificationToken(user);

        // Send a welcome email to the newly registered user
        String subject = "Welcome to Rowdy's Dining Service!";
        String verificationLink = "http://localhost:3000/verify?token=" + verificationToken.getEmailVerificationToken();
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
