package com.example.registration_with_email_verification.service.authentication;

import com.example.registration_with_email_verification.model.dto.AuthenticationResponse;
import com.example.registration_with_email_verification.model.dto.LoginRequest;
import com.example.registration_with_email_verification.model.dto.RegisterRequest;

public interface AuthenticationService {
    void register(RegisterRequest request);

    AuthenticationResponse login(LoginRequest request);
}
