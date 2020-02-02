package com.serheev.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Project {
    private Long id;
    private String name;
    private Date starDate;
    private Date endDate;
    private Long cost;
    private Company companyId;
}
