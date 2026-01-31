package com.sentinelmind.service;

import com.sentinelmind.model.BaselineProfile;
import com.sentinelmind.model.Department;
import com.sentinelmind.model.WorkflowRecord;
import com.sentinelmind.repository.WorkflowRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskTrendService {

    private final WorkflowRecordRepository workflowRepo;

    public RiskTrendService(WorkflowRecordRepository workflowRepo) {
        this.workflowRepo = workflowRepo;
    }

    public String calculateTrend(Department department, BaselineProfile baseline) {

        if (baseline == null) return "INSUFFICIENT DATA";

        List<WorkflowRecord> records =
                workflowRepo.findTop5ByDepartmentOrderByTimestampDesc(department);

        if (records.size() < 3) return "INSUFFICIENT DATA";

        int oldest = calculateRisk(records.get(records.size() - 1), baseline);
        int latest = calculateRisk(records.get(0), baseline);

        if (latest > oldest) return "INCREASING";
        if (latest < oldest) return "IMPROVING";
        return "STABLE";
    }

    private int calculateRisk(WorkflowRecord r, BaselineProfile b) {
        int score = 0;

        if (r.getPatientCount() > b.getAvgPatients() * 1.5) score += 20;
        if (r.getAvgResponseTime() > b.getAvgResponseTime() * 2) score += 40;
        if (r.getStaffOnDuty() < b.getAvgStaff()) score += 40;

        return score;
    }
}
