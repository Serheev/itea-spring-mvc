package com.serheev.controller;

import com.serheev.dto.Car;

import com.serheev.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping(value = "/car/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@RequestBody Car car) {
        return carService.create(car);
    }

    @GetMapping(value = "/car/list")
    public List<Car> getCars() {
        return carService.findAll();
    }

    @GetMapping(value = "/car/{id}")
    public Car getCar(@PathVariable("id") Long id) {
        return carService.findCarById(id);
    }

    @PutMapping(value = "/car/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCar(@RequestBody Car car, @PathVariable("id") Long id){
        carService.update(carService.findCarById(id));
    }

    @DeleteMapping(value = "/car/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable("id") Long id){
        carService.deleteById(id);
    }

}
