package com.vajraraksha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired(required = false)
    @Lazy
    private JavaMailSender mailSender;

    @Async
    public void sendOtp(String to, String otp) {
        String subject = "Your VajraRaksha Verification Code";
        String text = "Your One-Time Password (OTP) for verifying your profile change is: " + otp
                + "\n\nThis code will expire in 5 minutes.\n\nStay Secure,\nVajraRaksha Team";

        // In a real scenario with SMTP configured, we would send the mail.
        // For now, if mailSender is not configured (no application.properties entry),
        // we fallback to console log for testing.
        if (mailSender != null) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(to);
                message.setSubject(subject);
                message.setText(text);
                mailSender.send(message);
                System.out.println("EMAIL SENT to " + to + ": " + otp);
            } catch (Exception e) {
                System.err.println("FAILED TO SEND EMAIL: " + e.getMessage());
                // Fallback log
                System.out.println("DEBUG OTP for " + to + ": " + otp);
            }
        } else {
            System.out.println("DEBUG OTP for " + to + ": " + otp);
        }
    }
}
