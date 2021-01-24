package com.example.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "localized_question")
@Getter
@Setter
@NoArgsConstructor
public class LocalizedQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text")
    private String questionText;

    @OneToOne
    @JoinColumn(name = "lanuage_id")
    private Language language;

    public LocalizedQuestion(String questionText, Language language) {
        this.questionText = questionText;
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
        LocalizedQuestion localizedQuestion = (LocalizedQuestion) obj;
        return this.getId().equals(localizedQuestion.getId());
    }

    @Override
    public int hashCode() {
        return (31 * ((null == questionText) ? 0 : questionText.hashCode()) +
                31 * ((null == language) ? 0 : language.hashCode()));
    }
}
