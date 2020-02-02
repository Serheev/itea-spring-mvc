package com.serheev.repository;

import com.serheev.model.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    CarEntity findCarEntitiesById(Long id);
}
