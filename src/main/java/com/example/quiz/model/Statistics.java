package com.example.quiz.model;

import com.example.quiz.model.enumeration.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@NoArgsConstructor
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "high_difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty highDifficulty;

    @Column(name = "number_of_games")
    private int numberOfGames;

    @Column(name = "score")
    private int score;

    @OneToMany
    @JoinTable(name = "answer_statistics")
    private List<AnswerStatistics> answeredQuestionStatistics;

    public Statistics(Difficulty difficulty, int numberOfGames, int score) {
        this.highDifficulty = difficulty;
        this.numberOfGames = numberOfGames;
        this.score = score;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Statistics statistics = (Statistics) obj;
        return this.highDifficulty == statistics.highDifficulty && this.numberOfGames == statistics.numberOfGames &&
                this.score == statistics.score;
    }

    @Override
    public int hashCode() {
        return (31 * ((highDifficulty == null) ? 0 : highDifficulty.hashCode()) +
                31 * numberOfGames + 31 * score);
    }
}
