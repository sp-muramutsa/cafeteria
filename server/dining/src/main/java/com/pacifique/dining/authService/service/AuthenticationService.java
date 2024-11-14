package com.pacifique.dining.authService.service;

import com.pacifique.dining.authService.HttpMethods.AuthenticationRequest;
import com.pacifique.dining.authService.HttpMethods.AuthenticationResponse;
import com.pacifique.dining.authService.HttpMethods.RegisterRequest;
import com.pacifique.dining.authService.models.Dorm;
import com.pacifique.dining.authService.models.Role;
import com.pacifique.dining.authService.models.User;
import com.pacifique.dining.authService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
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
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
