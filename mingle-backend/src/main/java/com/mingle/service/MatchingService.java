package com.mingle.service;

import com.mingle.model.User;
import com.mingle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingService {

    @Autowired
    private UserRepository userRepository;

    public void updateUserLocation(Long userId, Double lat, Double lon) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLatitude(lat);
        user.setLongitude(lon);
        userRepository.save(user);
    }

    public List<User> findMatches(Long userId, Double distanceKm) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (currentUser.getLatitude() == null || currentUser.getLongitude() == null) {
            throw new RuntimeException("User location not set");
        }

        return userRepository.findUsersNear(userId, currentUser.getLatitude(), currentUser.getLongitude(), distanceKm);
    }
}
