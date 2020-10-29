package com.example.quiz.repository;

import com.example.quiz.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAll();

//    @Query(value="SELECT * FROM User ORDER BY RAND() LIMIT 1", nativeQuery = true)
//    UserEntity findUser();
}
