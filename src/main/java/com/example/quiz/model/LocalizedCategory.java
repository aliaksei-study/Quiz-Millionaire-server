package com.example.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "localized_category")
@Getter
@Setter
@NoArgsConstructor
public class LocalizedCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "language_id")
    private Language language;

    public LocalizedCategory(String categoryName, Language language) {
        this.categoryName = categoryName;
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
        LocalizedCategory localizedCategory = (LocalizedCategory) obj;
        return this.getId().equals(localizedCategory.getId());
    }

    @Override
    public int hashCode() {
        return (31 * ((null == categoryName) ? 0 : categoryName.hashCode()) +
                31 * ((null == language) ? 0 : language.hashCode()));
    }
}
