package com.serheev.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Company {
    private Long id;
    private String name;
    private Date foundationDate;
    private Long cost;
}
