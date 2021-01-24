package com.example.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "language")
@Getter
@Setter
@NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    public Language(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Language language = (Language) obj;
        return this.name != null && this.name.equals(language.name) && this.abbreviation != null &&
                this.abbreviation.equals(language.abbreviation);
    }

    @Override
    public int hashCode() {
        return 31 * ((null == this.name) ? 0 : this.name.hashCode()) +
                31 * ((null == this.abbreviation) ? 0 : this.abbreviation.hashCode());
    }
}
