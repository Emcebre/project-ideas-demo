package com.example.project_ideas_demo.model.dto;

import lombok.Data;

@Data
public class IdeaDto {

    private Long id;
    private String title;
    private String description;
    private String author;
    private String graphicDesignerName;
    private int likes;
    private String imageUrl;
}