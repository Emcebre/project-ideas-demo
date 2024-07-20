package com.example.project_ideas_demo.mapper;

import com.example.project_ideas_demo.model.Comment;
import com.example.project_ideas_demo.model.command.CreateCommentCommand;
import com.example.project_ideas_demo.model.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    Comment mapFromCommand(CreateCommentCommand command);

    CommentDto mapToDto(Comment comment);
}