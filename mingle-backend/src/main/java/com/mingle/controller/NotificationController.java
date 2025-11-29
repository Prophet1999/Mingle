package com.mingle.controller;

import com.mingle.model.Notification;
import com.mingle.security.CurrentUser;
import com.mingle.security.UserPrincipal;
import com.mingle.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Notification> getNotifications(@CurrentUser UserPrincipal userPrincipal) {
        return notificationService.getUserNotifications(userPrincipal.getId());
    }

    @PostMapping("/{id}/read")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Marked as read");
    }
}
