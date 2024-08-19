package com.example.registration_with_email_verification.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractSubject(String token);

    String generateToken(UserDetails userDetails);

    public Boolean isTokenValid(String token, UserDetails userDetails);
}
