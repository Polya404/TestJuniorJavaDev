package org.example.test.modelsForTest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserModel {
    private final String username;
    private final String password;
    private final String phoneNumber;
}

