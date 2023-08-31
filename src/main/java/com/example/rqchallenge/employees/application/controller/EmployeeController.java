package com.example.rqchallenge.employees.application.controller;

import com.example.rqchallenge.employees.domain.model.Employee;
import com.example.rqchallenge.employees.domain.service.IEmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * The Employee controller using the interface specified. Some possible improvements could be the use of WebFlux
 * components to make it completely reactive. There is also a difference from the README.md file for the creation of
 * an employee `createEmployee() should return string of the status (i.e. success)` but the interface had it return
 * an Employee. I do think the interface is correct/better in this case though and have kept it as such.
 */
@Component
public class EmployeeController implements IEmployeeController {

    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Get All Employees
     * @return A ResponseEntity of a list of Employees
     */
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    /**
     * Get Employees by name search
     * @param searchString The search string to match against Employee names
     * @return A ResponseEntity of a list of Employees that match
     */
    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return ResponseEntity.ok(employeeService.getByName(searchString));
    }

    /**
     * Get Employee by id
     * @param id the Employee id
     * @return A ResponseEntity of the Employee
     */
    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return ResponseEntity.of(employeeService.getById(id));
    }

    /**
     * Get the highest salary
     * @return The highest salary
     */
    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.of(employeeService.getHighestSalary());
    }

    /**
     * Get the top ten highest earners
     * @return A ResponseEntity of a list of top ten highest earning Employees
     */
    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ResponseEntity.ok(employeeService.getTopTenHighestEarnersByName());
    }

    /**
     * Create an employee
     * @param employeeInput A json string with name, salary, and age fields
     * @return A ResponseEntity of the employee that was created or badRequest if failed
     */
    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        return employeeService.create(employeeInput)
                       .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
                       .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Delete an Employee
     * @param id The Employee's id
     * @return A ResponseEntity of the employee that was deleted or badRequest if failed
     */
    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return employeeService.delete(id)
                              .map(resp -> ResponseEntity.status(HttpStatus.ACCEPTED).body(resp))
                              .orElse(ResponseEntity.badRequest().build());
    }
}
