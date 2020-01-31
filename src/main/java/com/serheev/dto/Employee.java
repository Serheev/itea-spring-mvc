package com.serheev.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private Long id;
    private String name;
    private int age;
    private String sex;
    private Long salary;
    boolean onLeave;
}
