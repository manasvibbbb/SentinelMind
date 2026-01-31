package com.sentinelmind.controller;

import com.sentinelmind.model.BaselineProfile;
import com.sentinelmind.repository.BaselineProfileRepository;
import com.sentinelmind.repository.DepartmentRepository;
import com.sentinelmind.service.RiskTrendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DepartmentRepository departmentRepository;
    private final BaselineProfileRepository baselineRepository;
    private final RiskTrendService riskTrendService;

    public DashboardController(DepartmentRepository departmentRepository,
                               BaselineProfileRepository baselineRepository,
                               RiskTrendService riskTrendService) {
        this.departmentRepository = departmentRepository;
        this.baselineRepository = baselineRepository;
        this.riskTrendService = riskTrendService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        String erTrend = departmentRepository.findByName("ER")
                .map(dept -> {
                    BaselineProfile baseline =
                            baselineRepository.findByDepartment(dept).orElse(null);
                    return riskTrendService.calculateTrend(dept, baseline);
                })
                .orElse("UNKNOWN");

        model.addAttribute("erTrend", erTrend);
        return "dashboard";
    }
}
