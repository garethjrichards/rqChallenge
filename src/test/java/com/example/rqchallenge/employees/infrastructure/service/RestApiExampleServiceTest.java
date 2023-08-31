package com.example.rqchallenge.employees.infrastructure.service;

import com.example.rqchallenge.employees.domain.model.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Rest API Example test. Due to rate limiting this test fails almost always. Really should be an integration test.
 * Disabled until rate limiting can be handled. Couldn't test reliably
 */
@SpringBootTest
class RestApiExampleServiceTest {

    @Autowired
    private EmployeeApi apiService;

    @BeforeEach
    void setUp() {
    }

    @Disabled
    @Test
    void testFindAll() {
        assertThat(apiService.findAll()).isNotEmpty();
    }

    @Disabled
    @Test
    void testFindById() {
        Optional<Employee> employeeOptional = apiService.findById("1");
        System.out.println(employeeOptional);
        assertThat(employeeOptional).isPresent();
        assertThat(employeeOptional.map(Employee::getName)
                                   .get()).isEqualTo("Tiger Nixon");
    }

    @Disabled
    @Test
    void testCreate() {
        Optional<Employee> employeeOptional = apiService.create("Test-Bobby-Bob", "1", "1");
        assertThat(employeeOptional).isPresent();
        assertThat(employeeOptional.map(Employee::getName)
                                   .get()).isEqualTo("Test-Bobby-Bob");
        apiService.delete(String.valueOf(employeeOptional.map(Employee::getId)
                                                         .get()));
    }

    @Disabled
    @Test
    void testDelete() {
        Optional<Employee> employeeOptional = apiService.create("Test-Bobby-Bob", "1", "1");
        Optional<String> responseOptional = apiService.delete(String.valueOf(employeeOptional.map(Employee::getId)
                                                                                             .get()));
        assertThat(responseOptional).isPresent();
        assertThat(responseOptional.get()).isEqualTo("success");
    }
}
