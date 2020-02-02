package com.serheev.listener;

import com.serheev.model.CreatableDateEntity;

import javax.persistence.PrePersist;
import java.util.Date;

public class CreatableDateListener {

    @PrePersist
    public void setCreatedDate(CreatableDateEntity entity){
        entity.setCreatedDate(new Date().getTime());
    }
}
