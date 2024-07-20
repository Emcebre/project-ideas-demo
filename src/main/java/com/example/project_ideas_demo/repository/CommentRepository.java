package com.example.project_ideas_demo.repository;

import com.example.project_ideas_demo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
