package com.sentinelmind.controller;

import com.sentinelmind.model.*;
import com.sentinelmind.repository.*;
import com.sentinelmind.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminMetricsController {

    private final DepartmentRepository departmentRepository;
    private final BaselineProfileRepository baselineRepository;
    private final AlertRepository alertRepository;
    private final RiskReasoningService reasoningService;
    private final AlertWebSocketService socketService;

    public AdminMetricsController(
            DepartmentRepository departmentRepository,
            BaselineProfileRepository baselineRepository,
            AlertRepository alertRepository,
            RiskReasoningService reasoningService,
            AlertWebSocketService socketService
    ) {
        this.departmentRepository = departmentRepository;
        this.baselineRepository = baselineRepository;
        this.alertRepository = alertRepository;
        this.reasoningService = reasoningService;
        this.socketService = socketService;
    }

    /* =========================
       ADMIN METRICS PAGE
       ========================= */
    @GetMapping("/admin/metrics")
    public String adminMetricsPage(Model model) {
        model.addAttribute("activePage", "admin");
        return "admin-metrics";
    }

    /* =========================
       METRICS SUBMISSION
       ========================= */
    @PostMapping("/admin/metrics")
    public String submitMetrics(
            @RequestParam String department,
            @RequestParam int patientCount,
            @RequestParam int staffOnDuty,
            @RequestParam int avgResponseTime
    ) {

        Department dept = departmentRepository
                .findByName(department)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        BaselineProfile baseline = baselineRepository
                .findByDepartment(dept)
                .orElseThrow(() -> new RuntimeException("Baseline not found"));

        LiveMetrics live = new LiveMetrics(
                patientCount,
                staffOnDuty,
                avgResponseTime
        );

        String risk = reasoningService.evaluateRisk(live, baseline);
        String explanation = reasoningService.buildExplanation(live, baseline);

        if (!"LOW".equals(risk)) {

            Alert alert = new Alert(
                    dept,
                    risk,
                    explanation,
                    "Increase staffing and activate surge protocol"
            );

            alertRepository.save(alert);
            socketService.sendAlert(alert);
        }

        return "redirect:/dashboard";
    }
}
