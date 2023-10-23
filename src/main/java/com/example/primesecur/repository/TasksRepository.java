package com.example.primesecur.repository;

import com.example.primesecur.model.Tasks;
import com.example.primesecur.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    Tasks findByIdAndDateGreaterThan(Long id, LocalDate date);

    @EntityGraph(attributePaths = "user")
    Optional<Tasks> findById(Long id);
    List<Tasks> findByUser(User user);
}
