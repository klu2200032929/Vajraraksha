package com.vajraraksha.service;

import com.vajraraksha.model.User;
import com.vajraraksha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GamificationService {

    @Autowired
    private UserRepository userRepository;

    public void awardPoints(String username, int points) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            user.setPoints(user.getPoints() + points);
            checkBadges(user);
            userRepository.save(user);
        }
    }

    private void checkBadges(User user) {
        if (user.getBadges() == null) {
            user.setBadges(new ArrayList<>());
        }

        int points = user.getPoints();
        if (points >= 100 && !user.getBadges().contains("Cyber Novice")) {
            user.getBadges().add("Cyber Novice");
        }
        if (points >= 500 && !user.getBadges().contains("Script Kiddie Hunter")) {
            user.getBadges().add("Script Kiddie Hunter");
        }
        if (points >= 1000 && !user.getBadges().contains("White Hat Hacker")) {
            user.getBadges().add("White Hat Hacker");
        }
    }
}
