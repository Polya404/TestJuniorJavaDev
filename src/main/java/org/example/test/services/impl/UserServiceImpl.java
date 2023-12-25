package org.example.test.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.test.models.User;
import org.example.test.repositories.UserRepository;
import org.example.test.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
