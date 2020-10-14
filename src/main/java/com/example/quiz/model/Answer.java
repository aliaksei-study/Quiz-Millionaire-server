package com.example.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "isCorrect")
    private Boolean isCorrect;

    public Answer(String answerText, boolean isCorrect) {
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Answer answer = (Answer) obj;
        if (answer.answerText != null) {
            return answer.answerText.equals(this.answerText) && this.isCorrect == answer.isCorrect;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (31 * ((null == answerText) ? 0 : answerText.hashCode()) + ((Boolean) isCorrect).hashCode());
    }
}
