package com.sentinelmind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_records")
public class WorkflowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

    private LocalDateTime timestamp;

    private int patientCount;

    private int avgResponseTime; // in minutes

    private int staffOnDuty;

    public WorkflowRecord() {}

    public WorkflowRecord(Department department, LocalDateTime timestamp,
                          int patientCount, int avgResponseTime, int staffOnDuty) {
        this.department = department;
        this.timestamp = timestamp;
        this.patientCount = patientCount;
        this.avgResponseTime = avgResponseTime;
        this.staffOnDuty = staffOnDuty;
    }

    public Long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public int getAvgResponseTime() {
        return avgResponseTime;
    }

    public int getStaffOnDuty() {
        return staffOnDuty;
    }
}
