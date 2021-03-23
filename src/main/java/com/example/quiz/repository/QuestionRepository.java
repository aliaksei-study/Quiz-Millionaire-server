package com.example.quiz.repository;

import com.example.quiz.model.Category;
import com.example.quiz.model.Question;
import com.example.quiz.model.enumeration.Difficulty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    List<Question> findAll();

    @Query(value="select q from Question q INNER join q.questionTextTranslates lq INNER JOIN lq.language l where l.abbreviation = :language and q.difficulty = :difficulty and q.isTemporal = :isTemporal " +
            "ORDER BY function('RAND')")
    List<Question> findNthRandomQuestionsByDifficultyAndLanguage(@Param("difficulty")Difficulty difficulty,
                                                                 @Param("isTemporal")Boolean isTemporal,
                                                                 @Param("language") String language,
                                                                 Pageable pageable);

    List<Question> findAllByIsTemporal(Boolean isTemporal);

    List<Question> findAllByCategory(Category category);

    List<Question> findAllByDislikedQuestionPlayersIsNotNullAndId(Long id);

    List<Question> findAllByLikedQuestionPlayersIsNotNullAndId(Long id);
}
