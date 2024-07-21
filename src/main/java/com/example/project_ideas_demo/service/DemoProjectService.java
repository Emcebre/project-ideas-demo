package com.example.project_ideas_demo.service;

import com.example.project_ideas_demo.model.DemoProject;
import com.example.project_ideas_demo.model.command.CreateDemoProjectCommand;
import com.example.project_ideas_demo.model.dto.DemoProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DemoProjectService {
    Page<DemoProjectDto> getAllProjects(Pageable pageable);

    DemoProjectDto createProject(CreateDemoProjectCommand command);

    DemoProjectDto getProjectById(Long id);

    DemoProjectDto updateProject(Long id, DemoProject projectDetails);
}