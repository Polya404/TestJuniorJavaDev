package org.example.test.services;

import org.example.test.models.User;

public interface UserService {
    void saveUser(User user);

    User getUserByUsername(String username);

    User getById(Long id);
}
