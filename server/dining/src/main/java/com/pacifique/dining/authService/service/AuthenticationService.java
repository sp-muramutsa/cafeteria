package com.pacifique.dining.authService.service;

import com.pacifique.dining.authService.HttpMethods.AuthenticationRequest;
import com.pacifique.dining.authService.HttpMethods.AuthenticationResponse;
import com.pacifique.dining.authService.HttpMethods.RegisterRequest;
import com.pacifique.dining.authService.HttpMethods.TokenRefreshRequest;
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

    public AuthenticationResponse register(RegisterRequest request) {
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

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user =  userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(TokenRefreshRequest request){

        String refreshToken = request.getRefreshToken();
        String userEmail = jwtService.extractUserEmail(refreshToken);

         var user = userRepository.findByEmail(userEmail)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isValid = jwtService.isTokenValid(refreshToken, user);

        if(!isValid){
            throw new RuntimeException("Invalid refresh token");
        }


        var accessToken = jwtService.generateAccessToken(user);
        var newRefreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
