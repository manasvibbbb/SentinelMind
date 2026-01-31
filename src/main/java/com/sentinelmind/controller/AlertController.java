package com.sentinelmind.controller;

import com.sentinelmind.model.Alert;
import com.sentinelmind.repository.AlertRepository;
import com.sentinelmind.service.AlertStreamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertRepository alertRepository;
    private final AlertStreamService alertStreamService;

    public AlertController(AlertRepository alertRepository,
                           AlertStreamService alertStreamService) {
        this.alertRepository = alertRepository;
        this.alertStreamService = alertStreamService;
    }

    // JSON alert history (used by UI & integrations)
    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    // Real-time alert stream (SSE)
    @GetMapping("/stream")
    public SseEmitter streamAlerts() {
        return alertStreamService.subscribe();
    }
}
