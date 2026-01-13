package com.vajraraksha.controller;

import com.vajraraksha.model.Lab;
import com.vajraraksha.repository.LabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lab")
public class LabController {

    @Autowired
    private LabRepository labRepository;

    @GetMapping("/{id}")
    public String viewLab(@PathVariable String id, Model model) {
        // Fetch from DB, fallback to default if not found (prevents null errors in
        // demo)
        Lab lab = labRepository.findById(id).orElse(new Lab());

        if (lab.getId() == null) {
            lab.setId(id);
            lab.setTitle("Operation: SQL Storm (Demo)");
            lab.setDescription("Target: 192.168.1.50\nObjective: Find the admin password.");
            lab.setDifficulty("Intermediate");
            lab.setPointsAwarded(500);
        }

        model.addAttribute("lab", lab);
        return "student/lab-terminal";
    }

    @GetMapping("/demo")
    public String demoLab(Model model) {
        Lab lab = new Lab();
        lab.setId("demo-001");
        lab.setTitle("Sandbox: Network Recon");
        lab.setDescription("Target: 10.0.0.5\nMission: Perform a port scan to identify open services.");
        lab.setDifficulty("Beginner");
        lab.setPointsAwarded(100);

        model.addAttribute("lab", lab);
        return "student/lab-terminal";
    }
}
