package com.example.quiz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TranslatedAnswerDto {
    private Boolean isCorrect;
    private List<TranslatedTextDto> localizedAnswers;

}
