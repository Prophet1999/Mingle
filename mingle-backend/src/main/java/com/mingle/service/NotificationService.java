package com.mingle.service;

import com.mingle.model.Notification;
import com.mingle.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(Long recipientId, String type, String message, Long relatedUserId) {
        Notification notification = Notification.builder()
                .recipientId(recipientId)
                .type(type)
                .message(message)
                .relatedUserId(relatedUserId)
                .isRead(false)
                .timestamp(new Date())
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByRecipientIdOrderByTimestampDesc(userId);
    }

    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }
}
