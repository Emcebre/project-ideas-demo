package com.example.project_ideas_demo.service.impl;

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
import com.example.project_ideas_demo.service.IdeaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final IdeaMapper ideaMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<IdeaDto> getAllIdeas() {
        return ideaRepository.findAll().stream()
                .map(ideaMapper::mapToDto)
                .toList();
    }

    @Override
    public IdeaDto create(CreateIdeaCommand command) {
        Idea idea = ideaRepository.save(ideaMapper.mapFromCommand(command));
        return ideaMapper.mapToDto(idea);
    }

    @Override
    @Transactional
    public IdeaDto likeIdea(Long ideaId) {
        Idea idea = ideaRepository.findWithLockingById(ideaId).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("Idea with id={0} not found: ", ideaId)));
        idea.setLikes(idea.getLikes() + 1);
        ideaRepository.save(idea);
        return ideaMapper.mapToDto(idea);
    }

    @Override
    public CommentDto addComment(Long ideaId, CreateCommentCommand command) {
        Idea idea = ideaRepository.findById(ideaId).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("Person with id={0} not found: ", ideaId)));
        Comment comment = commentRepository.save(commentMapper.mapFromCommand(command));
        comment.setIdea(idea);
        comment.setCreatedAt(LocalDateTime.now());
        return commentMapper.mapToDto(comment);
    }
}