package com.vajraraksha.repository;

import com.vajraraksha.model.Lab;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LabRepository extends MongoRepository<Lab, String> {
}
