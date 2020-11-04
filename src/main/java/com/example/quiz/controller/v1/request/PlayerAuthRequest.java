package com.example.quiz.controller.v1.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlayerAuthRequest {
    @Email
    private String email;
    @NotNull
    private String password;
}
