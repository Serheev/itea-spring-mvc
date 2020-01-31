package com.serheev.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Company {
    private Long id;
    private String name;
    private Date foundationDate;
    private Long cost;
}
