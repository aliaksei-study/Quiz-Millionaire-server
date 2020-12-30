package com.example.quiz.dto;

import com.example.quiz.model.enumeration.Difficulty;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String questionText;
    private String imagePath;
    private Boolean isTemporal;
    private Difficulty difficulty;
    private CategoryDto category;
    private List<AnswerDto> answers;
    private String createdDate;
    private String lastModifiedDate;
    private String createdBy;
}
