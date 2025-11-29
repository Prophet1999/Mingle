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
    private com.mingle.repository.LikeRepository likeRepository;

    @Autowired
    private com.mingle.repository.DislikeRepository dislikeRepository;

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
            return new ArrayList<>();
        }

        // Get all users near location
        List<User> nearbyUsers = userRepository.findUsersNear(userId, currentUser.getLatitude(),
                currentUser.getLongitude(), distanceKm);

        // Filter out users already liked
        List<Like> likes = likeRepository.findAll();
        Set<Long> likedUserIds = likes.stream()
                .filter(like -> like.getLikerId().equals(userId))
                .map(Like::getLikeeId)
                .collect(Collectors.toSet());

        // Filter out users already disliked
        List<com.mingle.model.Dislike> dislikes = dislikeRepository.findAll();
        Set<Long> dislikedUserIds = dislikes.stream()
                .filter(dislike -> dislike.getDislikerId().equals(userId))
                .map(com.mingle.model.Dislike::getDislikeeId)
                .collect(Collectors.toSet());

        return nearbyUsers.stream()
                .filter(user -> !likedUserIds.contains(user.getId()))
                .filter(user -> !dislikedUserIds.contains(user.getId()))
                .filter(user -> matchesPreferences(currentUser, user))
                .collect(Collectors.toList());
    }

    private boolean matchesPreferences(User currentUser, User candidate) {
        // Gender check
        if (currentUser.getInterestedIn() != null && !currentUser.getInterestedIn().equalsIgnoreCase("BOTH")) {
            if (!currentUser.getInterestedIn().equalsIgnoreCase(candidate.getGender())) {
                return false;
            }
        }

        // Age check
        if (currentUser.getMinAgePreference() != null && currentUser.getMaxAgePreference() != null
                && candidate.getDob() != null) {
            int age = java.time.Period.between(candidate.getDob(), java.time.LocalDate.now()).getYears();
            if (age < currentUser.getMinAgePreference() || age > currentUser.getMaxAgePreference()) {
                return false;
            }
        }

        return true;
    }

    public void dislikeUser(Long dislikerId, Long dislikeeId) {
        if (dislikeRepository.existsByDislikerIdAndDislikeeId(dislikerId, dislikeeId)) {
            return;
        }
        com.mingle.model.Dislike dislike = com.mingle.model.Dislike.builder()
                .dislikerId(dislikerId)
                .dislikeeId(dislikeeId)
                .build();
        dislikeRepository.save(dislike);
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
