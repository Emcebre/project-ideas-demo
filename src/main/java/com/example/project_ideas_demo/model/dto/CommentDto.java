package com.example.project_ideas_demo.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Long id;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;
}