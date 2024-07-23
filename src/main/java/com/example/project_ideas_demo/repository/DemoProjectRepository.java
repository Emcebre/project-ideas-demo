package com.example.project_ideas_demo.repository;

import com.example.project_ideas_demo.model.DemoProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoProjectRepository extends JpaRepository<DemoProject, Long> {

}
