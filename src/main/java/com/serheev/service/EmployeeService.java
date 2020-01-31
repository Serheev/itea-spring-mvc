package com.serheev.service;

import com.serheev.dto.Employee;
import com.serheev.interceptor.SimpleLogger;
import com.serheev.model.EmployeeEntity;
import com.serheev.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Interceptors(SimpleLogger.class)
    public Employee create(Employee employee) {
        return convertToDto(employeeRepository.saveAndFlush(new EmployeeEntity()
                        .setName(employee.getName())
                        .setAge(employee.getAge())
                        .setSalary(employee.getSalary())
                        .setSex(employee.getSex())
                )
        );
    }

    public void update(Employee employee) {
        if (employee.getId() != 0) {
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employee.getId());
            employeeEntity.orElse(null)
                    .setName(employee.getName())
                    .setAge(employee.getAge())
                    .setSalary(employee.getSalary())
                    .setSex(employee.getSex());
            employeeRepository.saveAndFlush(employeeEntity.orElse(null));
        }
    }

    public void delete(Employee employee) {
        if (employee.getId() != 0) {
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employee.getId());
            employeeRepository.delete(employeeEntity.orElse(null));
        }
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public void truncate() {
        employeeRepository.deleteAll();
    }

    public Employee findEmployeeById(Long id) {
        return convertToDto(employeeRepository.findEmployeeEntitiesById(id));
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Employee convertToDto(EmployeeEntity employeeEntity) {
        Employee employee = modelMapper.map(employeeEntity, Employee.class);
        employee.setName(employeeEntity.getName());
        employee.setAge(employeeEntity.getAge());
        employee.setSex(employeeEntity.getSex());
        employee.setSalary(employeeEntity.getSalary());
        return employee;
    }

    private EmployeeEntity convertToEntity(Employee employee) {
        EmployeeEntity employeeEntity = modelMapper.map(employee, EmployeeEntity.class);
        employeeEntity.setName(employee.getName());
        employeeEntity.setAge(employee.getAge());
        employeeEntity.setSex(employee.getSex());
        employeeEntity.setSalary(employee.getSalary());
        return employeeEntity;
    }
}
