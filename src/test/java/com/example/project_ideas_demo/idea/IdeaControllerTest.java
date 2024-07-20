package com.example.project_ideas_demo.idea;

import com.example.project_ideas_demo.model.command.CreateIdeaCommand;
import com.example.project_ideas_demo.model.dto.CommentDto;
import com.example.project_ideas_demo.model.dto.IdeaDto;
import com.example.project_ideas_demo.service.impl.IdeaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IdeaControllerTest {

    @MockBean
    private IdeaServiceImpl ideaServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllIdeas_ShouldReturnAllIdeas() throws Exception {
        // given
        IdeaDto ideaDto = new IdeaDto();
        when(ideaServiceImpl.getAllIdeas()).thenReturn(List.of(ideaDto));

        // when / then
        mockMvc.perform(get("/api/projects/ideas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(ideaServiceImpl).getAllIdeas();
    }

    @Test
    void createIdea_ShouldCreateAndReturnIdea() throws Exception {
        // given
        CreateIdeaCommand command = new CreateIdeaCommand();
        command.setTitle("Alfa");
        command.setDescription("Test");
        command.setAuthor("John");

        IdeaDto ideaDto = new IdeaDto();
        ideaDto.setId(1L);
        ideaDto.setTitle("Alfa");
        ideaDto.setDescription("Test");
        ideaDto.setAuthor("John");
        when(ideaServiceImpl.create(any())).thenReturn(ideaDto);

        // when / then
        mockMvc.perform(post("/api/projects/ideas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.title", is("Alfa")))
                .andExpect(jsonPath("$.description", is("Test")))
                .andExpect(jsonPath("$.author", is("John")));

        verify(ideaServiceImpl).create(any());
    }

    @Test
    void likeIdea_ShouldIncrementLikesAndReturnIdea() throws Exception {
        // given
        IdeaDto ideaDto = new IdeaDto();
        ideaDto.setId(1L);
        Long ideaId = ideaDto.getId();

        when(ideaServiceImpl.likeIdea(ideaId)).thenReturn(ideaDto);

        // when / then
        mockMvc.perform(post("/api/projects/ideas/{ideaId}/like", ideaId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        verify(ideaServiceImpl).likeIdea(ideaId);
    }

    @Test
    void addComment_ShouldAddCommentAndReturnCommentDto() throws Exception {
        // given
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        Long ideaId = commentDto.getId();

        when(ideaServiceImpl.addComment(anyLong(), any())).thenReturn(commentDto);

        // when / then
        mockMvc.perform(post("/api/projects/ideas/{ideaId}/comments", ideaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"authorName\": \"John\", \"content\": \"Great idea!\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        verify(ideaServiceImpl).addComment(anyLong(), any());
    }
}