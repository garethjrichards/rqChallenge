package com.example.rqchallenge.employees.domain.service;

import com.example.rqchallenge.employees.domain.model.Employee;
import com.example.rqchallenge.employees.infrastructure.service.EmployeeApi;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Main employee domain service implementing all the domain service related functions for employees.
 * Ideally some functions might live in the domain object Employee but no such functions exist currently.
 */
@Service
@Slf4j
public class EmployeeService implements IEmployeeService {

    private final EmployeeApi api;

    public EmployeeService(EmployeeApi repository) {
        this.api = repository;
    }

    /**
     * Get all the employee records
     *
     * @return a list of all employees
     */
    @Override
    public List<Employee> getAll() {
        log.info("Getting all employee records");
        return api.findAll();
    }

    /**
     * Gets all the employees by name based on a search string.
     * Finds all employees, filters on null names then, filters on names containing the search string.
     * Could possibly use a regex here as well (probably not as performant though)
     *
     * @param searchString The search string to use to find a match
     * @return a list of employees matching the search string
     */
    @Override
    public List<Employee> getByName(String searchString) {
        log.info("Getting all employees with names that match {}", searchString);
        return api.findAll()
                  .stream()
                  .filter(employee -> null != employee.getName())
                  .filter(employee -> employee.getName()
                                              .contains(searchString))
                  .peek(employee -> log.debug("Found employee matching {} - {}", searchString, employee))
                  .collect(Collectors.toList());
    }

    /**
     * Gets an Optional Employee by their id.
     *
     * @param id the Employee id
     * @return an Optional of the employee or an empty optional if none was found
     */
    @Override
    public Optional<Employee> getById(String id) {
        log.info("Getting an employee by their id {}", id);
        return api.findById(id);
    }

    /**
     * Gets the highest salary from all employees.
     * Finds all employees, gets the max salary, maps the salary and returns.
     *
     * @return An optional of the salary
     */
    @Override
    public Optional<Integer> getHighestSalary() {
        log.info("Getting the highest salary of all employees");
        return api.findAll()
                  .stream()
                  .max(Comparator.comparingInt(Employee::getSalary))
                  .map(Employee::getSalary);
    }

    /**
     * Gets the top ten highest earners by name.
     * finds all employees, sorts based on salary (high to low), limits to 10 records,
     * maps to their names, and returns
     *
     * @return The names as a list
     */
    @Override
    public List<String> getTopTenHighestEarnersByName() {
        log.info("Getting the top ten salaried employees");
        return api.findAll()
                  .stream()
                  .sorted(Comparator.comparing(Employee::getSalary)
                                    .reversed())
                  .limit(10)
                  .peek(employee -> log.debug("Top Ten Employee {}", employee))
                  .map(Employee::getName)
                  .collect(Collectors.toList());
    }

    /**
     * Creates a new Employee record using name, salary, age. Checks to make sure all values are present before creating
     *
     * @param employeeInput The map describing the name, salary, and age
     * @return The optional employee that was created or an empty optional if it failed
     */
    @Override
    public Optional<Employee> create(Map<String, Object> employeeInput) {
        log.info("Attempting to create employee with {}", employeeInput);
        Optional<String> name = Optional.ofNullable((String) employeeInput.getOrDefault("name", null));
        Optional<String> salary = Optional.ofNullable((String) employeeInput.getOrDefault("salary", null));
        Optional<String> age = Optional.ofNullable((String) employeeInput.getOrDefault("age", null));

        if (name.isPresent() && salary.isPresent() && age.isPresent()) {
            log.debug("Creating new employee with name {}, salary {}, age {}", name, salary, age);
            return api.create(name.get(), salary.get(), age.get());
        }
        log.warn("Could not create employee with name {}, salary {}, age {}", name, salary, age);
        return Optional.empty();
    }

    /**
     * Deletes an Employee by their id. Only returns successful if response has returned success.
     *
     * @param id The Employee id
     * @return The optional of the employee that was deleted or an empty optional if it failed
     */
    @Override
    public Optional<String> delete(String id) {
        log.info("Attempting to delete employee with id {}", id);
        return api.delete(id)
                  .filter("success"::equals);
    }
}

