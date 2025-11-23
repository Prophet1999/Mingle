package com.mingle.service;

import com.mingle.model.Like;
import com.mingle.model.User;
import com.mingle.repository.LikeRepository;
import com.mingle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository likeRepository;

    public void updateUserLocation(Long userId, Double lat, Double lon) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLatitude(lat);
        user.setLongitude(lon);
        userRepository.save(user);
    }

    public List<User> findCandidates(Long userId, Double distanceKm) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (currentUser.getLatitude() == null || currentUser.getLongitude() == null) {
            // Return empty list if location not set, or maybe throw exception
            return new ArrayList<>();
        }

        // Get all users near location
        List<User> nearbyUsers = userRepository.findUsersNear(userId, currentUser.getLatitude(),
                currentUser.getLongitude(), distanceKm);

        // Filter out users already liked
        // This is a naive implementation. For production, this should be done in the
        // query.
        List<Like> likes = likeRepository.findAll(); // Optimization needed here for real app
        Set<Long> likedUserIds = likes.stream()
                .filter(like -> like.getLikerId().equals(userId))
                .map(Like::getLikeeId)
                .collect(Collectors.toSet());

        return nearbyUsers.stream()
                .filter(user -> !likedUserIds.contains(user.getId()))
                .collect(Collectors.toList());
    }

    public boolean likeUser(Long likerId, Long likeeId) {
        if (likeRepository.existsByLikerIdAndLikeeId(likerId, likeeId)) {
            return false; // Already liked
        }

        Like like = Like.builder()
                .likerId(likerId)
                .likeeId(likeeId)
                .build();
        likeRepository.save(like);

        // Check for match
        return likeRepository.existsByLikerIdAndLikeeId(likeeId, likerId);
    }

    public List<User> findMatches(Long userId) {
        // Find all users who liked the current user
        List<Like> receivedLikes = likeRepository.findAll().stream()
                .filter(like -> like.getLikeeId().equals(userId))
                .collect(Collectors.toList());

        List<User> matches = new ArrayList<>();
        for (Like receivedLike : receivedLikes) {
            // Check if current user also liked them
            if (likeRepository.existsByLikerIdAndLikeeId(userId, receivedLike.getLikerId())) {
                userRepository.findById(receivedLike.getLikerId()).ifPresent(matches::add);
            }
        }
        return matches;
    }
}
