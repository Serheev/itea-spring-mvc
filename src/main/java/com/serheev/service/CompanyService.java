package com.serheev.service;

import com.serheev.dto.Company;
import com.serheev.interceptor.SimpleLogger;
import com.serheev.model.CompanyEntity;
import com.serheev.repository.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Interceptors(SimpleLogger.class)
    public Company create(Company company) {
        return convertToDto(companyRepository.saveAndFlush(new CompanyEntity()
                        .setName(company.getName())
                        .setFoundationDate(company.getFoundationDate())
                        .setCost(company.getCost())
                )
        );
    }

    @Transactional
    public void update(Company company) {
        Optional<CompanyEntity> companyEntity = companyRepository.findById(company.getId());
        if (!companyEntity.isPresent()) {
            return;
        }
        companyEntity.get().setName(company.getName()).setFoundationDate(company.getFoundationDate()).setCost(company.getCost());
    }

    public void delete(Company company) {
        companyRepository.deleteById(company.getId());
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    public void truncate() {
        companyRepository.deleteAll();
    }

    public Company findCompanyById(Long id) {
        return convertToDto(companyRepository.findCompanyEntitiesById(id));
    }

    public List<Company> findAll() {
        return companyRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CompanyEntity getCompanyEntity(Company company){
        return convertToEntity(company);
    }

    public Company getCompanyDto(CompanyEntity companyEntity){
        return convertToDto(companyEntity);
    }

    private Company convertToDto(CompanyEntity companyEntity) {
        Company company = modelMapper.map(companyEntity, Company.class);
        company.setName(companyEntity.getName());
        company.setFoundationDate(companyEntity.getFoundationDate());
        company.setCost(companyEntity.getCost());
        return company;
    }

    private CompanyEntity convertToEntity(Company company) {
        CompanyEntity companyEntity = modelMapper.map(company, CompanyEntity.class);
        companyEntity.setName(company.getName());
        companyEntity.setFoundationDate(company.getFoundationDate());
        companyEntity.setCost(company.getCost());
        return companyEntity;
    }
}
