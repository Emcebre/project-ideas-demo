package com.example.project_ideas_demo.model.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCommentCommand {

    @NotNull(message = "NULL_VALUE")
    @Pattern(regexp = "[A-Z][a-z]{1,19}", message = "PATTERN_MISMATCH_{regexp}")
    private String authorName;

    @NotNull(message = "NULL_VALUE")
    @Size(max = 200, message = "DESCRIPTION_TOO_LONG")
    private String content;
}