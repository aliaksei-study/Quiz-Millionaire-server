package com.example.quiz.controller.v1.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerAnswerRequest {
    @NotNull
    private Long answerId;
    @NotNull
    private Long questionId;
}
