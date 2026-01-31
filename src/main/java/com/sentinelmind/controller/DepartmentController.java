package com.sentinelmind.controller;

import com.sentinelmind.model.Alert;
import com.sentinelmind.model.BaselineProfile;
import com.sentinelmind.model.Department;
import com.sentinelmind.repository.AlertRepository;
import com.sentinelmind.repository.BaselineProfileRepository;
import com.sentinelmind.repository.DepartmentRepository;
import com.sentinelmind.service.RiskTrendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
    private final BaselineProfileRepository baselineRepository;
    private final AlertRepository alertRepository;
    private final RiskTrendService riskTrendService;

    public DepartmentController(
            DepartmentRepository departmentRepository,
            BaselineProfileRepository baselineRepository,
            AlertRepository alertRepository,
            RiskTrendService riskTrendService) {

        this.departmentRepository = departmentRepository;
        this.baselineRepository = baselineRepository;
        this.alertRepository = alertRepository;
        this.riskTrendService = riskTrendService;
    }

    @GetMapping("/{name}")
    public String viewDepartment(@PathVariable String name, Model model) {

        Department department = departmentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        BaselineProfile baseline =
                baselineRepository.findByDepartment(department).orElse(null);

        List<Alert> alerts =
                alertRepository.findTop5ByDepartmentOrderByCreatedAtDesc(department);

        String trend = riskTrendService.calculateTrend(department, baseline);

        model.addAttribute("department", department);
        model.addAttribute("baseline", baseline);
        model.addAttribute("alerts", alerts);
        model.addAttribute("trend", trend);

        return "department";
    }
}
