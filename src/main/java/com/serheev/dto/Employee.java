package com.serheev.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Employee {
    private Long id;
    private String name;
    private int age;
    private String sex;
    private Long salary;
    private boolean onLeave;
    private Project projectId;
    private Car carId;
}
