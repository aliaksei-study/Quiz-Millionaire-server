package com.example.quiz.service;

import com.example.quiz.dto.*;
import com.example.quiz.exception.AnswerNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.*;
import com.example.quiz.repository.AnswerStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnswerStatisticsImpl implements IAnswerStatistics {
    private final AnswerStatisticsRepository answerStatisticsRepository;

    private final IQuestionService questionService;
    private final IAnswerService answerService;
    private final ILanguageService languageService;

    @Autowired
    public AnswerStatisticsImpl(AnswerStatisticsRepository answerStatisticsRepository, IQuestionService questionService,
                                IAnswerService answerService, ILanguageService languageService) {
        this.answerStatisticsRepository = answerStatisticsRepository;
        this.questionService = questionService;
        this.answerService = answerService;
        this.languageService = languageService;
    }

    @Override
    public AnswerStatisticsDto savePlayerQuestionAnswer(Player player,
                                                        Long questionId,
                                                        Long answerId,
                                                        String languageAbbrev,
                                                        String language)
            throws AnswerNotFoundException, QuestionNotFoundException {
        Optional<Language> languageFromDb = languageService.findLanguageByAbbreviation(languageAbbrev);
        Language persistedLanguage = languageFromDb
                .orElseGet(() -> Mapper.map(languageService.saveLanguage(new LanguageDto(null, language, languageAbbrev)),
                        Language.class));

        Optional<AnswerStatistics> answerStatisticsOptional;
        Question question = questionService.getQuestionById(questionId);
        Answer answer = answerService.getAnswerById(answerId);

        answerStatisticsOptional = answerStatisticsRepository.findAnswerStatisticsByAnswerAndQuestionAndPlayer(answer,
                question, player);
        if (answerStatisticsOptional.isEmpty()) {
            return Mapper.map(answerStatisticsRepository.save
                    (new AnswerStatistics(question, answer, persistedLanguage, player)), AnswerStatisticsDto.class);
        } else {
            AnswerStatistics answerStatistics = answerStatisticsOptional.get();
            answerStatistics.setLanguage(persistedLanguage);
            return Mapper.map(answerStatistics, AnswerStatisticsDto.class);
        }
    }

    @Override
    public Map<Long, Integer> getPlayerAnswersHistogram(Long questionId) throws QuestionNotFoundException {
        Question question = questionService.getQuestionById(questionId);
        List<Answer> answers = answerStatisticsRepository.findAnswerStatisticsByQuestion(question).stream()
                .map(AnswerStatistics::getAnswer)
                .collect(Collectors.toList());
        Map<Long, Integer> answersHistogram = new HashMap<>();
        for (Answer answer : answers) {
            Integer numberOfOccurrences = answersHistogram.get(answer.getId());
            answersHistogram.put(answer.getId(), numberOfOccurrences == null ? 1 : numberOfOccurrences + 1);
        }
        return answersHistogram;
    }

    @Override
    public List<QuestionAnswersStatisticsDto> getQuestionAnswersHistograms() {
        QuestionAnswersStatisticsDto mapStats;
        List<AnswerStatistics> statistics = answerStatisticsRepository.findAll();
        Map<Long, QuestionAnswersStatisticsDto> questionAnswers = new HashMap<>();
        for (AnswerStatistics answerStatistics : statistics) {
            mapStats = questionAnswers.get(answerStatistics.getQuestion().getId());
            questionAnswers.put(answerStatistics.getQuestion().getId(), mapStats == null ?
                    createInitialQuestionAnswerStatisticsInstance(answerStatistics)
                    : changeNumberOfAnswersInQuestionAnswersStatistics(mapStats, answerStatistics.getAnswer()));
        }
        return new ArrayList<>(questionAnswers.values());
    }

    @Override
    public void deleteAnswerStatisticsByPlayer(Player player) {
        answerStatisticsRepository.deleteAll(answerStatisticsRepository.findAnswerStatisticsByPlayer(player));
    }

    public QuestionAnswersStatisticsDto changeNumberOfAnswersInQuestionAnswersStatistics(
            QuestionAnswersStatisticsDto changedStats, Answer answer) {
        changedStats.setAnswerHistograms(changedStats.getAnswerHistograms()
                .stream().peek((item) -> {
                    if (item.getAnswer().getId().equals(answer.getId())) {
                        item.setNumberOfAnswers(item.getNumberOfAnswers() + 1);
                    }
                }).collect(Collectors.toList()));
        return changedStats;
    }

    public QuestionAnswersStatisticsDto createInitialQuestionAnswerStatisticsInstance(
            AnswerStatistics answerStatistics) {
//        return new QuestionAnswersStatisticsDto(answerStatistics.getQuestion().getQuestionText(),
//                answerStatistics.getQuestion().getDifficulty(), Mapper.map(answerStatistics.getQuestion()
//                        .getCategory() == null ? new Category() : answerStatistics.getQuestion().getCategory(),
//                CategoryDto.class), answerStatistics.getQuestion().getAnswers()
//                .stream()
//                .map((answer) -> new AnswerHistogramDto(answer,
//                        answerStatistics.getAnswer().getId().equals(answer.getId()) ? 1 : 0))
//                .collect(Collectors.toList()));
        return new QuestionAnswersStatisticsDto();
    }
}
