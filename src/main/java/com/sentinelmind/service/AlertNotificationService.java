package com.sentinelmind.service;

import com.sentinelmind.model.Alert;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlertNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public AlertNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyDoctors(Alert alert) {
        messagingTemplate.convertAndSend("/topic/alerts", alert);
    }
}


