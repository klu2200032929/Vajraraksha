package com.vajraraksha.repository;

import com.vajraraksha.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuizRepository extends MongoRepository<Quiz, String> {
    List<Quiz> findByLessonId(String lessonId);
}
