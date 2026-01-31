package com.sentinelmind.repository;

import com.sentinelmind.model.Alert;
import com.sentinelmind.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByDepartment(Department department);
    List<Alert> findTop5ByDepartmentOrderByCreatedAtDesc(Department department);

}
