package com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.controllers;

import com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.entities.EmployeeEntity;
import com.atsin.LearningDataMappingsInSpringBoot.LearningDataMapping.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity employeeEntity){
        return employeeService.createNewEmployee(employeeEntity);
    }

    @GetMapping("/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable Long employeeId){
        return employeeService.getEmployeeById(employeeId);
    }
}
