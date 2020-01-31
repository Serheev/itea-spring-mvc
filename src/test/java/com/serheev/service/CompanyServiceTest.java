package com.serheev.service;

import com.serheev.configuration.DataSourceConfiguration;
import com.serheev.dto.Company;
import com.serheev.model.CompanyEntity;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.util.Assert.notNull;

@DirtiesContext
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfiguration.class})
public class CompanyServiceTest {
    private static Logger log = Logger.getLogger(CompanyServiceTest.class);

    @Autowired
    private CompanyService companyService;

    private ModelMapper modelMapper = new ModelMapper();
    private Company company;

    @Before
    public void setUp() {
        /** Pre-testing */
        companyService.truncate();
        notNull(companyService, "null");
        /** Predicate */
        company = new Company();
        company.setName("Microsoft");
        company.setFoundationDate(new Date(System.currentTimeMillis()));
        company.setCost((long) 1_000_000);
    }

    @Test
    public void mappingDtoToEntityIsSuccess() {
        /** Functionality */
        CompanyEntity companyEntity = modelMapper.map(company, CompanyEntity.class);
        /** Testing */
        assertEquals(company.getName(), companyEntity.getName());
        assertEquals(company.getFoundationDate(), companyEntity.getFoundationDate());
        assertEquals(company.getCost(), companyEntity.getCost());
        /** Logging */
        log.info(company.getName() + " = " + companyEntity.getName());
        log.info(company.getFoundationDate() + " = " + companyEntity.getFoundationDate());
        log.info(company.getCost() + " = " + companyEntity.getCost());
    }

    @Test
    public void mappingEntityToDtoIsSuccess() {
        /** Predicate */
        CompanyEntity companyEntity = new CompanyEntity()
                .setName("Oracle")
                .setFoundationDate(new Date(System.currentTimeMillis()))
                .setCost((long) 2_000_000);
        /** Functionality */
        Company companyDto = modelMapper.map(companyEntity, Company.class);
        /** Testing */
        assertEquals(companyDto.getName(), companyEntity.getName());
        assertEquals(companyDto.getFoundationDate(), companyEntity.getFoundationDate());
        assertEquals(companyDto.getCost(), companyEntity.getCost());
        /** Logging */
        log.info(companyDto.getName() + " = " + companyEntity.getName());
        log.info(companyDto.getFoundationDate() + " = " + companyEntity.getFoundationDate());
        log.info(companyDto.getCost() + " = " + companyEntity.getCost());
    }

    @Test
    public void companyShouldBeCreatedAndPutInDataBase() {
        /** Predicate */
        companyService.create(company);
        /** Testing */
        assertEquals(1, companyService.findAll().size());
        /** Logging */
        companyService.findAll().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void companyShouldBeGetById() {
        /** Predicate */
        Company companyDto = companyService.create(company);
        /** Functionality and Testing*/
        assertEquals(company.getName(), companyService.findCompanyById(companyDto.getId()).getName());
        /** Logging */
        log.info(companyService.findCompanyById(companyDto.getId()));
    }

    @Test
    public void allCompanysShouldBeGetFromDataBase() {
        companyService.create(company);

        /** Functionality and Testing */
        assertEquals(1, companyService.findAll().size());
        /** Logging */
        companyService.findAll().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void companyFieldsShouldBeUpdate() {
        /** Predicate */
        Company companyDto = companyService.create(company);
        /** Functionality */
        companyDto.setName("Amazon");
        companyDto.setCost((long) 2_500_000);
        companyService.update(companyDto);
        /** Testing */
        assertNotEquals(company.getName(), companyService.findCompanyById(companyDto.getId()).getName());
        assertEquals(companyDto.getName(), companyService.findCompanyById(companyDto.getId()).getName());
        assertEquals(companyDto.getId(), companyService.findCompanyById(companyDto.getId()).getId());
    }

    @Test
    public void companyFieldsShouldBeDeleteByid() {
        /** Predicate and Functionality */
        companyService.deleteById(companyService.create(company).getId());
        /** Testing */
        assertEquals(0, companyService.findAll().size());
    }

    @Test
    public void companyFieldsShouldBeDeleteByCompanyDto() {
        /** Predicate and Functionality */
        companyService.delete(companyService.create(company));
        /** Testing */
        assertEquals(0, companyService.findAll().size());
    }
}