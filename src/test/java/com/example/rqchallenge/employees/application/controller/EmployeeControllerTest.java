package com.example.rqchallenge.employees.application.controller;

import com.example.rqchallenge.employees.domain.model.Employee;
import com.example.rqchallenge.employees.domain.service.IEmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Employee Controller test with a mocked Employee service.
 */
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private IEmployeeService employeeService;

    @BeforeEach
    void setUp() {
        when(employeeService.getAll()).thenReturn(List.of(
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

        when(employeeService.getByName("Quinn")).thenReturn(List.of(new Employee(12L, "Quinn Flynn", 342000, 22, "")));

        when(employeeService.getById("6")).thenReturn(Optional.of(new Employee(6L, "Brielle Williamson", 372000, 61, "")));

        when(employeeService.getHighestSalary()).thenReturn(Optional.of(433060));

        when(employeeService.getTopTenHighestEarnersByName()).thenReturn(List.of("Cedric Kelly", "Brielle Williamson", "Quinn Flynn", "Rhona Davidson", "Tiger Nixon", "Colleen Hurst", "Garrett Winters", "Airi Satou", "Herrod Chandler", "Sonya Frost"));

        when(employeeService.create(Map.of("name", "Tiger Nixon", "salary", "320800", "age", "61"))).thenReturn(Optional.of(new Employee(1L, "Tiger Nixon", 320800, 61, "")));

        when(employeeService.delete(anyString())).thenReturn(Optional.of("success"));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(12)));
    }

    @Test
    void testGetEmployeesByNameSearch() throws Exception {
        mockMvc.perform(get("/search/{searchString}", "Quinn"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.[0].id").value(12));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        mockMvc.perform(get("/{id}", 6))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.name").value("Brielle Williamson"));
    }

    @Test
    void testGetHighestSalaryOfEmployees() throws Exception {
        mockMvc.perform(get("/highestSalary"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$").value(433060));
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() throws Exception {
        mockMvc.perform(get("/topTenHighestEarningEmployeeNames"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$", hasSize(10)))
               .andExpect(jsonPath("$.[0]").value("Cedric Kelly"));
    }

    @Test
    void testCreateEmployee() throws Exception {
        mockMvc.perform(post("/")
                       .content(objectMapper.writeValueAsString(Map.of("name", "Tiger Nixon", "salary", "320800", "age", "61")))
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.name").value("Tiger Nixon"));
    }

    @Test
    void testDeleteEmployeeById() throws Exception {
        mockMvc.perform(delete("/{id}", "12"))
               .andExpect(status().isAccepted())
               .andExpect(jsonPath("$").value("success"));
    }
}
