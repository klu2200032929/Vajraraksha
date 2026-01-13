package com.vajraraksha.repository;

import com.vajraraksha.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    java.util.List<User> findTop5ByRoleOrderByPointsDesc(com.vajraraksha.model.Role role);
}
