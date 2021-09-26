package com.example.quiz.repository;

import com.example.quiz.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAll();

    Optional<Category> findCategoryById(Long id);
}
