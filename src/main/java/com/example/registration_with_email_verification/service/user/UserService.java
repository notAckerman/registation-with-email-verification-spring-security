package com.example.registration_with_email_verification.service.user;

import com.example.registration_with_email_verification.model.entity.User;

public interface UserService {
    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void createUser(User user);

    void updateUser(User user);
}
