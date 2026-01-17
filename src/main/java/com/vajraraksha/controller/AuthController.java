package com.vajraraksha.controller;

import com.vajraraksha.model.Role;
import com.vajraraksha.model.User;
import com.vajraraksha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam String role, Model model) {
        // Password Validation
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (!user.getPassword().matches(passwordPattern)) {
            model.addAttribute("error",
                    "Password must be at least 8 chars, contain one uppercase, one lowercase, one number, and one special char.");
            model.addAttribute("user", user); // Keep filled data
            return "register";
        }

        user.setRole(Role.valueOf(role));
        try {
            userService.registerUser(user);
        } catch (Exception e) {
            model.addAttribute("error", "Username or Email already exists.");
            model.addAttribute("user", user);
            return "register";
        }
        return "redirect:/login?registered=true";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else if (auth != null
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
            return "redirect:/student/dashboard";
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String home() {
        return "index"; // Assuming we'll have an index.jsp
    }
}
