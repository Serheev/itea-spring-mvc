package com.serheev.service;

import com.serheev.dto.Employee;
import com.serheev.interceptor.SimpleLogger;
import com.serheev.model.EmployeeEntity;
import com.serheev.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void update(Employee employee) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employee.getId());
        if (!employeeEntity.isPresent()) {
            return;
        }
        employeeEntity.get().setName(employee.getName()).setAge(employee.getAge()).setSex(employee.getSex()).setSalary(employee.getSalary());
    }

    public void delete(Employee employee) {
        employeeRepository.deleteById(employee.getId());
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
