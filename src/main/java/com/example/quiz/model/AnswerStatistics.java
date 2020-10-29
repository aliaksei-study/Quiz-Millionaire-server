package com.example.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answer_statistics")
@Getter
@Setter
@NoArgsConstructor
public class AnswerStatistics implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    public AnswerStatistics(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        AnswerStatistics answerStatistics = (AnswerStatistics) obj;
        return super.equals(answerStatistics) && this.question != null && this.question.equals(answerStatistics.question)
                && this.answer != null && this.answer.equals(answerStatistics.answer);
    }

    @Override
    public int hashCode() {
        return (super.hashCode() + ((null == question) ? 0 : question.hashCode()) + ((null == answer) ? 0 : answer.hashCode()));
    }
}
