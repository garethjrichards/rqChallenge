package com.example.rqchallenge;

import com.example.rqchallenge.employees.application.controller.IEmployeeController;
import com.example.rqchallenge.employees.domain.service.IEmployeeService;
import com.example.rqchallenge.employees.infrastructure.service.EmployeeApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RqChallengeApplicationTests {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IEmployeeController employeeController;
    @Autowired
    private EmployeeApi employeeApi;

    /**
     * Just a quick validation that the Spring context loads correctly
     */
    @Test
    void testContextLoads() {
        assertThat(employeeService).isNotNull();
        assertThat(employeeController).isNotNull();
        assertThat(employeeApi).isNotNull();
    }
}
