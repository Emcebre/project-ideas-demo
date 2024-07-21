package com.example.project_ideas_demo.service;

import com.example.project_ideas_demo.model.DemoProject;
import com.example.project_ideas_demo.model.command.CreateDemoProjectCommand;
import com.example.project_ideas_demo.model.dto.DemoProjectDto;

import java.util.List;

public interface DemoProjectService {
    List<DemoProjectDto> getAllProjects();

    DemoProjectDto createProject(CreateDemoProjectCommand command);

    DemoProjectDto getProjectById(Long id);

    DemoProjectDto updateProject(Long id, DemoProject projectDetails);
}