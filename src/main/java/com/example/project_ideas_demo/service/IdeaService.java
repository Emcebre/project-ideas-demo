package com.example.project_ideas_demo.service;

import com.example.project_ideas_demo.model.command.CreateCommentCommand;
import com.example.project_ideas_demo.model.command.CreateIdeaCommand;
import com.example.project_ideas_demo.model.dto.CommentDto;
import com.example.project_ideas_demo.model.dto.IdeaDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IdeaService {
    List<IdeaDto> getAllIdeas();

    IdeaDto create(CreateIdeaCommand command);

    @Transactional
    IdeaDto likeIdea(Long ideaId);

    CommentDto addComment(Long ideaId, CreateCommentCommand command);
}
