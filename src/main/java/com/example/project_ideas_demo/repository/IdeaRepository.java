package com.example.project_ideas_demo.repository;

import com.example.project_ideas_demo.model.Idea;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface IdeaRepository extends JpaRepository<Idea, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Idea> findWithLockingById(Long id);
}
