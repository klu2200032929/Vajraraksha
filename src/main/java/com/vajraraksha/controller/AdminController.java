package com.vajraraksha.controller;

import com.vajraraksha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private com.vajraraksha.repository.UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private com.vajraraksha.repository.LabRepository labRepository;

    @Autowired
    private com.vajraraksha.repository.CourseRepository courseRepository;

    @Autowired
    private com.vajraraksha.service.CourseGeneratorService courseGeneratorService;

    @org.springframework.web.bind.annotation.PostMapping("/courses/generate")
    public String generateCourse(@org.springframework.web.bind.annotation.RequestParam String title,
            @org.springframework.web.bind.annotation.RequestParam String description) {
        System.out.println("DEBUG: generateCourse called for " + title); // Debug log
        courseGeneratorService.generateCourse(title, description);
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@org.springframework.web.bind.annotation.PathVariable String id) {
        userRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Real Data
        List<com.vajraraksha.model.User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        // Dynamic Analytics
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("activeStudents", users.stream().filter(u -> "STUDENT".equals(u.getRole().name())).count());
        model.addAttribute("totalLabs", labRepository.count());
        model.addAttribute("totalCourses", courseRepository.count());

        // Real Data Calculation
        long totalCompletedLabs = users.stream()
                .filter(u -> u.getCompletedLabs() != null)
                .mapToInt(u -> u.getCompletedLabs().size())
                .sum();

        model.addAttribute("completedLabs", totalCompletedLabs);

        // Data arrays for Chart.js - Keep mock history for visual appeal, but use real
        // total for last point
        // Ideally, we would have a separate 'ActivityLog' collection to track dates,
        // but this is a good approximation for now.
        model.addAttribute("chartLabels", new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun" });
        model.addAttribute("chartDataUsers", new int[] { 5, 12, 19, 25, 30, (int) users.size() });
        model.addAttribute("chartDataLabs", new int[] { 2, 8, 15, 22, 35, (int) totalCompletedLabs });

        return "admin/dashboard";
    }

    // --- Course Management ---

    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "admin/course-list";
    }

    @GetMapping("/courses/new")
    public String showCourseForm(Model model) {
        model.addAttribute("course", new com.vajraraksha.model.Course());
        return "admin/course-form";
    }

    @org.springframework.web.bind.annotation.PostMapping("/courses")
    public String saveCourse(
            @org.springframework.web.bind.annotation.ModelAttribute com.vajraraksha.model.Course course) {
        if (course.getId() != null && course.getId().trim().isEmpty()) {
            course.setId(null);
        }
        courseRepository.save(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourse(@org.springframework.web.bind.annotation.PathVariable String id, Model model) {
        model.addAttribute("course", courseRepository.findById(id).orElse(null));
        return "admin/course-form";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@org.springframework.web.bind.annotation.PathVariable String id) {
        courseRepository.deleteById(id);
        // Cascading Delete: Remove labs associated with this course
        List<com.vajraraksha.model.Lab> associatedLabs = labRepository.findByCourseId(id);
        labRepository.deleteAll(associatedLabs);
        return "redirect:/admin/courses";
    }

    // --- Lab Management ---

    @org.springframework.web.bind.annotation.PostMapping("/labs/generate")
    public String generateLab(@org.springframework.web.bind.annotation.RequestParam String title,
            @org.springframework.web.bind.annotation.RequestParam String description,
            @org.springframework.web.bind.annotation.RequestParam(defaultValue = "Beginner") String difficulty,
            @org.springframework.web.bind.annotation.RequestParam(defaultValue = "50") int points) {
        System.out.println("DEBUG: generateLab called with Title: " + title + ", Difficulty: [" + difficulty + "]");
        courseGeneratorService.generateLabOnly(title, description, difficulty, points);
        return "redirect:/admin/labs";
    }

    @GetMapping("/labs")
    public String listLabs(Model model) {
        model.addAttribute("labs", labRepository.findAll());
        return "admin/lab-list";
    }

    @GetMapping("/labs/new")
    public String showLabForm(Model model) {
        model.addAttribute("lab", new com.vajraraksha.model.Lab());
        return "admin/lab-form";
    }

    @org.springframework.web.bind.annotation.PostMapping("/labs")
    public String saveLab(@org.springframework.web.bind.annotation.ModelAttribute com.vajraraksha.model.Lab lab) {
        if (lab.getId() != null && lab.getId().trim().isEmpty()) {
            lab.setId(null);
        }
        labRepository.save(lab);
        return "redirect:/admin/labs";
    }

    @GetMapping("/labs/edit/{id}")
    public String editLab(@org.springframework.web.bind.annotation.PathVariable String id, Model model) {
        model.addAttribute("lab", labRepository.findById(id).orElse(null));
        return "admin/lab-form";
    }

    @GetMapping("/labs/delete/{id}")
    public String deleteLab(@org.springframework.web.bind.annotation.PathVariable String id) {
        labRepository.deleteById(id);
        return "redirect:/admin/labs";
    }
}
