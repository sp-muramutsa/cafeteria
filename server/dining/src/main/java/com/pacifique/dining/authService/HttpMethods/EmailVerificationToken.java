package com.pacifique.dining.authService.HttpMethods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationToken {
    private String emailVerificationToken;
}

