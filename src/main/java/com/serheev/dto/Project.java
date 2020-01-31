package com.serheev.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Project {
    private Long id;
    private String name;
    private Date starDate;
    private Date endDate;
    private Long cost;
}
