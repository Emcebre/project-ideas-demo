package com.example.project_ideas_demo.demoProject;

import com.example.project_ideas_demo.model.command.CreateDemoProjectCommand;
import com.example.project_ideas_demo.model.dto.DemoProjectDto;
import com.example.project_ideas_demo.service.impl.DemoProjectServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DemoProjectControllerTest {

    @MockBean
    private DemoProjectServiceImpl demoProjectServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAllProjects_ShouldReturnPagedProjects() throws Exception {
        // given
        DemoProjectDto projectDto1 = new DemoProjectDto();
        DemoProjectDto projectDto2 = new DemoProjectDto();
        List<DemoProjectDto> projectDtos = List.of(projectDto1, projectDto2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<DemoProjectDto> page = new PageImpl<>(projectDtos, pageable, projectDtos.size());

        when(demoProjectServiceImpl.getAllProjects(pageable)).thenReturn(page);

        // when / then
        mockMvc.perform(get("/api/projects?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(2)));

        verify(demoProjectServiceImpl).getAllProjects(pageable);
    }

    @Test
    void createProject_ShouldCreateAndReturnProject() throws Exception {
        // given
        CreateDemoProjectCommand command = new CreateDemoProjectCommand();
        command.setName("Alfa");
        command.setDescription("Test");

        DemoProjectDto projectDto = new DemoProjectDto();
        projectDto.setId(1L);
        projectDto.setName("Alfa");
        projectDto.setDescription("Test");

        when(demoProjectServiceImpl.createProject(any())).thenReturn(projectDto);

        // when / then
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("name", is("Alfa")))
                .andExpect(jsonPath("$.description", is("Test")));

        verify(demoProjectServiceImpl).createProject(any());
    }

    @Test
    void getProjectById_ShouldReturnProject() throws Exception {
        // given
        DemoProjectDto projectDto = new DemoProjectDto();
        projectDto.setId(1L);
        Long id = projectDto.getId();
        when(demoProjectServiceImpl.getProjectById(id)).thenReturn(projectDto);

        // when / then
        mockMvc.perform(get("/api/projects/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        verify(demoProjectServiceImpl).getProjectById(id);
    }

    @Test
    void updateProject_ShouldUpdateAndReturnProject() throws Exception {
        // given
        DemoProjectDto projectDto = new DemoProjectDto();
        projectDto.setId(1L);
        Long id = projectDto.getId();
        when(demoProjectServiceImpl.updateProject(anyLong(), any())).thenReturn(projectDto);

        // when / then
        mockMvc.perform(put("/api/projects/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Project\", \"description\": \"Updated Description\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        verify(demoProjectServiceImpl).updateProject(anyLong(), any());
    }
}