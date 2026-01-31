package com.sentinelmind.controller;

import com.sentinelmind.model.Alert;
import com.sentinelmind.repository.AlertRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecommendationController {

    private final AlertRepository alertRepository;

    public RecommendationController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @GetMapping("/recommendations")
    public String recommendations(Model model) {

        List<Alert> alerts =
                alertRepository.findAll()
                        .stream()
                        .filter(a -> a.getRecommendation() != null && !a.getRecommendation().isEmpty())
                        .toList();

        model.addAttribute("alerts", alerts);
        return "recommendations";
    }
}
