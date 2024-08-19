package com.example.registration_with_email_verification.service.verification;

import com.example.registration_with_email_verification.model.entity.User;
import com.example.registration_with_email_verification.service.user.UserService;
import com.example.registration_with_email_verification.util.exception.VerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class DefaultVerificationService implements VerificationService {

    private final UserService userService;

    @Override
    public void verify(String email, String token) {
        User user = userService.getUserByEmail(email);

        if (user.getConfirmationToken() == null || !user.getConfirmationToken().getToken().equals(token)) {
            throw new VerificationException("Invalid verification token");
        }
        if (now().isAfter(user.getConfirmationToken().getExpiresAt())) {
            throw new VerificationException("Verification token expired");
        }

        user.setEnabled(true);
        user.setConfirmationToken(null);
        userService.updateUser(user);
    }
}
