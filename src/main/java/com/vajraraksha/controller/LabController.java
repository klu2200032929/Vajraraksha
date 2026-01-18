package com.vajraraksha.controller;

import com.vajraraksha.model.Lab;
import com.vajraraksha.service.GamificationService;
import com.vajraraksha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;

@Controller
@RequestMapping("/student/lab")
@SuppressWarnings("null")
public class LabController {

    @Autowired
    private com.vajraraksha.repository.LabRepository labRepository;

    @Autowired
    private GamificationService gamificationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public String openLab(@PathVariable String id, Model model) {
        // Try to find in DB, else mock
        Lab lab = labRepository.findById(id).orElse(new Lab());

        if (lab.getId() == null) {
            String safeId = (id != null) ? id : "default";
            lab.setId(safeId);
            lab.setTitle("Operation: SQL Storm (Demo)");
            lab.setDescription("Target: 192.168.1.50\nObjective: Find the admin password.");
            lab.setDifficulty("Intermediate");
        }

        model.addAttribute("lab", lab);

        return "student/lab-terminal";
    }

    @PostMapping("/{id}/complete")
    @ResponseBody
    public String completeLab(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null)
            return "Error: Not logged in";

        Lab lab = labRepository.findById(id).orElse(null);
        if (lab == null)
            return "Error: Lab not found";

        com.vajraraksha.model.User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (user != null) {
            // Check if already completed
            if (user.getCompletedLabs() == null) {
                user.setCompletedLabs(new ArrayList<>());
            }
            if (!user.getCompletedLabs().contains(id)) {
                user.getCompletedLabs().add(id);
                userRepository.save(user); // Save completion status
                gamificationService.awardPoints(user.getUsername(), lab.getPoints() > 0 ? lab.getPoints() : 50);
                return "Success: Points awarded";
            } else {
                return "Info: Already completed";
            }
        }
        return "Error: User not found";
    }
}
