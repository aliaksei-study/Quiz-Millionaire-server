package com.example.quiz.repository;

import com.example.quiz.model.LocalizedQuestion;
import org.springframework.data.repository.CrudRepository;

public interface LocalizedQuestionRepository extends CrudRepository<LocalizedQuestion, Long> {
}
