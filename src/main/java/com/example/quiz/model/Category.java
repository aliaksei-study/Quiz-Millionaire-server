package com.example.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "translated_category_id")
    List<LocalizedCategory> categoryNameTranslates;

    public Category(List<LocalizedCategory> categoryNameTranslates) {
        this.categoryNameTranslates = categoryNameTranslates;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Category category = (Category) obj;
        if(null != this.categoryNameTranslates) {
            return this.id.equals(category.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return  31 * ((null == this.categoryNameTranslates) ? 0 : this.categoryNameTranslates.hashCode());
    }
}
