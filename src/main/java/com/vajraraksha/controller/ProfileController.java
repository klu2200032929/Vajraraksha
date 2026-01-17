package com.vajraraksha.controller;

import com.vajraraksha.model.User;
import com.vajraraksha.repository.UserRepository;
import com.vajraraksha.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Random;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null)
            return "redirect:/login";
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/profile/update-info")
    public String updateInfo(@RequestParam String username, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            currentUser.setUsername(username);
            userRepository.save(currentUser);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        }
        return "redirect:/profile";
    }

    @PostMapping("/profile/send-otp")
    public String sendOtp(RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            String otp = String.format("%06d", new Random().nextInt(999999));
            currentUser.setOtp(otp);
            currentUser.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
            userRepository.save(currentUser);

            emailService.sendOtp(currentUser.getEmail(), otp);
            redirectAttributes.addFlashAttribute("info",
                    "OTP sent to verify password change. Check your email (or console for debug).");
            redirectAttributes.addFlashAttribute("otpSent", true);
        }
        return "redirect:/profile";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam String otp,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match!");
            redirectAttributes.addFlashAttribute("otpSent", true); // Keep the form open
            return "redirect:/profile";
        }

        User currentUser = getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getOtp() == null || !currentUser.getOtp().equals(otp)) {
                redirectAttributes.addFlashAttribute("error", "Invalid OTP!");
                redirectAttributes.addFlashAttribute("otpSent", true);
                return "redirect:/profile";
            }

            if (currentUser.getOtpExpiry().isBefore(LocalDateTime.now())) {
                redirectAttributes.addFlashAttribute("error", "OTP has expired. Please request a new one.");
                redirectAttributes.addFlashAttribute("otpSent", true);
                return "redirect:/profile";
            }

            // OTP Verified
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            currentUser.setOtp(null); // Clear OTP
            currentUser.setOtpExpiry(null);
            userRepository.save(currentUser);
            redirectAttributes.addFlashAttribute("success", "Password changed successfully!");
        }
        return "redirect:/profile";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return userRepository.findByUsername(auth.getName()).orElse(null);
        }
        return null;
    }
}
