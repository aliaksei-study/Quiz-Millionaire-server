package com.example.quiz.repository;

import com.example.quiz.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    Set<Question> findAll();
}
