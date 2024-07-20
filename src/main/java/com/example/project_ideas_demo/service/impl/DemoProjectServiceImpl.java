package com.example.project_ideas_demo.service.impl;

import com.example.project_ideas_demo.mapper.DemoProjectMapper;
import com.example.project_ideas_demo.model.DemoProject;
import com.example.project_ideas_demo.model.command.CreateDemoProjectCommand;
import com.example.project_ideas_demo.model.dto.DemoProjectDto;
import com.example.project_ideas_demo.repository.DemoProjectRepository;
import com.example.project_ideas_demo.service.DemoProjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoProjectServiceImpl implements DemoProjectService {

    private final DemoProjectRepository projectRepository;
    private final DemoProjectMapper demoProjectMapper;
    private final DemoProjectRepository demoProjectRepository;

    @Override public List<DemoProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(demoProjectMapper::mapToDto)
                .toList();
    }

    @Override public DemoProjectDto createProject(CreateDemoProjectCommand command) {
        DemoProject demoProject = demoProjectRepository.save(demoProjectMapper.mapFromCommand(command));
        return demoProjectMapper.mapToDto(demoProject);
    }

    @Override public DemoProjectDto getProjectById(Long id) {
        DemoProject demoProject = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("Project with id={0} not found: ", id)));
        return demoProjectMapper.mapToDto(demoProject);
    }

    @Override public DemoProjectDto updateProject(Long id, DemoProject projectDetails) {
        DemoProject project = demoProjectRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                                .format("Project with id={0} not found: ", id)));
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());

        DemoProject updatedProject = demoProjectRepository.save(project);
        return demoProjectMapper.mapToDto(updatedProject);
    }
}