package com.example.project_ideas_demo.service;

import com.example.project_ideas_demo.model.command.CreateCommentCommand;
import com.example.project_ideas_demo.model.command.CreateIdeaCommand;
import com.example.project_ideas_demo.model.dto.CommentDto;
import com.example.project_ideas_demo.model.dto.IdeaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IdeaService {
    Page<IdeaDto> getAllIdeas(Pageable pageable);

    IdeaDto create(CreateIdeaCommand command);

    IdeaDto likeIdea(Long ideaId);

    CommentDto addComment(Long ideaId, CreateCommentCommand command);
}
