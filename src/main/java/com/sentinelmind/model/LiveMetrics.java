package com.sentinelmind.model;

public class LiveMetrics {

    private int patientCount;
    private int staffOnDuty;
    private int avgResponseTime;

    // ✅ Default constructor (required)
    public LiveMetrics() {}

    // ✅ Constructor YOU ARE CALLING
    public LiveMetrics(int patientCount, int staffOnDuty, int avgResponseTime) {
        this.patientCount = patientCount;
        this.staffOnDuty = staffOnDuty;
        this.avgResponseTime = avgResponseTime;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public int getStaffOnDuty() {
        return staffOnDuty;
    }

    public int getAvgResponseTime() {
        return avgResponseTime;
    }
}
