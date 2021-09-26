package com.example.quiz.service;

import com.example.quiz.controller.v1.response.TranslatedQuestionResponse;
import com.example.quiz.dto.*;
import com.example.quiz.exception.CategoryNotFoundException;
import com.example.quiz.exception.QuestionNotFoundException;
import com.example.quiz.mapper.Mapper;
import com.example.quiz.model.*;
import com.example.quiz.model.enumeration.Difficulty;
import com.example.quiz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@EnableScheduling
public class QuestionServiceImpl implements IQuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerStatisticsRepository answerStatisticsRepository;
    private final PlayerRepository playerRepository;
    private final LocalizedAnswerRepository localizedAnswerRepository;
    private final LocalizedQuestionRepository localizedQuestionRepository;

    private final ILanguageService languageService;
    private final ICategoryService categoryService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository,
                               AnswerStatisticsRepository answerStatisticsRepository,
                               PlayerRepository playerRepository,
                               ILanguageService languageService,
                               ICategoryService categoryService,
                               LocalizedQuestionRepository localizedQuestionRepository,
                               LocalizedAnswerRepository localizedAnswerRepository) {
        this.questionRepository = questionRepository;
        this.answerStatisticsRepository = answerStatisticsRepository;
        this.playerRepository = playerRepository;
        this.languageService = languageService;
        this.categoryService = categoryService;
        this.localizedQuestionRepository = localizedQuestionRepository;
        this.localizedAnswerRepository = localizedAnswerRepository;
    }

    @Override
    public TranslatedQuestionResponse updateQuestion(TranslatedQuestionDto questionDto, Long id)
            throws QuestionNotFoundException, CategoryNotFoundException {
        Question question = getQuestionById(id);

        if (question.getCategory() == null || !question.getCategory().getId().equals(questionDto.getCategory())) {
            Category category = categoryService.getCategoryById(questionDto.getCategory());
            question.setCategory(category);
        }

        questionDto.getQuestionTextTranslates().forEach(languageService::setPersistedLanguageIfNotExist);
        question.setQuestionTextTranslates(Mapper.mapAll(questionDto.getQuestionTextTranslates(), LocalizedQuestion.class));

        questionDto.getAnswers()
                .forEach((answer) -> answer.getLocalizedAnswers().forEach(languageService::setPersistedLanguageIfNotExist));
        question.setAnswers(Mapper.mapAll(questionDto.getAnswers(), Answer.class));

        question.setIsTemporal(questionDto.getIsTemporal());
        question.setDifficulty(questionDto.getDifficulty());
        question.setImagePath(questionDto.getImagePath());

        updatePersistedQuestion(question);

        question = questionRepository.save(question);

        return Mapper.map(question, TranslatedQuestionResponse.class);
    }

    @Override
    public void deleteQuestion(Long id) throws QuestionNotFoundException {
        Question question = getQuestionById(id);
        answerStatisticsRepository.deleteAll(answerStatisticsRepository.findAnswerStatisticsByQuestion(question));
        processPlayersWithDeletedQuestion(question);
        questionRepository.delete(question);
    }

    public void processPlayersWithDeletedQuestion(Question question) {
        List<Player> playersByLikedQuestion = playerRepository.findPlayerByLikedQuestions(question);
        List<Player> playersByDislikedQuestion = playerRepository.findPlayersByDislikedQuestions(question);
        playersByLikedQuestion.forEach((player) -> player.getLikedQuestions()
                .removeIf((likedQuestion) -> likedQuestion.equals(question)));
        playersByDislikedQuestion.forEach((player) -> player.getDislikedQuestions()
                .removeIf((likedQuestion) -> likedQuestion.equals(question)));
        playerRepository.saveAll(playersByLikedQuestion);
        playerRepository.saveAll(playersByDislikedQuestion);
    }

    @Override
    public Question getQuestionById(Long questionId) throws QuestionNotFoundException {
        return questionRepository.findById(questionId).orElseThrow(() ->
                new QuestionNotFoundException("question with id: " + questionId + " is not exist"));
    }

    @Override
    public List<SatisfiedQuestionStatisticsDto> getSatisfiedQuestionStatistics() {
        return questionRepository.findAll()
                .stream()
                .map((question) -> new SatisfiedQuestionStatisticsDto(Mapper.mapAll(question.getQuestionTextTranslates(), TranslatedTextDto.class),
                        question.getDifficulty(), question.getCategory() != null ? question.getCategory().getId() : null,
                        question.getLikedQuestionPlayers().size(), question.getDislikedQuestionPlayers().size()))
                .filter((statistic) -> statistic.getNumberOfDislikes() > 0 || statistic.getNumberOfLikes() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<TranslatedQuestionResponse> getQuestions() {
        return Mapper.mapAll(questionRepository.findAll(), TranslatedQuestionResponse.class);
    }

    @Override
    public TranslatedQuestionResponse saveQuestion(TranslatedQuestionDto questionDto) throws CategoryNotFoundException {
        Category category = categoryService.getCategoryById(questionDto.getCategory());

        questionDto.getQuestionTextTranslates().forEach(languageService::setPersistedLanguageIfNotExist);

        questionDto.getAnswers()
                .forEach((answer) -> answer.getLocalizedAnswers().forEach(languageService::setPersistedLanguageIfNotExist));

        Question question = Mapper.map(questionDto, Question.class);
        question.setCategory(category);
        if (question.getDifficulty() == null || question.getCategory() == null) {
            question.setDifficulty(Difficulty.MEDIUM);
            question.setIsTemporal(true);
        }

        updatePersistedQuestion(question);

        question = questionRepository.save(question);
        return Mapper.map(question, TranslatedQuestionResponse.class);
    }

    @Override
    public List<QuestionDto> getFifteenRandomQuestions(String language) {
        final int numberOfEasyQuestions = 5;
        final int numberOfMediumQuestions = 5;
        final int numberOfHardQuestions = 3;
        final int numberOfNightMareQuestions = 2;
        List<Question> randomEasyQuestions = questionRepository.findNthRandomQuestionsByDifficultyAndLanguage(Difficulty.EASY,
                false, language, PageRequest.of(0, numberOfEasyQuestions));
        List<Question> randomTemporalQuestions = questionRepository.findNthRandomQuestionsByDifficultyAndLanguage(Difficulty.MEDIUM,
                true, language, PageRequest.of(0, 1));
        List<Question> randomMediumQuestions = questionRepository.findNthRandomQuestionsByDifficultyAndLanguage(Difficulty.MEDIUM,
                false, language, PageRequest.of(0, randomTemporalQuestions.size() == 0 ? numberOfEasyQuestions :
                        numberOfMediumQuestions));
        List<Question> randomHardQuestions = questionRepository.findNthRandomQuestionsByDifficultyAndLanguage(Difficulty.HARD,
                false, language, PageRequest.of(0, numberOfHardQuestions));
        List<Question> randomNightMareQuestions = questionRepository.findNthRandomQuestionsByDifficultyAndLanguage(Difficulty.NIGHTMARE,
                false, language, PageRequest.of(0, numberOfNightMareQuestions));
        if (randomTemporalQuestions.size() == 1) {
            randomMediumQuestions.remove(randomMediumQuestions.size() - 1);
            randomMediumQuestions.add(randomTemporalQuestions.get(0));
        }
        return Stream.of(randomEasyQuestions, randomMediumQuestions, randomHardQuestions,
                randomNightMareQuestions)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .stream()
                .map(question -> Mapper.mapLocalizedQuestionToQuestionDto(question, language))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 500000)
    @Override
    public void processPlayerQuestions() {
        final int minimalNumberOfLikes = 1;
        List<Question> temporalQuestions = questionRepository.findAllByIsTemporal(true);
        if (temporalQuestions.size() > 0) {
            temporalQuestions.forEach((question) -> {
                int numberOfDislikes = questionRepository
                        .findAllByDislikedQuestionPlayersIsNotNullAndId(question.getId()).size();
                int numberOfLikes = questionRepository
                        .findAllByLikedQuestionPlayersIsNotNullAndId(question.getId()).size();
                if (numberOfLikes > minimalNumberOfLikes && numberOfLikes > numberOfDislikes) {
                    question.setIsTemporal(false);
                    questionRepository.save(question);
                } else if (numberOfDislikes > 2 * numberOfLikes && numberOfLikes < minimalNumberOfLikes) {
                    try {
                        deleteQuestion(question.getId());
                    } catch (QuestionNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void updatePersistedQuestion(Question question) {
        question.setQuestionTextTranslates(question.getQuestionTextTranslates()
                .stream()
                .map(this::persistQuestionTranslateIfNotExist)
                .collect(Collectors.toList()));

        question.getAnswers().forEach((answer) -> answer.setLocalizedAnswers(answer.getLocalizedAnswers()
                .stream()
                .map(this::persistAnswerTranslateIfNotExist)
                .collect(Collectors.toList())));
    }

    public LocalizedQuestion persistQuestionTranslateIfNotExist(LocalizedQuestion localizedQuestion) {
        if (localizedQuestion.getId() == null) {
            return localizedQuestionRepository.save(localizedQuestion);
        }
        return localizedQuestion;
    }

    public LocalizedAnswer persistAnswerTranslateIfNotExist(LocalizedAnswer localizedAnswer) {
        if(localizedAnswer.getId() == null) {
            return localizedAnswerRepository.save(localizedAnswer);
        }
        return localizedAnswer;
    }
}
