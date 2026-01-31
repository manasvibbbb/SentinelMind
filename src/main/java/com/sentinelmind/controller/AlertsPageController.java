package com.sentinelmind.controller;

import com.sentinelmind.repository.AlertRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlertsPageController {

    private final AlertRepository alertRepository;

    public AlertsPageController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @GetMapping("/alerts")
    public String alertsPage(Model model) {

        var alerts = alertRepository.findAll();

        long highRiskCount = alerts.stream()
                .filter(a -> "HIGH".equalsIgnoreCase(a.getRiskLevel()))
                .count();

        model.addAttribute("alerts", alerts);
        model.addAttribute("totalAlerts", alerts.size());
        model.addAttribute("highRiskAlerts", highRiskCount);
        model.addAttribute("activePage", "alerts");

        return "alerts";
    }
}
