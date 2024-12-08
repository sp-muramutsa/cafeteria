package com.pacifique.dining.authService.controller;

import com.pacifique.dining.authService.entity.Role;
import com.pacifique.dining.authService.entity.User;
import com.pacifique.dining.authService.http.EmailVerificationToken;
import com.pacifique.dining.authService.http.RegisterRequest;
import com.pacifique.dining.authService.repository.UserRepository;
import com.pacifique.dining.authService.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<EmailVerificationToken> createUser(@Valid @RequestBody RegisterRequest request) {
        EmailVerificationToken token = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") UUID id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable("id") UUID id, @RequestBody User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(user.getEmail());
                    existingUser.setMcneeseId(user.getMcneeseId());
                    existingUser.setFirstname(user.getFirstname());
                    existingUser.setLastname(user.getLastname());
                    existingUser.setDorm(user.getDorm());
                    existingUser.setRole(user.getRole());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setVerified(user.isVerified());

                    User updatedUser = userRepository.save(existingUser);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }


    @GetMapping("roles/{role}")
    public ResponseEntity<List<User>> findByRole(@PathVariable("role") String role) {
        try {
            Role userRole = Role.valueOf(role);
            List<User> users = userRepository.findAllByRole(userRole);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAllById(@RequestBody List<UUID> ids) {

        if (ids.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<User> usersToDelete = userRepository.findAllById(ids);
        if (!usersToDelete.isEmpty()) {
            userRepository.deleteAll(usersToDelete);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<List<User>> updateAllById(@RequestBody List<User> users) {
        if (users.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<UUID> ids = users.stream().map(User::getId).collect(Collectors.toList());

        List<User> usersToUpdate = userRepository.findAllById(ids);

        if (usersToUpdate.size() != users.size()) {
            return ResponseEntity.badRequest().build();
        }

        for (int i = 0; i < users.size(); i++) {
            User updatedUser = users.get(i);
            User existingUser = usersToUpdate.get(i);

            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setMcneeseId(updatedUser.getMcneeseId());
            existingUser.setFirstname(updatedUser.getFirstname());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setDorm(updatedUser.getDorm());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setVerified(updatedUser.isVerified());

            userRepository.save(existingUser);
        }

        return ResponseEntity.ok(usersToUpdate);
    }
}
