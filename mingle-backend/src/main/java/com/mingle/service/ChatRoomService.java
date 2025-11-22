package com.mingle.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    public Optional<String> getChatId(Long senderId, Long recipientId, boolean createNewRoomIfNotExists) {
        // Simple chat ID generation logic: smallerId_largerId
        Long minId = Math.min(senderId, recipientId);
        Long maxId = Math.max(senderId, recipientId);
        return Optional.of(minId + "_" + maxId);
    }
}
