package com.example.quiz.dto;

import com.example.quiz.model.enumeration.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerDto {
    private Long id;
    private String username;
    private Role role;
    private String createdDate;
}
