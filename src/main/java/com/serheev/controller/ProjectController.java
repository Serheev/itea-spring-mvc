package com.serheev.controller;

import com.serheev.dto.Project;

import com.serheev.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/project/test")
    public String test() {
        return "Success!";
    }

    @PostMapping(value = "/project/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody Project project) {
        return projectService.create(project);
    }

    @GetMapping(value = "/project/list")
    public List<Project> getProjects() {
        return projectService.findAll();
    }

    @GetMapping(value = "/project/{id}")
    public Project getProject(@PathVariable("id") Long id) {
        return projectService.findProjectById(id);
    }

    @PutMapping(value = "/project/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProject(@RequestBody Project project, @PathVariable("id") Long id){
        projectService.update(projectService.findProjectById(id));
    }

    @DeleteMapping(value = "/project/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable("id") Long id){
        projectService.deleteById(id);
    }

}
