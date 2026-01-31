package com.sentinelmind.repository;

import com.sentinelmind.model.BaselineProfile;
import com.sentinelmind.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaselineProfileRepository
        extends JpaRepository<BaselineProfile, Long> {

    Optional<BaselineProfile> findByDepartment(Department department);
}
