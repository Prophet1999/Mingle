package com.mingle.controller;

import com.mingle.model.User;
import com.mingle.security.CurrentUser;
import com.mingle.security.UserPrincipal;
import com.mingle.service.FileStorageService;
import com.mingle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getCurrentUser(userPrincipal);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User updateProfile(@CurrentUser UserPrincipal userPrincipal, @RequestBody User userDetails) {
        return userService.updateUserProfile(userPrincipal.getId(), userDetails);
    }

    @PostMapping("/me/photo")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> uploadPhoto(@CurrentUser UserPrincipal userPrincipal,
            @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        User updatedUser = userService.updateUserPhoto(userPrincipal.getId(), fileDownloadUri);

        return ResponseEntity.ok(updatedUser);
    }
}
