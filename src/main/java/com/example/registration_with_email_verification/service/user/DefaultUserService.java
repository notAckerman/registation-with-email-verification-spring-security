package com.example.registration_with_email_verification.service.user;

import com.example.registration_with_email_verification.model.entity.User;
import com.example.registration_with_email_verification.reposutiry.UserRepository;
import com.example.registration_with_email_verification.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        // Some logic
        userRepository.save(user);
    }
}
