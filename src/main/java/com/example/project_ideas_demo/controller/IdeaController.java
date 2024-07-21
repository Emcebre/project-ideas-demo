package com.example.project_ideas_demo.controller;

import com.example.project_ideas_demo.model.command.CreateCommentCommand;
import com.example.project_ideas_demo.model.command.CreateIdeaCommand;
import com.example.project_ideas_demo.model.dto.CommentDto;
import com.example.project_ideas_demo.model.dto.IdeaDto;
import com.example.project_ideas_demo.service.IdeaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects/ideas")
@RequiredArgsConstructor
public class IdeaController {

    private final IdeaService ideaService;

    @GetMapping
    public Page<IdeaDto> getAllIdeas(Pageable pageable) {
        return ideaService.getAllIdeas(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<IdeaDto> createIdea(@RequestBody @Valid CreateIdeaCommand command) {
        IdeaDto ideaDto = ideaService.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ideaDto);
    }

    @PostMapping("/{ideaId}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<IdeaDto> likeIdea(@PathVariable Long ideaId) {
        IdeaDto ideaDto = ideaService.likeIdea(ideaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ideaDto);
    }

    @PostMapping("/{ideaId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentDto> addComment(@PathVariable Long ideaId, @RequestBody CreateCommentCommand command) {
        CommentDto commentDto = ideaService.addComment(ideaId, command);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }
}