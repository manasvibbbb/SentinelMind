package com.sentinelmind.service;

import com.sentinelmind.model.Alert;
import com.sentinelmind.model.Department;
import com.sentinelmind.model.WorkflowRecord;
import com.sentinelmind.repository.DepartmentRepository;
import com.sentinelmind.repository.WorkflowRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WorkflowService {

    private final WorkflowRecordRepository workflowRecordRepository;
    private final DepartmentRepository departmentRepository;
    private final BaselineService baselineService;
    private final AnomalyDetectionService anomalyDetectionService;

    public WorkflowService(WorkflowRecordRepository workflowRecordRepository,
                           DepartmentRepository departmentRepository,
                           BaselineService baselineService,
                           AnomalyDetectionService anomalyDetectionService) {
        this.workflowRecordRepository = workflowRecordRepository;
        this.departmentRepository = departmentRepository;
        this.baselineService = baselineService;
        this.anomalyDetectionService = anomalyDetectionService;
    }

    /**
     * Ingest new workflow data
     */
    public Alert ingestWorkflowData(String departmentName,
                                    int patientCount,
                                    int avgResponseTime,
                                    int staffOnDuty) {

        Department department = departmentRepository
                .findByName(departmentName)
                .orElseGet(() ->
                        departmentRepository.save(new Department(departmentName))
                );

        WorkflowRecord record = new WorkflowRecord(
                department,
                LocalDateTime.now(),
                patientCount,
                avgResponseTime,
                staffOnDuty
        );

        workflowRecordRepository.save(record);

        // Update baseline after ingestion
        baselineService.calculateBaseline(department);

        // Run anomaly detection
        return anomalyDetectionService.analyzeWorkflow(record);
    }
}
