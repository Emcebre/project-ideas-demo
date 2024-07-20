package com.example.project_ideas_demo.mapper;

import com.example.project_ideas_demo.model.DemoProject;
import com.example.project_ideas_demo.model.command.CreateDemoProjectCommand;
import com.example.project_ideas_demo.model.dto.DemoProjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DemoProjectMapper {

    @Mapping(target = "id", ignore = true)
    DemoProject mapFromCommand(CreateDemoProjectCommand command);

    DemoProjectDto mapToDto(DemoProject demoProject);
}