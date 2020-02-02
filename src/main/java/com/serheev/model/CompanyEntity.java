package com.serheev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "company")
public class CompanyEntity extends CreatableDateEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "foundation_date")
    private Date foundationDate;

    @Column(name = "cost")
    private Long cost;

    @OneToMany(mappedBy = "companyId")
    private Set<ProjectEntity> projects;
}
