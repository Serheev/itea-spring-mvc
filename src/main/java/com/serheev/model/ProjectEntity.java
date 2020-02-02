package com.serheev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "project")
public class ProjectEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Date starDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "cost")
    private Long cost;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private CompanyEntity companyId;

    @OneToMany(mappedBy = "projectId")
    private Set<EmployeeEntity> employees;
}