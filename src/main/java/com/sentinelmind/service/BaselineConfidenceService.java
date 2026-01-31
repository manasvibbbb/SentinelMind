package com.sentinelmind.service;

import com.sentinelmind.model.Department;
import com.sentinelmind.repository.WorkflowRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class BaselineConfidenceService {

    private final WorkflowRecordRepository workflowRecordRepository;

    public BaselineConfidenceService(WorkflowRecordRepository workflowRecordRepository) {
        this.workflowRecordRepository = workflowRecordRepository;
    }

    public ConfidenceResult calculateConfidence(Department department) {

        long recordCount =
                workflowRecordRepository.countByDepartment(department);

        String level;
        String explanation;

        if (recordCount < 10) {
            level = "LOW";
            explanation = "Insufficient historical data. Baseline may be unstable.";
        } else if (recordCount <= 30) {
            level = "MEDIUM";
            explanation = "Moderate amount of data. Baseline is reasonably reliable.";
        } else {
            level = "HIGH";
            explanation = "Large historical dataset. Baseline is highly reliable.";
        }

        return new ConfidenceResult(level, recordCount, explanation);
    }

    /* Simple DTO */
    public record ConfidenceResult(
            String level,
            long recordsUsed,
            String explanation
    ) {}
}
