package com.sentinelmind.service;

import com.sentinelmind.model.BaselineProfile;
import com.sentinelmind.model.Department;
import com.sentinelmind.model.WorkflowRecord;
import com.sentinelmind.repository.BaselineProfileRepository;
import com.sentinelmind.repository.WorkflowRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BaselineService {

    private final WorkflowRecordRepository workflowRecordRepository;
    private final BaselineProfileRepository baselineProfileRepository;

    public BaselineService(WorkflowRecordRepository workflowRecordRepository,
                           BaselineProfileRepository baselineProfileRepository) {
        this.workflowRecordRepository = workflowRecordRepository;
        this.baselineProfileRepository = baselineProfileRepository;
    }

    /**
     * Recalculate baseline for a department
     * using recent workflow records (last 7 days)
     */
    public BaselineProfile calculateBaseline(Department department) {

        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        List<WorkflowRecord> records =
                workflowRecordRepository
                        .findByDepartmentAndTimestampAfter(department, sevenDaysAgo);

        if (records.isEmpty()) {
            return null; // Not enough data to learn baseline
        }

        double avgPatients = records.stream()
                .mapToInt(WorkflowRecord::getPatientCount)
                .average()
                .orElse(0);

        double avgResponseTime = records.stream()
                .mapToInt(WorkflowRecord::getAvgResponseTime)
                .average()
                .orElse(0);

        double avgStaff = records.stream()
                .mapToInt(WorkflowRecord::getStaffOnDuty)
                .average()
                .orElse(0);

        BaselineProfile baseline = baselineProfileRepository
                .findByDepartment(department)
                .orElse(new BaselineProfile());

        baseline.setDepartment(department);
        baseline.setAvgPatients(avgPatients);
        baseline.setAvgResponseTime(avgResponseTime);
        baseline.setAvgStaff(avgStaff);
        baseline.updateTimestamp();

        return baselineProfileRepository.save(baseline);
    }
}
