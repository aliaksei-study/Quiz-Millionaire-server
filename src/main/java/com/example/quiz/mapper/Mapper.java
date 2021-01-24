package com.example.quiz.mapper;

import com.example.quiz.dto.AnswerDto;
import com.example.quiz.dto.QuestionDto;
import com.example.quiz.model.Answer;
import com.example.quiz.model.LocalizedAnswer;
import com.example.quiz.model.LocalizedQuestion;
import com.example.quiz.model.Question;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    private static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(false)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
    }

    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public static <S, T> List<T> mapAll(Collection<? extends S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(e -> map(e, targetClass))
                .collect(Collectors.toList());
    }

    public static List<QuestionDto> mapAllLocalizedQuestions(Collection<? extends Question> questions, String language) {
        return questions.stream()
                .map(e -> localizedQuestionMap(e, language))
                .collect(Collectors.toList());
    }

    public static QuestionDto localizedQuestionMap(Question question, String language) {
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
        List<LocalizedQuestion> questionTranslates = question.getLocalizedQuestion()
                .stream()
                .filter((item) -> item.getLanguage().getAbbreviation().equals(language))
                .collect(Collectors.toList());
        if(!questionTranslates.isEmpty()) {
            questionDto.setQuestionText(questionTranslates.get(0).getQuestionText());
        }
        questionDto.setAnswers(question.getAnswers()
                .stream()
                .map((answer) -> localizedAnswerMap(answer, language))
                .collect(Collectors.toList()));
        return questionDto;
    }

    public static AnswerDto localizedAnswerMap(Answer answer, String language) {
        AnswerDto answerDto = modelMapper.map(answer, AnswerDto.class);
        List<LocalizedAnswer> answerTranslates = answer.getLocalizedAnswers()
                .stream()
                .filter((item) -> item.getLanguage().getAbbreviation().equals(language))
                .collect(Collectors.toList());
        if(!answerTranslates.isEmpty()) {
            answerDto.setAnswerText(answerTranslates.get(0).getAnswerText());
        }
        return answerDto;
    }
}
