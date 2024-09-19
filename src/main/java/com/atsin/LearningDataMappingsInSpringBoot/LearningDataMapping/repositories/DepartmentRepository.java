package com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.repositories;

import com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.entities.DepartmentEntity;
import com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findByManager(EmployeeEntity employeeEntity);
}
