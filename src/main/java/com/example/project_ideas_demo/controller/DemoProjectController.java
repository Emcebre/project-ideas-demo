package com.example.project_ideas_demo.controller;

import com.example.project_ideas_demo.model.DemoProject;
import com.example.project_ideas_demo.model.command.CreateDemoProjectCommand;
import com.example.project_ideas_demo.model.dto.DemoProjectDto;
import com.example.project_ideas_demo.service.DemoProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class DemoProjectController {

    private final DemoProjectService projectService;

    @GetMapping
    public List<DemoProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DemoProjectDto> createProject(@RequestBody @Valid CreateDemoProjectCommand command) {
        DemoProjectDto demoProjectDto = projectService.createProject(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(demoProjectDto);
    }

    @GetMapping("/{id}")
    public DemoProjectDto getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemoProjectDto> updateProject(@PathVariable Long id, @RequestBody DemoProject projectDetails) {
        DemoProjectDto demoProjectDto = projectService.updateProject(id, projectDetails);
        return ResponseEntity.ok(demoProjectDto);
    }
}