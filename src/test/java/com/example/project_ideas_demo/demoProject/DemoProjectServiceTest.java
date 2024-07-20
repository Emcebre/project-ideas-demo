package com.example.project_ideas_demo.demoProject;

import com.example.project_ideas_demo.mapper.DemoProjectMapper;
import com.example.project_ideas_demo.model.DemoProject;
import com.example.project_ideas_demo.model.command.CreateDemoProjectCommand;
import com.example.project_ideas_demo.model.dto.DemoProjectDto;
import com.example.project_ideas_demo.repository.DemoProjectRepository;
import com.example.project_ideas_demo.service.impl.DemoProjectServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void getAllProjects_ShouldReturnAllProjects() {
        // given
        DemoProject project = new DemoProject();
        when(projectRepository.findAll()).thenReturn(List.of(project));
        DemoProjectDto projectDto = new DemoProjectDto();
        when(demoProjectMapper.mapToDto(project)).thenReturn(projectDto);

        // when
        List<DemoProjectDto> result = projectService.getAllProjects();

        // then
        assertEquals(1, result.size());
        assertEquals(projectDto, result.get(0));
        verify(projectRepository).findAll();
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
        DemoProject projectDetails = new DemoProject();
        projectDetails.setName("New Name");
        projectDetails.setDescription("New Description");
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));
        when(projectRepository.save(project)).thenReturn(project);
        DemoProjectDto projectDto = new DemoProjectDto();
        when(demoProjectMapper.mapToDto(project)).thenReturn(projectDto);

        // when
        DemoProjectDto result = projectService.updateProject(id, projectDetails);

        // then
        assertEquals(projectDto, result);
        verify(projectRepository).save(project);
    }

    @Test
    void updateProject_ShouldThrowException_WhenNotFound() {
        // given
        Long id = 1L;
        DemoProject projectDetails = new DemoProject();
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        // when / then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            projectService.updateProject(id, projectDetails);
        });

        assertTrue(thrown.getMessage().contains("Project with id=" + id + " not found"));
        verify(projectRepository).findById(id);
    }
}