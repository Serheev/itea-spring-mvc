package com.serheev.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Car {
    private Long id;
    private String model;
    private Double power;
    private String createdDate;
}
