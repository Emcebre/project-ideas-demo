package com.example.project_ideas_demo.repository;

import com.example.project_ideas_demo.model.DemoProject;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface DemoProjectRepository extends JpaRepository<DemoProject, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<DemoProject> findWithLockingById(Long id);
}
