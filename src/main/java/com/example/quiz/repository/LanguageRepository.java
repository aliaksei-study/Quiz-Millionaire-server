package com.example.quiz.repository;

import com.example.quiz.model.Language;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, Long> {
}
