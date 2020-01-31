package com.serheev.model;

import com.serheev.listener.CreatableDateListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(CreatableDateListener.class)
public class CreatableDateEntity extends BaseEntity{
    @Column(name="created_date")
    private Long createdDate;
}
