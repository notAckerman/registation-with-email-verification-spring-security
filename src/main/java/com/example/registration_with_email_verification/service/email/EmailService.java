package com.example.registration_with_email_verification.service.email;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
