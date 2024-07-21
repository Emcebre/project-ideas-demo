package com.example.project_ideas_demo.idea;

import com.example.project_ideas_demo.mapper.CommentMapper;
import com.example.project_ideas_demo.mapper.IdeaMapper;
import com.example.project_ideas_demo.model.Comment;
import com.example.project_ideas_demo.model.Idea;
import com.example.project_ideas_demo.model.command.CreateCommentCommand;
import com.example.project_ideas_demo.model.command.CreateIdeaCommand;
import com.example.project_ideas_demo.model.dto.CommentDto;
import com.example.project_ideas_demo.model.dto.IdeaDto;
import com.example.project_ideas_demo.repository.CommentRepository;
import com.example.project_ideas_demo.repository.IdeaRepository;
import com.example.project_ideas_demo.service.impl.IdeaServiceImpl;
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
public class IdeaServiceTest {

    @Mock
    private IdeaRepository ideaRepository;

    @Mock
    private IdeaMapper ideaMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private IdeaServiceImpl ideaService;

    @Test
    void getAllIdeas_ShouldReturnPagedIdeas() {
        // given
        Idea idea1 = new Idea();
        Idea idea2 = new Idea();
        List<Idea> ideas = List.of(idea1, idea2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Idea> page = new PageImpl<>(ideas, pageable, ideas.size());
        IdeaDto ideaDto1 = new IdeaDto();
        IdeaDto ideaDto2 = new IdeaDto();
        List<IdeaDto> ideaDtos = List.of(ideaDto1, ideaDto2);

        when(ideaRepository.findAll(pageable)).thenReturn(page);
        when(ideaMapper.mapToDto(idea1)).thenReturn(ideaDto1);
        when(ideaMapper.mapToDto(idea2)).thenReturn(ideaDto2);

        // when
        Page<IdeaDto> result = ideaService.getAllIdeas(pageable);

        // then
        assertEquals(2, result.getContent().size());
        assertEquals(ideaDtos, result.getContent());
        assertEquals(2, result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(2, result.getSize());
        verify(ideaRepository).findAll(pageable);
    }

    @Test
    void createIdea_ShouldCreateAndReturnIdea() {
        // given
        CreateIdeaCommand command = new CreateIdeaCommand();
        Idea idea = new Idea();
        when(ideaMapper.mapFromCommand(command)).thenReturn(idea);
        when(ideaRepository.save(idea)).thenReturn(idea);
        IdeaDto ideaDto = new IdeaDto();
        when(ideaMapper.mapToDto(idea)).thenReturn(ideaDto);

        // when
        IdeaDto result = ideaService.create(command);

        // then
        assertEquals(ideaDto, result);
        verify(ideaRepository).save(idea);
    }

    @Test
    void likeIdea_ShouldIncrementLikesAndReturnIdea() {
        // given
        Long ideaId = 1L;
        Idea idea = new Idea();
        idea.setLikes(0);
        when(ideaRepository.findWithLockingById(ideaId)).thenReturn(Optional.of(idea));
        when(ideaRepository.save(idea)).thenReturn(idea);
        IdeaDto ideaDto = new IdeaDto();
        when(ideaMapper.mapToDto(idea)).thenReturn(ideaDto);

        // when
        IdeaDto result = ideaService.likeIdea(ideaId);

        // then
        assertEquals(1, idea.getLikes());
        assertEquals(ideaDto, result);
        verify(ideaRepository).save(idea);
    }

    @Test
    void likeIdea_ShouldThrowException_WhenNotFound() {
        // given
        Long ideaId = 1L;
        when(ideaRepository.findWithLockingById(ideaId)).thenReturn(Optional.empty());

        // when / then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            ideaService.likeIdea(ideaId);
        });

        assertTrue(thrown.getMessage().contains("Idea with id=" + ideaId + " not found"));
        verify(ideaRepository).findWithLockingById(ideaId);
    }

    @Test
    void addComment_ShouldAddCommentAndReturnCommentDto() {
        // given
        Long ideaId = 1L;
        Idea idea = new Idea();
        when(ideaRepository.findById(ideaId)).thenReturn(Optional.of(idea));
        Comment comment = new Comment();
        CreateCommentCommand command = new CreateCommentCommand();
        when(commentMapper.mapFromCommand(command)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        CommentDto commentDto = new CommentDto();
        when(commentMapper.mapToDto(comment)).thenReturn(commentDto);

        // when
        CommentDto result = ideaService.addComment(ideaId, command);

        // then
        assertEquals(commentDto, result);
        assertEquals(idea, comment.getIdea());
        verify(commentRepository).save(comment);
    }

    @Test
    void addComment_ShouldThrowException_WhenIdeaNotFound() {
        // given
        Long ideaId = 1L;
        CreateCommentCommand command = new CreateCommentCommand();
        when(ideaRepository.findById(ideaId)).thenReturn(Optional.empty());

        // when / then
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            ideaService.addComment(ideaId, command);
        });

        assertTrue(thrown.getMessage().contains("Person with id=" + ideaId + " not found"));
        verify(ideaRepository).findById(ideaId);
    }
}