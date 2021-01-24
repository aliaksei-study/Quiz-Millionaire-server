package com.example.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "answer")
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    List<LocalizedAnswer> localizedAnswers;

    @Column(name = "isCorrect")
    private Boolean isCorrect;

    public Answer() {}

    public Answer(List<LocalizedAnswer> localizedAnswers, Boolean isCorrect) {
        this.localizedAnswers = localizedAnswers;
        this.isCorrect = isCorrect;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Answer answer = (Answer) obj;
        return this.getId().equals(answer.getId());
    }

    @Override
    public int hashCode() {
        return localizedAnswers.hashCode() + isCorrect.hashCode();
    }
}
