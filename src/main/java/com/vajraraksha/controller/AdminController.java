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
@SuppressWarnings("null")
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

        // --- REAL CHART DATA ---

        // 1. User Growth (Last 6 Months)
        java.time.YearMonth currentMonth = java.time.YearMonth.now();
        String[] chartLabels = new String[6];
        int[] chartDataUsers = new int[6];

        for (int i = 5; i >= 0; i--) {
            java.time.YearMonth targetMonth = currentMonth.minusMonths(i);
            chartLabels[5 - i] = targetMonth.getMonth().name().substring(0, 3);

            // Count users created in or before this month (Cumulative Growth)
            long count = users.stream()
                    .filter(u -> u.getCreatedAt() != null
                            && !java.time.YearMonth.from(u.getCreatedAt()).isAfter(targetMonth))
                    .count();
            chartDataUsers[5 - i] = (int) count;
        }
        model.addAttribute("chartLabels", chartLabels);
        model.addAttribute("chartDataUsers", chartDataUsers);

        // 2. Lab Activity (Top 5 Active Labs)
        List<com.vajraraksha.model.Lab> allLabs = labRepository.findAll();
        java.util.Map<String, Integer> labCounts = new java.util.HashMap<>();
        java.util.Map<String, String> labNames = new java.util.HashMap<>();

        // Initialize maps
        for (com.vajraraksha.model.Lab lab : allLabs) {
            labCounts.put(lab.getId(), 0);
            labNames.put(lab.getId(), lab.getTitle());
        }

        // Count completions
        for (com.vajraraksha.model.User u : users) {
            if (u.getCompletedLabs() != null) {
                for (String labId : u.getCompletedLabs()) {
                    labCounts.put(labId, labCounts.getOrDefault(labId, 0) + 1);
                }
            }
        }

        // Sort by frequency
        List<java.util.Map.Entry<String, Integer>> sortedLabs = new java.util.ArrayList<>(labCounts.entrySet());
        sortedLabs.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Descending

        // Take top 5
        int limit = Math.min(5, sortedLabs.size());
        String[] labActivityLabels = new String[limit];
        int[] labActivityData = new int[limit];

        for (int i = 0; i < limit; i++) {
            String labId = sortedLabs.get(i).getKey();
            // Truncate name if too long
            String name = labNames.getOrDefault(labId, "Unknown Lab");
            if (name.length() > 15)
                name = name.substring(0, 15) + "...";

            labActivityLabels[i] = name;
            labActivityData[i] = sortedLabs.get(i).getValue();
        }

        model.addAttribute("labActivityLabels", labActivityLabels);
        model.addAttribute("labActivityData", labActivityData);

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
