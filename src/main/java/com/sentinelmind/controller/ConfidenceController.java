package com.sentinelmind.controller;

import com.sentinelmind.model.Department;
import com.sentinelmind.repository.DepartmentRepository;
import com.sentinelmind.service.BaselineConfidenceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ConfidenceController {

    private final DepartmentRepository departmentRepository;
    private final BaselineConfidenceService confidenceService;

    public ConfidenceController(DepartmentRepository departmentRepository,
                                BaselineConfidenceService confidenceService) {
        this.departmentRepository = departmentRepository;
        this.confidenceService = confidenceService;
    }

    @GetMapping("/confidence")
    public String confidencePage(Model model) {

        List<ConfidenceView> confidenceList = new ArrayList<>();

        for (Department dept : departmentRepository.findAll()) {
            var result = confidenceService.calculateConfidence(dept);

            confidenceList.add(new ConfidenceView(
                    dept.getName(),
                    result.level(),
                    result.recordsUsed(),
                    result.explanation()
            ));
        }

        model.addAttribute("confidenceList", confidenceList);
        return "confidence";
    }

    /* View DTO */
    record ConfidenceView(
            String department,
            String level,
            long records,
            String explanation
    ) {}
}
