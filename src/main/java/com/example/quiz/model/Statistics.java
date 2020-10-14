package com.example.quiz.model;

import com.example.quiz.model.enumeration.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preferred_difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty preferredDifficulty;

    @Column(name = "number_of_games")
    private int numberOfGames;

    @Column(name = "score")
    private int score;

    public Statistics(Difficulty difficulty, int numberOfGames, int score) {
        this.preferredDifficulty = difficulty;
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
        return this.preferredDifficulty == statistics.preferredDifficulty && this.numberOfGames == statistics.numberOfGames &&
                this.score == statistics.score;
    }

    @Override
    public int hashCode() {
        return (31 * ((preferredDifficulty == null) ? 0 : preferredDifficulty.hashCode()) +
                31 * numberOfGames + 31 * score);
    }
}
