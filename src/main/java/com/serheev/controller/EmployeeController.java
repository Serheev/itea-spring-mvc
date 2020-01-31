package com.serheev.controller;

import com.serheev.dto.Employee;

import com.serheev.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/test")
    public String test() {
        return "Success!";
    }

    @PostMapping(value = "/employee/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @GetMapping(value = "/employee/list", consumes = "application/json")
    @ResponseBody
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }

    @GetMapping(value = "/employee/{id}", consumes = "application/json")
    @ResponseBody
    public Employee getEmployee(@PathVariable("id") Long id) {
        return employeeService.findEmployeeById(id);
    }

    @PutMapping(value = "/employee/update/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long id){
        employeeService.update(employeeService.findEmployeeById(id));
    }

    @DeleteMapping(value = "/employee/delete/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteById(id);
    }

}
