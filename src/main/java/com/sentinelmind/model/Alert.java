package com.sentinelmind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Department department;

    private String riskLevel;

    @Column(length = 1000)
    private String message;

    @Column(length = 1000)
    private String recommendation;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Alert() {}

    public Alert(Department department,
                 String riskLevel,
                 String message,
                 String recommendation) {
        this.department = department;
        this.riskLevel = riskLevel;
        this.message = message;
        this.recommendation = recommendation;
    }

    // ✅ GETTERS
    public Long getId() { return id; }
    public Department getDepartment() { return department; }
    public String getRiskLevel() { return riskLevel; }
    public String getMessage() { return message; }
    public String getRecommendation() { return recommendation; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ✅ SETTERS (THIS FIXES YOUR ERRORS)
    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
