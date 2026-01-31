package com.sentinelmind.service;

import com.sentinelmind.model.BaselineProfile;
import com.sentinelmind.model.LiveMetrics;
import org.springframework.stereotype.Service;

@Service
public class RiskReasoningService {

    public String evaluateRisk(LiveMetrics live, BaselineProfile baseline) {

        if (baseline == null) return "LOW";

        if (live.getAvgResponseTime() > baseline.getAvgResponseTime() * 2
                && live.getStaffOnDuty() < baseline.getAvgStaff()) {
            return "HIGH";
        }

        if (live.getAvgResponseTime() > baseline.getAvgResponseTime()) {
            return "MEDIUM";
        }

        return "LOW";
    }

    public String buildExplanation(LiveMetrics live, BaselineProfile baseline) {
        return "Response time increased due to high patient inflow and reduced staffing.";
    }
}
