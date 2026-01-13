package com.vajraraksha.controller;

import com.vajraraksha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", userService.findAllUsers());

        // Mock Data for Analytics
        model.addAttribute("totalUsers", userService.findAllUsers().size());
        model.addAttribute("activeStudents", 15); // Mock
        model.addAttribute("completedLabs", 42); // Mock

        // Data arrays for Chart.js
        model.addAttribute("chartLabels", new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun" });
        model.addAttribute("chartDataUsers", new int[] { 5, 12, 19, 25, 30, 45 });
        model.addAttribute("chartDataLabs", new int[] { 2, 8, 15, 22, 35, 60 });

        return "admin/dashboard";
    }
}
