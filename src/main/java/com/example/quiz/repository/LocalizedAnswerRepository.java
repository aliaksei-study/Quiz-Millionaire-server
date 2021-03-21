package com.example.quiz.repository;

import com.example.quiz.model.LocalizedAnswer;
import org.springframework.data.repository.CrudRepository;

public interface LocalizedAnswerRepository extends CrudRepository<LocalizedAnswer, Long> {
}
