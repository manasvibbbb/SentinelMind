package com.sentinelmind.repository;

import com.sentinelmind.model.Department;
import com.sentinelmind.model.WorkflowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkflowRecordRepository
        extends JpaRepository<WorkflowRecord, Long> {

    // Used for baseline calculation (recent window)
    List<WorkflowRecord> findByDepartmentAndTimestampAfter(
            Department department,
            LocalDateTime timestamp
    );

    // Used for charts and trends
    List<WorkflowRecord> findByDepartmentOrderByTimestampAsc(
            Department department
    );

    // ✅ USED BY RiskTrendService
    List<WorkflowRecord> findTop5ByDepartmentOrderByTimestampDesc(
            Department department
    );
    long countByDepartment(Department department);

}
