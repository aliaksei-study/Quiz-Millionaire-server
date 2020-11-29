package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractAuditingEntity {
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private String createdDate = Instant.now().toString();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    private String lastModifiedDate = Instant.now().toString();
}
