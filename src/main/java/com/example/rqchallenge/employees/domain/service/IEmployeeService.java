package com.example.rqchallenge.employees.domain.service;

import com.example.rqchallenge.employees.domain.model.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> getAll();

    List<Employee> getByName(String searchString);

    Optional<Employee> getById(String id);

    Optional<Integer> getHighestSalary();

    List<String> getTopTenHighestEarnersByName();

    Optional<Employee> create(Map<String, Object> employeeInput);

    Optional<String> delete(String id);
}
