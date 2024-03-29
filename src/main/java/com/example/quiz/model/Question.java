package com.example.quiz.model;


import com.example.quiz.model.enumeration.Difficulty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "question")
public class Question extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "question_translation_id")
    List<LocalizedQuestion> questionTextTranslates;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "is_temporal")
    private Boolean isTemporal;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_id")
    private Difficulty difficulty;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private List<Answer> answers;

    @ManyToMany(mappedBy = "likedQuestions")
    private List<Player> likedQuestionPlayers;

    @ManyToMany(mappedBy = "dislikedQuestions")
    private List<Player> dislikedQuestionPlayers;

    public Question() {

    }

    public Question(List<LocalizedQuestion> questionTextTranslates, String imagePath, Boolean isTemporal, Difficulty difficulty, Category category, List<Answer> answers) {
        this.questionTextTranslates = questionTextTranslates;
        this.imagePath = imagePath;
        this.isTemporal = isTemporal;
        this.difficulty = difficulty;
        this.category = category;
        this.answers = answers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Question question = (Question) obj;
        return this.getId().equals(question.getId());
    }

    @Override
    public int hashCode() {
        return (31 * ((null == imagePath) ? 0 : imagePath.hashCode()) +
                31 * ((null == difficulty) ? 0 : difficulty.hashCode()) +
                31 * ((null == category) ? 0 : category.hashCode()) +
                31 * ((null == isTemporal) ? 0 : isTemporal.hashCode()));
    }
}
