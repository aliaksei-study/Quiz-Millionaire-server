package com.example.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "localized_answer")
@Getter
@Setter
@NoArgsConstructor
public class LocalizedAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_text")
    private String answerText;

    @OneToOne
    @JoinColumn(name = "lanuage_id")
    private Language language;

    public LocalizedAnswer(String answerText, Language language) {
        this.answerText = answerText;
        this.language = language;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        LocalizedAnswer localizedAnswer = (LocalizedAnswer) obj;
        return this.getId().equals(localizedAnswer.getId());
    }

    @Override
    public int hashCode() {
        return (31 * ((null == answerText) ? 0 : answerText.hashCode()) +
                31 * ((null == language) ? 0 : language.hashCode()));
    }
}
