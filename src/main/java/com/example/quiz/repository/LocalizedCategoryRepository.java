package com.example.quiz.repository;

import com.example.quiz.model.LocalizedCategory;
import org.springframework.data.repository.CrudRepository;

public interface LocalizedCategoryRepository extends CrudRepository<LocalizedCategory, Long> {
}
