package org.example.test.controllers;

import org.example.test.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public record RegistrationForm(String username, String password,
                               String phoneNumber) {
    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password),
                phoneNumber);
    }
}
