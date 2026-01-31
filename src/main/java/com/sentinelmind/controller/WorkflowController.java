package com.sentinelmind.controller;

import com.sentinelmind.model.Alert;
import com.sentinelmind.service.WorkflowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    /**
     * Example:
     * POST /api/workflow/ingest?department=ER&patients=48&response=15&staff=6
     */
    @PostMapping("/ingest")
    public String ingestWorkflow(@RequestParam String department,
                                 @RequestParam int patients,
                                 @RequestParam int response,
                                 @RequestParam int staff) {

        Alert alert = workflowService.ingestWorkflowData(
                department,
                patients,
                response,
                staff
        );

        if (alert == null) {
            return "Workflow ingested successfully. No anomalies detected.";
        }

        return "ALERT [" + alert.getRiskLevel() + "]: " + alert.getMessage();
    }
}
