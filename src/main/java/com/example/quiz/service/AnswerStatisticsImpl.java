package com.example.quiz.service;

import com.example.quiz.dto.AnswerStatisticsDto;
import com.example.quiz.exception.AnswerNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.Answer;
import com.example.quiz.model.AnswerStatistics;
import com.example.quiz.model.Player;
import com.example.quiz.model.Question;
import com.example.quiz.repository.AnswerStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnswerStatisticsImpl implements IAnswerStatistics {
    private AnswerStatisticsRepository answerStatisticsRepository;

    private IQuestionService questionService;
    private IAnswerService answerService;

    @Autowired
    public AnswerStatisticsImpl(AnswerStatisticsRepository answerStatisticsRepository, IQuestionService questionService,
                                IAnswerService answerService) {
        this.answerStatisticsRepository = answerStatisticsRepository;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Override
    public AnswerStatisticsDto savePlayerQuestionAnswer(Player player, Long questionId, Long answerId)
            throws AnswerNotFoundException, QuestionNotFoundException {
        Optional<AnswerStatistics> answerStatisticsOptional;
        Question question = questionService.getQuestionById(questionId);
        Answer answer = answerService.getAnswerById(answerId);
        answerStatisticsOptional = answerStatisticsRepository.findAnswerStatisticsByAnswerAndQuestionAndPlayer(answer,
                question, player);
        if(answerStatisticsOptional.isEmpty()) {
            return Mapper.map(answerStatisticsRepository.save
                    (new AnswerStatistics(question, answer, player)), AnswerStatisticsDto.class);
        } else {
            return Mapper.map(answerStatisticsOptional.get(), AnswerStatisticsDto.class);
        }
    }

    @Override
    public Map<Long, Integer> getPlayerAnswersHistogram(Long questionId) throws QuestionNotFoundException {
        Question question = questionService.getQuestionById(questionId);
        List<Answer> answers = answerStatisticsRepository.findAnswerStatisticsByQuestion(question).stream()
                .map(AnswerStatistics::getAnswer)
                .collect(Collectors.toList());
        Map<Long, Integer> answersHistogram = new HashMap<>();
        for(Answer answer: answers) {
            Integer numberOfOccurrences = answersHistogram.get(answer.getId());
            answersHistogram.put(answer.getId(), numberOfOccurrences == null ? 1 : numberOfOccurrences + 1);
        }
        return answersHistogram;
    }
}
