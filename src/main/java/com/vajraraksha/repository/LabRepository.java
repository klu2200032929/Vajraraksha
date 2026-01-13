package com.vajraraksha.repository;

import com.vajraraksha.model.Lab;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabRepository extends MongoRepository<Lab, String> {
    List<Lab> findByCourseId(String courseId);

    List<Lab> findByInstructorId(String instructorId);
}
