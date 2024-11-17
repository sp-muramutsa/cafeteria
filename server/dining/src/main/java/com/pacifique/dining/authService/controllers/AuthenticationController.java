package com.pacifique.dining.authService.controllers;

import com.pacifique.dining.authService.HttpMethods.*;
import com.pacifique.dining.authService.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/register")
    public ResponseEntity<EmailVerificationToken> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody TokenRefreshRequest request
    ) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @RequestMapping("/verify")
    public ResponseEntity<AuthenticationResponse> verifyToken(
            @RequestParam("token") String token
    ) {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.verifyEmail(token);

            return ResponseEntity.ok(authenticationResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    AuthenticationResponse.builder()
                            .accessToken(null)
                            .refreshToken(null)
                            .build()
            );
        }
    }



}
