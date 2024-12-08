package com.pacifique.dining.authService.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String mcneeseId;
    private String Email;
    private String firstname;
    private String lastname;
    private String dorm;
    private String role;
    private String password;
}
