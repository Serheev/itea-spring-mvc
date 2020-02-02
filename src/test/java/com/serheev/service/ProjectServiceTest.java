package com.serheev.service;

import com.serheev.configuration.DataSourceConfiguration;
import com.serheev.dto.Company;
import com.serheev.dto.Project;
import com.serheev.model.ProjectEntity;
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

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.util.Assert.notNull;

@DirtiesContext
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfiguration.class})
public class ProjectServiceTest {
    private static Logger log = Logger.getLogger(ProjectServiceTest.class);

    @Autowired
    private ProjectService projectService;
    private ModelMapper modelMapper = new ModelMapper();
    private Company company;
    private Project project;

    @Before
    public void setUp() {
        /** Pre-testing */
        notNull(projectService, "null");
        /** Predicate */
        company = new Company();
        company.setName("KMB");
        company.setFoundationDate(new Date(System.currentTimeMillis()));
        company.setCost((long) 12_000_000);

        project = new Project();
        project.setName("ITEA WebSite");
        project.setStarDate(new Date(System.currentTimeMillis()));
        project.setEndDate(new Date(System.currentTimeMillis() + 1000000));
        project.setCost((long) 1_000_000);
        project.setCompanyId(company);
    }

    @After
    public void cleanAfterTest() {
        projectService.truncate();
    }

    @Test
    public void mappingDtoToEntityIsSuccess() {
        /** Functionality */
        ProjectEntity projectEntity = modelMapper.map(project, ProjectEntity.class);
        /** Testing */
        assertEquals(project.getName(), projectEntity.getName());
        assertEquals(project.getStarDate(), projectEntity.getStarDate());
        assertEquals(project.getCost(), projectEntity.getCost());
        /** Logging */
        log.info(project.getName() + " = " + projectEntity.getName());
        log.info(project.getStarDate() + " = " + projectEntity.getStarDate());
        log.info(project.getEndDate() + " = " + projectEntity.getEndDate());
        log.info(project.getCost() + " = " + projectEntity.getCost());
    }

    @Test
    public void mappingEntityToDtoIsSuccess() {
        /** Predicate */
        ProjectEntity projectEntity = new ProjectEntity()
                .setName("Google WebSite")
                .setStarDate(new Date(System.currentTimeMillis()))
                .setCost((long) 2_000_000);
        /** Functionality */
        Project projectDto = modelMapper.map(projectEntity, Project.class);
        /** Testing */
        assertEquals(projectDto.getName(), projectEntity.getName());
        assertEquals(projectDto.getStarDate(), projectEntity.getStarDate());
        assertEquals(projectDto.getCost(), projectEntity.getCost());
        /** Logging */
        log.info(projectDto.getName() + " = " + projectEntity.getName());
        log.info(projectDto.getStarDate() + " = " + projectEntity.getStarDate());
        log.info(projectDto.getCost() + " = " + projectEntity.getCost());
    }

    @Test
    public void projectShouldBeCreatedAndPutInDataBase() {
        /** Predicate */
        projectService.create(project);
        /** Testing */
        assertEquals(1, projectService.findAll().size());
        /** Logging */
        projectService.findAll().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void projectShouldBeGetById() {
        /** Predicate */
        Project projectDto = projectService.create(project);
        /** Functionality and Testing*/
        assertEquals(project.getName(), projectService.findProjectById(projectDto.getId()).getName());
        /** Logging */
        log.info(projectService.findProjectById(projectDto.getId()));
    }

    @Test
    public void allProjectsShouldBeGetFromDataBase() {
        projectService.create(project);

        /** Functionality and Testing */
        assertEquals(1, projectService.findAll().size());
        /** Logging */
        projectService.findAll().stream().map(Objects::toString).forEach(log::info);
    }

    @Test
    public void projectFieldsShouldBeUpdate() {
        /** Predicate */
        Project projectDto = projectService.create(project);
        /** Functionality */
        projectDto.setName("Amazon WebSite");
        projectDto.setCost((long) 2_500_000);
        projectService.update(projectDto);
        /** Testing */
        assertNotEquals(project.getName(), projectService.findProjectById(projectDto.getId()).getName());
        assertEquals(projectDto.getName(), projectService.findProjectById(projectDto.getId()).getName());
        assertEquals(projectDto.getId(), projectService.findProjectById(projectDto.getId()).getId());
    }

    @Test
    public void projectFieldsShouldBeDeleteByid() {
        /** Predicate and Functionality */
        projectService.deleteById(projectService.create(project).getId());
        /** Testing */
        assertEquals(0, projectService.findAll().size());
    }

    @Test
    public void projectFieldsShouldBeDeleteByProjectDto() {
        /** Predicate and Functionality */
        projectService.delete(projectService.create(project));
        /** Testing */
        assertEquals(0, projectService.findAll().size());
    }
}