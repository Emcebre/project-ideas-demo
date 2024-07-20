package com.example.project_ideas_demo.model.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateDemoProjectCommand {

    @NotNull(message = "NULL_VALUE")
    @Pattern(regexp = "[A-Z][a-z]{1,19}", message = "PATTERN_MISMATCH_{regexp}")
    private String name;

    @NotNull(message = "NULL_VALUE")
    @Size(max = 500, message = "DESCRIPTION_TOO_LONG")
    private String description;
}