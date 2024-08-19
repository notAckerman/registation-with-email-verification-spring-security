package com.example.registration_with_email_verification.service.authentication;

import com.example.registration_with_email_verification.model.dto.AuthenticationResponse;
import com.example.registration_with_email_verification.model.dto.LoginRequest;
import com.example.registration_with_email_verification.model.dto.RegisterRequest;
import com.example.registration_with_email_verification.model.entity.ConfirmationToken;
import com.example.registration_with_email_verification.model.entity.User;
import com.example.registration_with_email_verification.model.enums.UserRole;
import com.example.registration_with_email_verification.service.email.EmailService;
import com.example.registration_with_email_verification.service.jwt.JwtService;
import com.example.registration_with_email_verification.service.user.UserService;
import com.example.registration_with_email_verification.util.exception.EmailNotConfirmedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    @Value("${app.domain}")
    private final String DOMAIN;

    @Override
    public void register(RegisterRequest request) {
        User user = getUser(request);
        sendVerificationToken(user.getEmail(), user.getConfirmationToken().getToken());
        userService.createUser(user);
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        User user = userService.getUserByUsername(request.getUsername());

        if (!user.isEnabled()) {
            throw new EmailNotConfirmedException("Account not verified. Please verify your account and try again.");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        return new AuthenticationResponse(jwtService.generateToken(user));
    }

    private User getUser(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .confirmationToken(createToken())
                .role(UserRole.ROLE_USER)
                .enabled(false)
                .password(encoder.encode(request.getPassword()))
                .build();
    }

    private ConfirmationToken createToken() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(generateVerificationToken());
        confirmationToken.setExpiresAt(now().plusMinutes(30));
        return confirmationToken;
    }

    private void sendVerificationToken(String email, String token) {
        String subject = "Email Verification";
        String url = DOMAIN + "/verify?email=" + email + "&token=" + token;
        String body = "Please verify your email by clicking the following link: " + url +
                "\nThis link is valid for 1 hour.";

        emailService.sendEmail(email, subject, body);
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }
}
