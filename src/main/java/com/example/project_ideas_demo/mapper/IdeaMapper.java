package com.example.project_ideas_demo.mapper;

import com.example.project_ideas_demo.model.Idea;
import com.example.project_ideas_demo.model.command.CreateIdeaCommand;
import com.example.project_ideas_demo.model.dto.IdeaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IdeaMapper {

    @Mapping(target = "id", ignore = true)
    Idea mapFromCommand(CreateIdeaCommand command);

    IdeaDto mapToDto(Idea idea);
}