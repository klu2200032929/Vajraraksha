package com.vajraraksha.controller;

import com.vajraraksha.model.Course;
import com.vajraraksha.model.Lesson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @GetMapping("/demo")
    public String demoCourse(Model model) {
        Course course = new Course();
        course.setTitle("Cybersecurity Fundamentals");
        course.setDescription("The essential guide to network security and defense.");

        List<Lesson> lessons = new ArrayList<>();
        Lesson l1 = new Lesson();
        l1.setTitle("1. The CIA Triad");
        l1.setContent("Confidentiality, Integrity, and Availability are the three pillars of security...");
        l1.setVideoUrl("https://www.youtube.com/embed/ub-7uF02L2c?si=X0"); // Placeholder ID
        lessons.add(l1);

        course.setLessons(lessons);

        model.addAttribute("course", course);
        return "student/course-viewer";
    }
}
