package com.sentinelmind.controller;

import com.sentinelmind.model.BaselineProfile;
import com.sentinelmind.model.Department;
import com.sentinelmind.repository.BaselineProfileRepository;
import com.sentinelmind.repository.DepartmentRepository;
import com.sentinelmind.service.RiskTrendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RiskTrendController {

    private final DepartmentRepository departmentRepo;
    private final BaselineProfileRepository baselineRepo;
    private final RiskTrendService riskTrendService;

    public RiskTrendController(DepartmentRepository departmentRepo,
                               BaselineProfileRepository baselineRepo,
                               RiskTrendService riskTrendService) {
        this.departmentRepo = departmentRepo;
        this.baselineRepo = baselineRepo;
        this.riskTrendService = riskTrendService;
    }

    @GetMapping("/risk-trends")
    public String riskTrends(Model model) {

        List<DepartmentTrend> trends = new ArrayList<>();

        for (Department dept : departmentRepo.findAll()) {
            BaselineProfile baseline =
                    baselineRepo.findByDepartment(dept).orElse(null);

            String trend = riskTrendService.calculateTrend(dept, baseline);
            trends.add(new DepartmentTrend(dept.getName(), trend));
        }

        model.addAttribute("trends", trends);
        return "risk-trends";
    }

    // Simple DTO
    static class DepartmentTrend {
        public String department;
        public String trend;

        public DepartmentTrend(String department, String trend) {
            this.department = department;
            this.trend = trend;
        }
    }
}
