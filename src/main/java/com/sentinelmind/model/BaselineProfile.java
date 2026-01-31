package com.sentinelmind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "baseline_profiles")
public class BaselineProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "department_id", unique = true)
    private Department department;

    private double avgPatients;
    private double avgResponseTime;
    private double avgStaff;

    private LocalDateTime lastUpdated;

    public BaselineProfile() {}

    public BaselineProfile(Department department, double avgPatients,
                           double avgResponseTime, double avgStaff) {
        this.department = department;
        this.avgPatients = avgPatients;
        this.avgResponseTime = avgResponseTime;
        this.avgStaff = avgStaff;
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public double getAvgPatients() {
        return avgPatients;
    }

    public double getAvgResponseTime() {
        return avgResponseTime;
    }

    public double getAvgStaff() {
        return avgStaff;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setAvgPatients(double avgPatients) {
        this.avgPatients = avgPatients;
    }

    public void setAvgResponseTime(double avgResponseTime) {
        this.avgResponseTime = avgResponseTime;
    }

    public void setAvgStaff(double avgStaff) {
        this.avgStaff = avgStaff;
    }

}
