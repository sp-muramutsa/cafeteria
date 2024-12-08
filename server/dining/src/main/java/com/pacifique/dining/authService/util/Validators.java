package com.pacifique.dining.authService.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public static boolean validatePassword(String password) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long, include an uppercase letter, "
                            + "a lowercase letter, a digit, and a special character."
            );
        }

        return true;
    }
}
