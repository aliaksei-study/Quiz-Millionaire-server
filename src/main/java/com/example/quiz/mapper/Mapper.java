package com.example.quiz.mapper;

import com.example.quiz.dto.*;
import com.example.quiz.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(false)
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.typeMap(LocalizedCategory.class, TranslatedTextDto.class).addMappings((mapper) ->
                mapper.map(LocalizedCategory::getCategoryName, TranslatedTextDto::setTranslatedText));

        modelMapper.typeMap(LocalizedQuestion.class, TranslatedTextDto.class).addMappings((mapper) ->
                mapper.map(LocalizedQuestion::getQuestionText, TranslatedTextDto::setTranslatedText));

        modelMapper.typeMap(LocalizedAnswer.class, TranslatedTextDto.class).addMappings((mapper) ->
                mapper.map(LocalizedAnswer::getAnswerText, TranslatedTextDto::setTranslatedText));

        modelMapper.typeMap(LocalizedCategory.class, TranslatedTextDto.class).addMappings((mapper) ->
                mapper.map(LocalizedCategory::getCategoryName, TranslatedTextDto::setTranslatedText));

        modelMapper.typeMap(TranslatedTextDto.class, LocalizedQuestion.class).addMappings((mapper) ->
                mapper.map(TranslatedTextDto::getTranslatedText, LocalizedQuestion::setQuestionText));

        modelMapper.typeMap(TranslatedTextDto.class, LocalizedAnswer.class).addMappings((mapper) ->
                mapper.map(TranslatedTextDto::getTranslatedText, LocalizedAnswer::setAnswerText));

        modelMapper.typeMap(TranslatedTextDto.class, LocalizedCategory.class).addMappings((mapper) ->
                mapper.map(TranslatedTextDto::getTranslatedText, LocalizedCategory::setCategoryName));

        modelMapper.typeMap(TranslatedTextDto.class, LocalizedCategory.class).addMappings((mapper) ->
                mapper.map(TranslatedTextDto::getTranslatedText, LocalizedCategory::setCategoryName));
    }

    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    public static <S, T> List<T> mapAll(Collection<? extends S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(e -> map(e, targetClass))
                .collect(Collectors.toList());
    }

    public static List<QuestionDto> mapAllLocalizedQuestionsToQuestionDto(Collection<? extends Question> questions,
                                                                          String language) {
        return questions.stream()
                .map(e -> mapLocalizedQuestionToQuestionDto(e, language))
                .collect(Collectors.toList());
    }

    public static QuestionDto mapLocalizedQuestionToQuestionDto(Question question, String language) {
        QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);
        List<LocalizedQuestion> questionTranslates = question.getQuestionTextTranslates()
                .stream()
                .filter((item) -> item.getLanguage().getAbbreviation().equals(language))
                .collect(Collectors.toList());
        if(!questionTranslates.isEmpty()) {
            questionDto.setQuestionText(questionTranslates.get(0).getQuestionText());
        }
        if(question.getCategory() != null) {
            questionDto.setCategory(mapLocalizedCategoryToCategoryDto(question.getCategory(), language));
        }
        questionDto.setAnswers(question.getAnswers()
                .stream()
                .map((answer) -> mapLocalizedAnswerToAnswerDto(answer, language))
                .collect(Collectors.toList()));
        questionDto.setCategory(mapLocalizedCategoryToCategoryDto(question.getCategory(), language));
        return questionDto;
    }

    public static Question mapQuestionDtoToLocalizedQuestion(QuestionDto questionDto, Language language) {
        Question question = modelMapper.map(questionDto, Question.class);
        question.setQuestionTextTranslates(List.of(new LocalizedQuestion(questionDto.getQuestionText(), language)));
        question.setAnswers(questionDto.getAnswers()
                .stream()
                .map((item) -> mapAnswerDtoToLocalizedAnswer(item, language))
                .collect(Collectors.toList()));
        return question;
    }

    public static Answer mapAnswerDtoToLocalizedAnswer(AnswerDto answerDto, Language language) {
        Answer answer = modelMapper.map(answerDto, Answer.class);
        answer.setLocalizedAnswers(List.of(new LocalizedAnswer(answerDto.getAnswerText(), language)));
        return answer;
    }

    public static AnswerDto mapLocalizedAnswerToAnswerDto(Answer answer, String language) {
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

    public static Category mapCategoryDtoToLocalizedCategory(CategoryDto categoryDto, Language language) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setCategoryNameTranslates(List.of(new LocalizedCategory(categoryDto.getCategoryName(), language)));
        return category;
    }

    public static CategoryDto mapLocalizedCategoryToCategoryDto(Category category, String language) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        categoryDto.setCategoryName(category.getCategoryNameTranslates()
                .stream()
                .filter((lc) -> lc.getLanguage().getAbbreviation().equals(language))
                .collect(Collectors.toList())
                .get(0)
                .getCategoryName());
        return categoryDto;
    }
}
