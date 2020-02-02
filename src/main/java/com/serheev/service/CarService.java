package com.serheev.service;

import com.serheev.dto.Car;
import com.serheev.interceptor.SimpleLogger;
import com.serheev.model.CarEntity;
import com.serheev.repository.CarRepository;

import com.serheev.utils.ModelEvaluate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Interceptors(SimpleLogger.class)
    public Car create(Car car) {
        return convertToDto(carRepository.saveAndFlush(new CarEntity()
                        .setModel(ModelEvaluate.evaluateModel(car.getModel()))
                        .setPower(car.getPower())
                )
        );
    }

    @Transactional
    public void update(Car car) {
        Optional<CarEntity> carEntity = carRepository.findById(car.getId());
        if (!carEntity.isPresent()) {
            return;
        }
        carEntity.get().setModel(ModelEvaluate.evaluateModel(car.getModel())).setPower(car.getPower());
    }

    public void delete(Car car) {
        carRepository.deleteById(car.getId());
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

    public void truncate() {
        carRepository.deleteAll();
    }

    public Car findCarById(Long id) {
        return convertToDto(carRepository.findCarEntitiesById(id));
    }

    public List<Car> findAll() {
        return carRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CarEntity getCarEntity(Car car){
        return convertToEntity(car);
    }

    public Car getCarDto(CarEntity carEntity){
        return convertToDto(carEntity);
    }

    private Car convertToDto(CarEntity carEntity) {
        Car car = modelMapper.map(carEntity, Car.class);
        car.setModel(carEntity.getModel().toString());
        car.setPower(carEntity.getPower());
        return car;
    }

    private CarEntity convertToEntity(Car car) {
        CarEntity carEntity = modelMapper.map(car, CarEntity.class);
        carEntity.setModel(ModelEvaluate.evaluateModel(car.getModel()));
        carEntity.setPower(car.getPower());
        return carEntity;
    }
}
