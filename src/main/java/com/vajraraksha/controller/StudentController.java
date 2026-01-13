package com.vajraraksha.controller;

import com.vajraraksha.model.User;
import com.vajraraksha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private com.vajraraksha.repository.CourseRepository courseRepository;
    @Autowired
    private com.vajraraksha.repository.LabRepository labRepository;
    @Autowired
    private com.vajraraksha.repository.UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            User user = userService.findByUsername(userDetails.getUsername()).orElse(new User());
            model.addAttribute("user", user);
        }

        // Fetch Dynamic Content
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("labs", labRepository.findAll());

        // Leaderboard (Top 5 Students)
        model.addAttribute("leaderboard",
                userRepository.findTop5ByRoleOrderByPointsDesc(com.vajraraksha.model.Role.STUDENT));

        return "student/dashboard";
    }
}
