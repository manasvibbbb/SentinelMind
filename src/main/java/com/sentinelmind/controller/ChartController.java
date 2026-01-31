package com.sentinelmind.controller;

import com.sentinelmind.model.Department;
import com.sentinelmind.model.WorkflowRecord;
import com.sentinelmind.repository.DepartmentRepository;
import com.sentinelmind.repository.WorkflowRecordRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChartController {

    private final DepartmentRepository departmentRepository;
    private final WorkflowRecordRepository workflowRecordRepository;

    public ChartController(DepartmentRepository departmentRepository,
                           WorkflowRecordRepository workflowRecordRepository) {
        this.departmentRepository = departmentRepository;
        this.workflowRecordRepository = workflowRecordRepository;
    }

    @GetMapping("/charts")
    public String charts(
            @RequestParam(defaultValue = "ER") String department,
            Model model) {

        Department dept = departmentRepository.findByName(department)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        List<WorkflowRecord> records =
                workflowRecordRepository.findByDepartmentOrderByTimestampAsc(dept);

        model.addAttribute("records", records);
        model.addAttribute("department", department);
        model.addAttribute("activePage", "charts");

        return "charts";
    }
}
