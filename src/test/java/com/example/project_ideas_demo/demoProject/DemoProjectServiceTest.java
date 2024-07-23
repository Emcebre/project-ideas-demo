package com.example.project_ideas_demo.demoProject;

import com.example.project_ideas_demo.mapper.DemoProjectMapper;
import com.example.project_ideas_demo.model.DemoProject;
import com.example.project_ideas_demo.model.command.CreateDemoProjectCommand;
import com.example.project_ideas_demo.model.command.UpdatedDemoProjectCommand;
import com.example.project_ideas_demo.model.dto.DemoProjectDto;
import com.example.project_ideas_demo.repository.DemoProjectRepository;
import com.example.project_ideas_demo.service.impl.DemoProjectServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DemoProjectServiceTest {

    @Mock
    private DemoProjectRepository projectRepository;

    @Mock
    private DemoProjectMapper demoProjectMapper;

    @InjectMocks
    private DemoProjectServiceImpl projectService;

    @Test
    void getAllProjects_ShouldReturnPagedProjects() {
        // given
        DemoProject project1 = new DemoProject();
        DemoProject project2 = new DemoProject();
        List<DemoProject> projects = List.of(project1, project2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<DemoProject> page = new PageImpl<>(projects, pageable, projects.size());
        DemoProjectDto projectDto1 = new DemoProjectDto();
        DemoProjectDto projectDto2 = new DemoProjectDto();
        List<DemoProjectDto> projectDtos = List.of(projectDto1, projectDto2);

        when(projectRepository.findAll(pageable)).thenReturn(page);
        when(demoProjectMapper.mapToDto(project1)).thenReturn(projectDto1);
        when(demoProjectMapper.mapToDto(project2)).thenReturn(projectDto2);

        // when
        Page<DemoProjectDto> result = projectService.getAllProjects(pageable);

        // then
        assertEquals(2, result.getContent().size());
        assertEquals(projectDtos, result.getContent());
        assertEquals(2, result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(2, result.getSize());
        verify(projectRepository).findAll(pageable);
    }

    @Test
    void createProject_ShouldCreateAndReturnProject() {
        // given
        CreateDemoProjectCommand command = new CreateDemoProjectCommand();
        DemoProject project = new DemoProject();
        when(demoProjectMapper.mapFromCommand(command)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        DemoProjectDto projectDto = new DemoProjectDto();
        when(demoProjectMapper.mapToDto(project)).thenReturn(projectDto);

        // when
        DemoProjectDto result = projectService.createProject(command);

        // then
        assertEquals(projectDto, result);
        verify(projectRepository).save(project);
    }

    @Test
    void getProjectById_ShouldReturnProject_WhenFound() {
        // given
        Long id = 1L;
        DemoProject project = new DemoProject();
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));
        DemoProjectDto projectDto = new DemoProjectDto();
        when(demoProjectMapper.mapToDto(project)).thenReturn(projectDto);

        // when
        DemoProjectDto result = projectService.getProjectById(id);

        // then
        assertEquals(projectDto, result);
        verify(projectRepository).findById(id);
    }

    @Test
    void getProjectById_ShouldThrowException_WhenNotFound() {
        // given
        Long id = 1L;
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        // when / then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            projectService.getProjectById(id);
        });

        assertTrue(thrown.getMessage().contains("Project with id=" + id + " not found"));
        verify(projectRepository).findById(id);
    }

    @Test
    void updateProject_ShouldUpdateAndReturnProject() {
        // given
        Long id = 1L;
        DemoProject project = new DemoProject();
        project.setVersion(1L);

        UpdatedDemoProjectCommand updateCommand = new UpdatedDemoProjectCommand();
        updateCommand.setName("New Name");
        updateCommand.setDescription("New Description");

        when(projectRepository.findById(id)).thenReturn(Optional.of(project));
        when(projectRepository.save(project)).thenReturn(project);
        DemoProjectDto projectDto = new DemoProjectDto();
        when(demoProjectMapper.mapToDto(project)).thenReturn(projectDto);

        // when
        DemoProjectDto result = projectService.updateProject(id, updateCommand);

        // then
        assertEquals(projectDto, result);
        verify(projectRepository).findById(id);
        verify(projectRepository).save(project);
        assertEquals("New Name", project.getName());
        assertEquals("New Description", project.getDescription());
    }

    @Test
    void updateProject_ShouldThrowException_WhenNotFound() {
        // given
        Long id = 1L;
        UpdatedDemoProjectCommand updateCommand = new UpdatedDemoProjectCommand();
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        // when / then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            projectService.updateProject(id, updateCommand);
        });

        assertTrue(thrown.getMessage().contains("Project with id=" + id + " not found"));
        verify(projectRepository).findById(id);
    }
}