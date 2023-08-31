package com.example.rqchallenge.employees.infrastructure.service;

import com.example.rqchallenge.employees.domain.model.Employee;
import com.example.rqchallenge.employees.infrastructure.model.DeleteResponse;
import com.example.rqchallenge.employees.infrastructure.model.EmployeeListResponse;
import com.example.rqchallenge.employees.infrastructure.model.EmployeeResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * Rest API Example Service. Would have loved to build in some rate limiting but the Example service
 * is so slow that I really don't think this will be a viable option anyway. Some possible improvements
 * could be the use of WebFlux to create a reactive webclient.
 */
@Service
@Slf4j
public class RestApiExampleService implements EmployeeApi {

    private final RestTemplate restTemplate;

    public RestApiExampleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Finds all the employees on the API
     *
     * @return a list of all the employees
     */
    @Override
    public List<Employee> findAll() {
        log.info("Getting employee list");
        EmployeeListResponse response = restTemplate.getForObject("/employees", EmployeeListResponse.class);
        return (null != response) ? response.getData() : Collections.emptyList();
    }

    /**
     * Finds an employee by id
     *
     * @param id the employee id
     * @return an Optional employee
     */
    @Override
    public Optional<Employee> findById(String id) {
        log.info("Getting employee using id {}", id);
        Map<String, String> vars = new HashMap<>();
        vars.put("id", id);

        EmployeeResponse response = restTemplate.getForObject("/employee/{id}", EmployeeResponse.class, vars);
        return (null != response) ? Optional.of(response.getData()) : Optional.empty();
    }

    /**
     * Creates an employee using various parameters
     *
     * @param name   the employee name
     * @param salary the employee salary
     * @param age    the employee age
     * @return the Optional Employee
     */
    @Override
    public Optional<Employee> create(String name, String salary, String age) {
        log.info("Creating employee using name {}, salary {}, age {}", name, salary, age);
        Map<String, String> vars = Map.of("name", name, "salary", salary, "age", age);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(vars);

        EmployeeResponse response = restTemplate.exchange("/create", HttpMethod.POST, httpEntity, EmployeeResponse.class)
                                                .getBody();
        return (null != response) ? Optional.of(response.getData()) : Optional.empty();
    }

    /**
     * Deletes the employee using their id. Ideally if this was a database repository I wouldn't delete (ever)
     * I'd rather disable the entity.
     *
     * @param id the employee id to delete
     * @return Optional string of success or failure
     */
    @Override
    public Optional<String> delete(String id) {
        log.info("Deleting employee using id {}", id);
        Map<String, String> vars = Map.of("id", id);

        DeleteResponse response = restTemplate.exchange("/delete/{id}", HttpMethod.DELETE, null, DeleteResponse.class, vars)
                                              .getBody();
        return (null != response) ? Optional.of(response.getStatus()) : Optional.empty();
    }
}
