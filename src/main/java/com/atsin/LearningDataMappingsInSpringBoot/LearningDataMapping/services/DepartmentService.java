package com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.services;

import com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.entities.DepartmentEntity;
import com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.entities.EmployeeEntity;
import com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.repositories.DepartmentRepository;
import com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public DepartmentEntity createNewDepartment(DepartmentEntity departmentEntity){
        return departmentRepository.save(departmentEntity);
    }

    public DepartmentEntity getDepartmentById(Long id){
        return departmentRepository.findById(id).orElse(null);
    }

    public DepartmentEntity assignManagerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        return departmentEntity.flatMap(department ->
            employeeEntity.map(employee -> {
                department.setManager(employee);
                return departmentRepository.save(department);
            })).orElse(null);
    }

    public DepartmentEntity getAssignedDepartmentOfManager(Long employeeId) {
//        First way
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
//        return  employeeEntity.map(empoyee-> empoyee.getManagedDepartment()).orElse(null);

//        Second Way
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(employeeId).build();
        return departmentRepository.findByManager(employeeEntity);
    }

    public ResponseEntity<List<DepartmentEntity>> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return ResponseEntity.ok(departmentEntities);
    }

    public DepartmentEntity assignWorkerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        return departmentEntity.flatMap(department ->
                employeeEntity.map(employee -> {
                    employee.setWorkerDepartment(department);
                    employeeRepository.save(employee);

                    department.getWorkers().add(employee);
                    return department;
                })).orElse(null);
    }

    public DepartmentEntity assignFreelancerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);

        return departmentEntity.flatMap(department ->
                employeeEntity.map(employee -> {
                    employee.getFreelanceDepartments().add(department);
                    employeeRepository.save(employee);

                    department.getFreelancers().add(employee);
                    return department;
                })).orElse(null);
    }
}
