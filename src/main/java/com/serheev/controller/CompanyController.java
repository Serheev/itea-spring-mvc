package com.serheev.controller;

import com.serheev.dto.Company;

import com.serheev.service.CompanyService;
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
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/company/test")
    public String test() {
        return "Success!";
    }

    @PostMapping(value = "/company/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Company createCompany(@RequestBody Company company) {
        return companyService.create(company);
    }

    @GetMapping(value = "/company/list", consumes = "application/json")
    @ResponseBody
    public List<Company> getCompanys() {
        return companyService.findAll();
    }

    @GetMapping(value = "/company/{id}", consumes = "application/json")
    @ResponseBody
    public Company getCompany(@PathVariable("id") Long id) {
        return companyService.findCompanyById(id);
    }

    @PutMapping(value = "/company/update/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateCompany(@RequestBody Company company, @PathVariable("id") Long id){
        companyService.update(companyService.findCompanyById(id));
    }

    @DeleteMapping(value = "/company/delete/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@PathVariable("id") Long id){
        companyService.deleteById(id);
    }

}
