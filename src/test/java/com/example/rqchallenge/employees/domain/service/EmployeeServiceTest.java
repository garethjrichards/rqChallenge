package com.example.rqchallenge.employees.domain.service;

import com.example.rqchallenge.employees.domain.model.Employee;
import com.example.rqchallenge.employees.infrastructure.service.EmployeeApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Employee Service tests with a mocked EmployeeApi.
 */
@SpringBootTest
class EmployeeServiceTest {

    @MockBean
    private EmployeeApi mockEmployeeApi;
    @Autowired
    private IEmployeeService employeeService;

    @BeforeEach
    void setUp() {
        when(mockEmployeeApi.findAll()).thenReturn(List.of(
                new Employee(1L, "Tiger Nixon", 320800, 61, ""),
                new Employee(2L, "Garrett Winters", 170750, 63, ""),
                new Employee(3L, "Ashton Cox", 86000, 66, ""),
                new Employee(4L, "Cedric Kelly", 433060, 22, ""),
                new Employee(5L, "Airi Satou", 162700, 33, ""),
                new Employee(6L, "Brielle Williamson", 372000, 61, ""),
                new Employee(7L, "Herrod Chandler", 137500, 59, ""),
                new Employee(8L, "Rhona Davidson", 327900, 55, ""),
                new Employee(9L, "Colleen Hurst", 205500, 39, ""),
                new Employee(10L, "Sonya Frost", 103600, 23, ""),
                new Employee(11L, "Jena Gaines", 90560, 30, ""),
                new Employee(12L, "Quinn Flynn", 342000, 22, "")));

        when(mockEmployeeApi.findById("1")).thenReturn(Optional.of(new Employee(1L, "Tiger Nixon", 320800, 61, "")));

        when(mockEmployeeApi.create("Tiger Nixon", "320800", "61")).thenReturn(Optional.of(new Employee(1L, "Tiger Nixon", 320800, 61, "")));

        when(mockEmployeeApi.delete(anyString())).thenReturn(Optional.of("success"));
    }

    @Test
    void getAll() {
        List<Employee> employeeList = employeeService.getAll();
        assertThat(employeeList.isEmpty()).isFalse();
        assertThat(employeeList.stream()
                               .filter(employee -> employee.getId() == 1)
                               .findFirst()
                               .map(Employee::getName)
                               .get()).isEqualTo("Tiger Nixon");
    }

    @Test
    void getByName() {
        List<Employee> employeeList = employeeService.getByName("Garrett");
        assertThat(employeeList.isEmpty()).isFalse();
        assertThat(employeeList.get(0)
                               .getName()).isEqualTo("Garrett Winters");
        assertThat(employeeList.get(0)
                               .getId()).isEqualTo(2);
    }

    @Test
    void getById() {
        Optional<Employee> employeeOptional = employeeService.getById("1");
        assertThat(employeeOptional).isPresent();
        assertThat(employeeOptional.get()
                                   .getName()).isEqualTo("Tiger Nixon");
    }

    @Test
    void getHighestSalary() {
        Optional<Integer> highestSalaryOptional = employeeService.getHighestSalary();
        assertThat(highestSalaryOptional).isPresent();
        assertThat(highestSalaryOptional.stream()
                                        .mapToInt(Integer::intValue)
                                        .findFirst()
                                        .getAsInt()).isEqualTo(433060);
    }

    @Test
    void getTopTenHighestEarnersByName() {
        List<String> topEarnerList = employeeService.getTopTenHighestEarnersByName();
        assertThat(topEarnerList.isEmpty()).isFalse();
        assertThat(topEarnerList.size()).isEqualTo(10);
        assertThat(topEarnerList.stream()
                                .filter("Ashton Cox"::equals)
                                .findFirst()).isEmpty();
    }

    @Test
    void create() {
        Map<String, Object> map = Map.of("name", "Tiger Nixon", "salary", "320800", "age", "61");
        Optional<Employee> employeeOptional = employeeService.create(map);
        assertThat(employeeOptional).isPresent();
        assertThat(employeeOptional.get()
                                   .getName()).isEqualTo("Tiger Nixon");
    }

    @Test
    void delete() {
        Optional<String> responseOptional = employeeService.delete("1");
        assertThat(responseOptional).isPresent();
        assertThat(responseOptional.get()).isEqualTo("success");
    }
}
