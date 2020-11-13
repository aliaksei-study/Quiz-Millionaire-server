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

    @Column(name = "question_text")
    private String questionText;

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
    @JoinColumn(name = "question_id")
    List<Answer> answers;

    public Question() {

    }

    public Question(String questionText, String imagePath, Boolean isTemporal, Difficulty difficulty, Category category, List<Answer> answers) {
        this.questionText = questionText;
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
        return (31 * ((null == questionText) ? 0 : questionText.hashCode()) +
                31 * ((null == imagePath) ? 0 : imagePath.hashCode()) +
                31 * ((null == difficulty) ? 0 : difficulty.hashCode()) +
                31 * ((null == category) ? 0 : category.hashCode()) +
                31 * ((null == isTemporal) ? 0 : isTemporal.hashCode()));
    }
}
