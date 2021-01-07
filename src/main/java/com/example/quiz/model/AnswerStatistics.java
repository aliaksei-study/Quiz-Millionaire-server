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

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public AnswerStatistics(Question question, Answer answer, Player player) {
        this.question = question;
        this.answer = answer;
        this.player = player;
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
        return this.question != null && this.question.equals(answerStatistics.question)
                && this.answer != null && this.answer.equals(answerStatistics.answer) &&
                this.player.equals(answerStatistics.player);
    }

    @Override
    public int hashCode() {
        return ((null == question) ? 0 : question.hashCode()) + ((null == answer) ? 0 : answer.hashCode()) +
                ((null == player) ? 0 : player.hashCode());
    }
}
