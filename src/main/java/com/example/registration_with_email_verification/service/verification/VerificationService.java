package com.example.registration_with_email_verification.service.verification;

public interface VerificationService {
    void verify(String email, String token);
}
