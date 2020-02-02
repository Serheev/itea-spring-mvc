package com.serheev.service;

import com.serheev.configuration.DataSourceConfiguration;
import com.serheev.dto.Car;
import com.serheev.model.CarEntity;
import com.serheev.model.Model;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.util.Assert.notNull;

@DirtiesContext
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfiguration.class})
public class CarServiceTest {
    private static Logger log = Logger.getLogger(CarServiceTest.class);

    @Autowired
    private CarService carService;
    private ModelMapper modelMapper;
    private Car car;

    @Before
    public void setUp() {
        /** Pre-testing */
        modelMapper = new ModelMapper();
        notNull(carService, "null");
        /** Predicate */
        car = new Car();
        car.setModel("AUDI");
        car.setPower(500.0);
    }

    @After
    public void cleanAfterTest(){
        carService.truncate();
    }

    @Test
    public void mappingDtoToEntityIsSuccess() {
        /** Functionality */
        CarEntity carEntity = modelMapper.map(car, CarEntity.class);
        /** Testing */
        assertEquals(car.getModel(), carEntity.getModel().toString());
        assertEquals(car.getPower(), carEntity.getPower());
        /** Logging */
        log.info(car.getModel() + " = " + carEntity.getModel().toString());
        log.info(car.getPower() + " = " + carEntity.getPower());
    }

    @Test
    public void mappingEntityToDtoIsSuccess() {
        /** Predicate */
        CarEntity carEntity = new CarEntity().setModel(Model.BMW).setPower(450.0);
        /** Functionality */
        Car carDto = modelMapper.map(carEntity, Car.class);
        /** Testing */
        assertEquals(carDto.getModel(), carEntity.getModel().toString());
        assertEquals(carDto.getPower(), carEntity.getPower());
        /** Logging */
        log.info(carDto.getModel() + " = " + carEntity.getModel().toString());
        log.info(carDto.getPower() + " = " + carEntity.getPower());
    }

    @Test
    public void carShouldBeCreatedAndPutInDataBase() {
        /** Predicate */
        carService.create(car);
        /** Testing */
        assertEquals(1, carService.findAll().size());
        /** Logging */
        carService.findAll().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void carShouldBeGetById() {
        /** Predicate */
        Car carDto = carService.create(car);
        /** Functionality and Testing*/
        assertEquals(car.getModel(), carService.findCarById(carDto.getId()).getModel());
        /** Logging */
        log.info(carService.findCarById(carDto.getId()));
    }

    @Test
    public void allCarsShouldBeGetFromDataBase() {
        carService.create(car);
        carService.create(car);
        carService.create(car);
        /** Functionality and Testing */
        assertEquals(3, carService.findAll().size());
        /** Logging */
        carService.findAll().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void carFieldsShouldBeUpdate() {
        /** Predicate */
        Car carDto = carService.create(car);
        /** Functionality */
        carDto.setModel("MERCEDES");
        carDto.setPower(250.0);
        carService.update(carDto);
        /** Testing */
        assertNotEquals(car.getModel(), carService.findCarById(carDto.getId()).getModel());
        assertEquals(carDto.getModel(), carService.findCarById(carDto.getId()).getModel());
        assertEquals(carDto.getId(), carService.findCarById(carDto.getId()).getId());
        assertEquals(carDto.getCreatedDate(), carService.findCarById(carDto.getId()).getCreatedDate());
    }

    @Test
    public void carFieldsShouldBeDeleteByid() {
        /** Predicate and Functionality */
        carService.deleteById(carService.create(car).getId());
        /** Testing */
        assertEquals(0, carService.findAll().size());
    }

    @Test
    public void carFieldsShouldBeDeleteByCarDto() {
        /** Predicate and Functionality */
        carService.delete(carService.create(car));
        /** Testing */
        assertEquals(0, carService.findAll().size());
    }
}
