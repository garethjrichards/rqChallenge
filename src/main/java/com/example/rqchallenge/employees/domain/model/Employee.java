package com.example.rqchallenge.employees.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Employee {
    private long id;
    private String name;
    private int salary;
    private int age;
    private String profile_image;
}
