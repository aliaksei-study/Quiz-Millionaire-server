package com.example.quiz.service;

import com.example.quiz.dto.AdminQuestionDto;
import com.example.quiz.dto.QuestionDto;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Question;
import com.example.quiz.model.enumeration.Difficulty;
import com.example.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@EnableScheduling
public class QuestionServiceImpl implements IQuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void updateQuestion(QuestionDto questionDto, Long id) {

    }

    @Override
    public void deleteQuestion(Long id) {

    }

    @Override
    public Question getQuestionById(Long questionId) throws QuestionNotFoundException {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new QuestionNotFoundException("question with id: " + questionId + " is not exist"));
    }

    @Override
    public List<AdminQuestionDto> getQuestions() {
        return Mapper.mapAll(questionRepository.findAll(), AdminQuestionDto.class);
    }

    @Override
    public QuestionDto saveQuestion(QuestionDto questionDto) {
        Question question = Mapper.map(questionDto, Question.class);
        question.setDifficulty(Difficulty.MEDIUM);
        question.setIsTemporal(true);
        question = questionRepository.save(question);
        return Mapper.map(question, QuestionDto.class);
    }

    @Override
    public List<QuestionDto> getFifteenRandomQuestions() {
        final int numberOfEasyQuestions = 5;
        final int numberOfMediumQuestions = 4;
        final int numberOfHardQuestions = 3;
        final int numberOfNightMareQuestions = 2;
        List<Question> randomEasyQuestions = questionRepository.findNthRandomQuestionsByDifficulty(Difficulty.EASY,
                false, PageRequest.of(0, numberOfEasyQuestions));
        List<Question> randomTemporalQuestions = questionRepository.findNthRandomQuestionsByDifficulty(Difficulty.MEDIUM,
                true, PageRequest.of(0, 1));
        List<Question> randomMediumQuestions = questionRepository.findNthRandomQuestionsByDifficulty(Difficulty.MEDIUM,
                false, PageRequest.of(0, randomTemporalQuestions.size() == 0 ? numberOfEasyQuestions :
                        numberOfMediumQuestions));
        List<Question> randomHardQuestions = questionRepository.findNthRandomQuestionsByDifficulty(Difficulty.HARD,
                false, PageRequest.of(0, numberOfHardQuestions));
        List<Question> randomNightMareQuestions = questionRepository.findNthRandomQuestionsByDifficulty(Difficulty.NIGHTMARE,
                false, PageRequest.of(0, numberOfNightMareQuestions));
        return Stream.of(randomEasyQuestions, randomTemporalQuestions, randomMediumQuestions, randomHardQuestions,
                randomNightMareQuestions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .stream()
                .map(question -> Mapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 5000)
    @Override
    public void processPlayerQuestions() {
        System.out.println("fucking Fuck");
    }
}
