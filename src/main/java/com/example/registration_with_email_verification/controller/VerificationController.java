package com.example.registration_with_email_verification.controller;

import com.example.registration_with_email_verification.service.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("email") String email, @RequestParam("token") String token) {
        verificationService.verify(email, token);
        return ResponseEntity.ok("Email successfully verified!");
    }
}
