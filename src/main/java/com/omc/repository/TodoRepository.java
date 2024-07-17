package com.omc.repository;

import com.omc.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByTitleContaining(String titleFilter, Pageable pageable);
    Page<Todo> findByUserUsername(String usernameFilter, Pageable pageable);
}
