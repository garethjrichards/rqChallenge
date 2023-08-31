package com.example.rqchallenge.employees.infrastructure.model;

import com.example.rqchallenge.employees.domain.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponse {
    Employee data;
}
