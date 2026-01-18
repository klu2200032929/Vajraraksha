package com.vajraraksha.controller;

import com.vajraraksha.model.Course;
import com.vajraraksha.model.User;
import com.vajraraksha.repository.CourseRepository;
import com.vajraraksha.repository.UserRepository;
import com.vajraraksha.service.CertificateService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;

@Controller
@RequestMapping("/certificate")
@SuppressWarnings("null")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/download/{courseId}")
    public void downloadCertificate(@PathVariable String courseId,
            @AuthenticationPrincipal UserDetails userDetails,
            HttpServletResponse response) throws IOException {

        if (userDetails == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in.");
            return;
        }

        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (user == null || course == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User or Course not found.");
            return;
        }

        // Verify Completion
        boolean isCompleted = isCourseFullyCompleted(user, course);
        if (!isCompleted) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You have not completed this course yet.");
            return;
        }

        // Generate PDF
        response.setContentType("application/pdf");
        String filename = "Certificate_" + course.getTitle().replaceAll("\\s+", "_") + ".pdf";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        certificateService.generateCertificate(user.getUsername(), course.getTitle(), response.getOutputStream());
    }

    private boolean isCourseFullyCompleted(User user, Course course) {
        if (course.getLessons() == null || course.getLessons().isEmpty())
            return false;
        if (user.getCompletedLessons() == null)
            return false;

        for (int i = 0; i < course.getLessons().size(); i++) {
            String lessonKey = course.getId() + "_" + i;
            if (!user.getCompletedLessons().contains(lessonKey)) {
                return false;
            }
        }
        return true;
    }
}
