package com.serheev.service;

import com.serheev.dto.Car;
import com.serheev.interceptor.SimpleLogger;
import com.serheev.model.CarEntity;
import com.serheev.model.Model;
import com.serheev.repository.CarRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.interceptor.Interceptors;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Interceptors(SimpleLogger.class)
    public Car create(Car car) {
        return convertToDto(carRepository.saveAndFlush(new CarEntity()
                        .setModel(evaluateModel(car.getModel()))
                        .setPower(car.getPower())
                )
        );
    }

    public void update(Car car) {
        if (car.getId() != 0) {
            Optional<CarEntity> carEntity = carRepository.findById(car.getId());
            carEntity.orElse(null)
                    .setModel(evaluateModel(car.getModel()))
                    .setPower(car.getPower());
            carRepository.saveAndFlush(carEntity.orElse(null));
        }
    }

    public void delete(Car car) {
        if (car.getId() != 0) {
            Optional<CarEntity> carEntity = carRepository.findById(car.getId());
            carRepository.delete(carEntity.orElse(null));
        }
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

    private Model evaluateModel(String model) {
        return Arrays.stream(Model.values())
                .map(Enum::toString)
                .collect(toList())
                .contains(model)
                ? Model.valueOf(model)
                : Model.UNKNOWN;
    }

    private Car convertToDto(CarEntity carEntity) {
        Car car = modelMapper.map(carEntity, Car.class);
        car.setModel(carEntity.getModel().toString());
        car.setPower(carEntity.getPower());
        return car;
    }

    private CarEntity convertToEntity(Car car) {
        CarEntity carEntity = modelMapper.map(car, CarEntity.class);
        carEntity.setModel(evaluateModel(car.getModel()));
        carEntity.setPower(car.getPower());
        return carEntity;
    }
}
