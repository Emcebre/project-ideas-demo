package com.example.project_ideas_demo.service.impl;

import com.example.project_ideas_demo.mapper.DemoProjectMapper;
import com.example.project_ideas_demo.model.DemoProject;
import com.example.project_ideas_demo.model.command.CreateDemoProjectCommand;
import com.example.project_ideas_demo.model.command.UpdatedDemoProjectCommand;
import com.example.project_ideas_demo.model.dto.DemoProjectDto;
import com.example.project_ideas_demo.repository.DemoProjectRepository;
import com.example.project_ideas_demo.service.DemoProjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class DemoProjectServiceImpl implements DemoProjectService {

    private final DemoProjectMapper demoProjectMapper;
    private final DemoProjectRepository demoProjectRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<DemoProjectDto> getAllProjects(Pageable pageable) {
        return demoProjectRepository.findAll(pageable)
                .map(demoProjectMapper::mapToDto);
    }

    @Override
    public DemoProjectDto createProject(CreateDemoProjectCommand command) {
        DemoProject demoProject = demoProjectRepository.save(demoProjectMapper.mapFromCommand(command));
        return demoProjectMapper.mapToDto(demoProject);
    }

    @Override
    @Transactional(readOnly = true)
    public DemoProjectDto getProjectById(Long id) {
        DemoProject demoProject = demoProjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("Project with id={0} not found: ", id)));
        return demoProjectMapper.mapToDto(demoProject);
    }

    @Override
    @Transactional
    public DemoProjectDto updateProject(Long id, UpdatedDemoProjectCommand updatedDemoProjectCommand) {
        DemoProject project = demoProjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Project with id={0} not found: ", id)));

        project.setName(updatedDemoProjectCommand.getName());
        project.setDescription(updatedDemoProjectCommand.getDescription());

        DemoProject updatedProject = demoProjectRepository.save(project);
        return demoProjectMapper.mapToDto(updatedProject);
    }
}