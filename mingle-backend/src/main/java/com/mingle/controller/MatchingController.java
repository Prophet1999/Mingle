package com.mingle.controller;

import com.mingle.model.User;
import com.mingle.payload.LocationUpdateRequest;
import com.mingle.security.CurrentUser;
import com.mingle.security.UserPrincipal;
import com.mingle.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matching")
public class MatchingController {

    @Autowired
    private MatchingService matchingService;

    @PostMapping("/location")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateLocation(@CurrentUser UserPrincipal userPrincipal,
            @RequestBody LocationUpdateRequest locationRequest) {
        matchingService.updateUserLocation(userPrincipal.getId(), locationRequest.getLatitude(),
                locationRequest.getLongitude());
        return ResponseEntity.ok("Location updated");
    }

    @GetMapping("/candidates")
    @PreAuthorize("hasRole('USER')")
    public List<User> getCandidates(@CurrentUser UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "50") Double distance) {
        return matchingService.findCandidates(userPrincipal.getId(), distance);
    }

    @PostMapping("/like/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> likeUser(@CurrentUser UserPrincipal userPrincipal,
            @PathVariable Long userId) {
        boolean isMatch = matchingService.likeUser(userPrincipal.getId(), userId);
        return ResponseEntity.ok(Map.of("match", isMatch));
    }

    @PostMapping("/dislike/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> dislikeUser(@CurrentUser UserPrincipal userPrincipal,
            @PathVariable Long userId) {
        matchingService.dislikeUser(userPrincipal.getId(), userId);
        return ResponseEntity.ok("User disliked");
    }

    @GetMapping("/matches")
    @PreAuthorize("hasRole('USER')")
    public List<User> getMatches(@CurrentUser UserPrincipal userPrincipal) {
        return matchingService.findMatches(userPrincipal.getId());
    }
}
