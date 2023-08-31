package com.example.rqchallenge.employees.infrastructure.service;

import com.example.rqchallenge.employees.domain.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeApi {
    List<Employee> findAll();

    Optional<Employee> findById(String id);

    Optional<Employee> create(String name, String salary, String age);

    Optional<String> delete(String id);
}
