package com.mingle.service;

import com.mingle.model.User;
import com.mingle.repository.UserRepository;
import com.mingle.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUserProfile(Long userId, User userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBio(userDetails.getBio());
        user.setGender(userDetails.getGender());
        user.setInterestedIn(userDetails.getInterestedIn());
        // Add more fields as needed

        return userRepository.save(user);
    }

    public User updateUserPhoto(Long userId, String photoUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setImageUrl(photoUrl);
        return userRepository.save(user);
    }
}
