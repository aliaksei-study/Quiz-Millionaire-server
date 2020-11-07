package com.example.quiz.controller.v1.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LikedQuestionRequest {
    private Long questionId;
}
