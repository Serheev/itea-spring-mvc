package com.serheev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "car")
public class CarEntity extends CreatableDateEntity {
    @Column(name = "model")
    @Enumerated(EnumType.STRING)
    private Model model;
    @Column(name = "power")
    private Double power;

    @OneToOne(mappedBy = "car")
    private EmployeeEntity employee;
}
