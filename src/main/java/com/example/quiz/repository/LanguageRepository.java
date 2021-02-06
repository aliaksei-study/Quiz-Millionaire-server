package com.example.quiz.repository;

import com.example.quiz.model.Language;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LanguageRepository extends CrudRepository<Language, Long> {

    Optional<Language> findLanguageByAbbreviation(String abbreviation);
}
