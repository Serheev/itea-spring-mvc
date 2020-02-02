package com.serheev.service;

import com.serheev.dto.Project;
import com.serheev.interceptor.SimpleLogger;
import com.serheev.model.ProjectEntity;
import com.serheev.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ModelMapper modelMapper;

    @Interceptors(SimpleLogger.class)
    public Project create(Project project) {
        return convertToDto(projectRepository.saveAndFlush(new ProjectEntity()
                        .setName(project.getName())
                        .setStarDate(project.getStarDate())
                        .setEndDate(project.getEndDate())
                        .setCost(project.getCost())
                        .setCompanyId(companyService.getCompanyEntity(project.getCompanyId()))
                )
        );
    }

    @Transactional
    public void update(Project project) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(project.getId());
        if (!projectEntity.isPresent()) {
            return;
        }
        projectEntity.get().setName(project.getName()).setStarDate(project.getStarDate()).setEndDate(project.getEndDate()).setCost(project.getCost());
    }

    public void delete(Project project) {
        projectRepository.deleteById(project.getId());
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public void truncate() {
        projectRepository.deleteAll();
    }

    public Project findProjectById(Long id) {
        return convertToDto(projectRepository.findProjectEntitiesById(id));
    }

    public List<Project> findAll() {
        return projectRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProjectEntity getProjectEntity(Project project) {
        return convertToEntity(project);
    }

    public Project getProjectDto(ProjectEntity projectEntity) {
        return convertToDto(projectEntity);
    }

    private Project convertToDto(ProjectEntity projectEntity) {
        Project project = modelMapper.map(projectEntity, Project.class);
        project.setName(projectEntity.getName());
        project.setStarDate(projectEntity.getStarDate());
        project.setEndDate(projectEntity.getEndDate());
        project.setCost(projectEntity.getCost());
        return project;
    }

    private ProjectEntity convertToEntity(Project project) {
        ProjectEntity projectEntity = modelMapper.map(project, ProjectEntity.class);
        projectEntity.setName(project.getName());
        projectEntity.setStarDate(project.getStarDate());
        projectEntity.setEndDate(project.getEndDate());
        projectEntity.setCost(project.getCost());
        return projectEntity;
    }
}
