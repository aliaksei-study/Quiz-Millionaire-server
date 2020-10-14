package com.example.quiz.repository;

import com.example.quiz.model.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
