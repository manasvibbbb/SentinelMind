package com.sentinelmind.service;

import com.sentinelmind.model.Alert;
import com.sentinelmind.model.BaselineProfile;
import com.sentinelmind.model.Department;
import com.sentinelmind.model.WorkflowRecord;
import com.sentinelmind.repository.AlertRepository;
import com.sentinelmind.repository.BaselineProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnomalyDetectionService {

    private final BaselineProfileRepository baselineRepo;
    private final AlertRepository alertRepo;
    private final AlertNotificationService notificationService;

    public AnomalyDetectionService(
            BaselineProfileRepository baselineRepo,
            AlertRepository alertRepo,
            AlertNotificationService notificationService) {
        this.baselineRepo = baselineRepo;
        this.alertRepo = alertRepo;
        this.notificationService = notificationService;
    }

    public Alert analyzeWorkflow(WorkflowRecord record) {

        Department dept = record.getDepartment();
        Optional<BaselineProfile> baseOpt = baselineRepo.findByDepartment(dept);
        if (baseOpt.isEmpty()) return null;

        BaselineProfile base = baseOpt.get();

        String risk = "LOW";
        StringBuilder explanation = new StringBuilder();
        StringBuilder recommendation = new StringBuilder();

        if (record.getPatientCount() > base.getAvgPatients() * 1.5) {
            risk = "MEDIUM";
            explanation.append("Patient inflow above baseline. ");
            recommendation.append("Divert non-critical patients. ");
        }

        if (record.getAvgResponseTime() > base.getAvgResponseTime() * 2) {
            risk = "HIGH";
            explanation.append("Response time critically delayed. ");
            recommendation.append("Prioritize critical cases. ");
        }

        if (record.getStaffOnDuty() < base.getAvgStaff()) {
            risk = "HIGH";
            explanation.append("Staffing below baseline. ");
            recommendation.append("Increase staffing immediately. ");
        }

        if (explanation.length() == 0) return null;

        Alert alert = alertRepo.save(
                new Alert(dept, risk, explanation.toString(), recommendation.toString())
        );

        if ("HIGH".equals(risk)) {
            notificationService.notifyDoctors(alert);
        }

        return alert;
    }
}
