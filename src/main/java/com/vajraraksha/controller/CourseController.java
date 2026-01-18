package com.vajraraksha.controller;

import com.vajraraksha.model.Course;
import com.vajraraksha.service.GamificationService;
import com.vajraraksha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;

@Controller
@RequestMapping("/courses")
@SuppressWarnings("null")
public class CourseController {

    @Autowired
    private com.vajraraksha.repository.CourseRepository courseRepository;
    @Autowired
    private com.vajraraksha.repository.QuizRepository quizRepository;
    @Autowired
    private GamificationService gamificationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private com.vajraraksha.service.QuizGeneratorService quizGeneratorService;

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable String id,
            @org.springframework.web.bind.annotation.RequestParam(required = false) Integer lessonIndex,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        Course course = courseRepository.findById(id).orElse(new Course());
        boolean isCompleted = false;

        // Determine Lesson Index
        int finalLessonIndex = 0;

        if (userDetails != null) {
            com.vajraraksha.model.User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElse(new com.vajraraksha.model.User());

            // Resume Logic: If no lessonIndex provided, find the last completed one
            if (lessonIndex == null) {
                int maxCompleted = -1;
                if (user.getCompletedLessons() != null) {
                    for (String completion : user.getCompletedLessons()) {
                        if (completion.startsWith(id + "_")) {
                            try {
                                int idx = Integer.parseInt(completion.split("_")[1]);
                                if (idx > maxCompleted)
                                    maxCompleted = idx;
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                // Resume at next lesson
                finalLessonIndex = maxCompleted + 1;
            } else {
                finalLessonIndex = lessonIndex;
            }

            // Safe null check for completion status of CURRENT lesson
            if (user.getCompletedLessons() != null
                    && user.getCompletedLessons().contains(id + "_" + finalLessonIndex)) {
                isCompleted = true;
            }
        } else {
            if (lessonIndex != null)
                finalLessonIndex = lessonIndex;
        }

        if (course.getLessons() != null && !course.getLessons().isEmpty()) {
            // Bounds check
            if (finalLessonIndex < 0 || finalLessonIndex >= course.getLessons().size()) {
                // If calculated index is valid (e.g. course complete), show last lesson or
                // first?
                // Let's safe guard to max index
                if (finalLessonIndex >= course.getLessons().size()) {
                    finalLessonIndex = course.getLessons().size() - 1;
                    isCompleted = true; // They finished everything
                } else
                    finalLessonIndex = 0;
            }

            com.vajraraksha.model.Lesson currentLesson = course.getLessons().get(finalLessonIndex);
            model.addAttribute("currentLesson", currentLesson);
            model.addAttribute("currentLessonIndex", finalLessonIndex);
            model.addAttribute("totalLessons", course.getLessons().size());

            // Check for existing quiz or generate dynamic one
            java.util.List<com.vajraraksha.model.Quiz> quizzes = quizRepository.findByLessonId(id);
            if (quizzes.isEmpty()) {
                // Generate dynamic quiz using the new Engine
                com.vajraraksha.model.Quiz dynamicQuiz = quizGeneratorService.generateQuiz(currentLesson.getTitle(),
                        course.getTitle());
                dynamicQuiz.setLessonId(id); // Associate with course for now
                quizzes.add(dynamicQuiz);
                // Note: Not saving dynamic quiz to DB to keep it random every time, or save if
                // persistence needed.
                // For this requirement "random generation", dynamic is better.
            }
            model.addAttribute("quizzes", quizzes);
        }

        model.addAttribute("course", course);
        model.addAttribute("isLessonCompleted", isCompleted);
        return "student/course-viewer";
    }

    @PostMapping("/{id}/quiz/complete")
    @ResponseBody
    public String completeQuiz(@PathVariable String id,
            @org.springframework.web.bind.annotation.RequestBody Map<String, Object> payload,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null)
            return "Error: Not logged in";

        Integer score = (Integer) payload.get("score");
        Integer lessonIdx = (Integer) payload.get("lessonIndex"); // New param
        String lessonKey = id + "_" + (lessonIdx != null ? lessonIdx : 0);

        com.vajraraksha.model.User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (user != null) {
            if (user.getCompletedLessons() == null)
                user.setCompletedLessons(new java.util.ArrayList<>());

            if (user.getCompletedLessons().contains(lessonKey)) {
                return "Info: Already completed (No extra points)";
            }

            // First time completion
            if (score != null && score > 0) {
                user.getCompletedLessons().add(lessonKey);
                userRepository.save(user);
                gamificationService.awardPoints(userDetails.getUsername(), score * 10);
                return "Success: Points awarded";
            }
        }
        return "Info: No points to award";
    }
}
