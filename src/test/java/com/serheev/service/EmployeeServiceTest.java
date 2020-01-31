package com.serheev.service;

import com.serheev.configuration.DataSourceConfiguration;
import com.serheev.dto.Employee;
import com.serheev.model.EmployeeEntity;
import org.apache.log4j.Logger;
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
public class EmployeeServiceTest {
    private static Logger log = Logger.getLogger(EmployeeServiceTest.class);

    @Autowired
    private EmployeeService employeeService;

    private ModelMapper modelMapper = new ModelMapper();
    private Employee employee;

    @Before
    public void setUp() {
        /** Pre-testing */
        employeeService.truncate();
        notNull(employeeService, "null");
        /** Predicate */
        employee = new Employee();
        employee.setName("John");
        employee.setAge(30);
        employee.setSex("man");
        employee.setSalary((long) 1_000_000);
        employee.setOnLeave(true);
    }

    @Test
    public void mappingDtoToEntityIsSuccess() {
        /** Functionality */
        EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
        /** Testing */
        assertEquals(employee.getName(), employeeEntity.getName());
        assertEquals(employee.getAge(), employeeEntity.getAge());
        assertEquals(employee.getSex(), employeeEntity.getSex());
        /** Logging */
        log.info(employee.getName() + " = " + employeeEntity.getName());
        log.info(employee.getAge() + " = " + employeeEntity.getAge());
        log.info(employee.getSex() + " = " + employeeEntity.getSex());
        log.info(employee.getSalary() + " = " + employeeEntity.getSalary());
    }

    @Test
    public void mappingEntityToDtoIsSuccess() {
        /** Predicate */
        EmployeeEntity employeeEntity = new EmployeeEntity()
                .setName("Kris")
                .setAge(25)
                .setSalary((long) 2_000);
        /** Functionality */
        Employee employeeDto = modelMapper.map(employeeEntity, Employee.class);
        /** Testing */
        assertEquals(employeeDto.getName(), employeeEntity.getName());
        assertEquals(employeeDto.getAge(), employeeEntity.getAge());
        assertEquals(employeeDto.getSalary(), employeeEntity.getSalary());
        /** Logging */
        log.info(employeeDto.getName() + " = " + employeeEntity.getName());
        log.info(employeeDto.getAge() + " = " + employeeEntity.getAge());
        log.info(employeeDto.getSalary() + " = " + employeeEntity.getSalary());
    }

    @Test
    public void employeeShouldBeCreatedAndPutInDataBase() {
        /** Predicate */
        employeeService.create(employee);
        /** Testing */
        assertEquals(1, employeeService.findAll().size());
        /** Logging */
        employeeService.findAll().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void employeeShouldBeGetById() {
        /** Predicate */
        Employee employeeDto = employeeService.create(employee);
        /** Functionality and Testing*/
        assertEquals(employee.getName(), employeeService.findEmployeeById(employeeDto.getId()).getName());
        /** Logging */
        log.info(employeeService.findEmployeeById(employeeDto.getId()));
    }

    @Test
    public void allEmployeesShouldBeGetFromDataBase() {
        employeeService.create(employee);

        /** Functionality and Testing */
        assertEquals(1, employeeService.findAll().size());
        /** Logging */
        employeeService.findAll().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void employeeFieldsShouldBeUpdate() {
        /** Predicate */
        Employee employeeDto = employeeService.create(employee);
        /** Functionality */
        employeeDto.setName("Michael Jackson");
        employeeDto.setSalary((long) 2_500);
        employeeService.update(employeeDto);
        /** Testing */
        assertNotEquals(employee.getName(), employeeService.findEmployeeById(employeeDto.getId()).getName());
        assertEquals(employeeDto.getName(), employeeService.findEmployeeById(employeeDto.getId()).getName());
        assertEquals(employeeDto.getId(), employeeService.findEmployeeById(employeeDto.getId()).getId());
    }

    @Test
    public void employeeFieldsShouldBeDeleteByid() {
        /** Predicate and Functionality */
        employeeService.deleteById(employeeService.create(employee).getId());
        /** Testing */
        assertEquals(0, employeeService.findAll().size());
    }

    @Test
    public void employeeFieldsShouldBeDeleteByEmployeeDto() {
        /** Predicate and Functionality */
        employeeService.delete(employeeService.create(employee));
        /** Testing */
        assertEquals(0, employeeService.findAll().size());
    }
}