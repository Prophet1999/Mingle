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
        return matchingService.findMatches(userPrincipal.getId(), distance);
    }
}
