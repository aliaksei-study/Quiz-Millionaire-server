package com.example.quiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player")
@Getter
@Setter
@NoArgsConstructor
public class Player extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "principal_id")
    private Principal principal;

    @OneToOne
    @JoinColumn(name = "statistics_id")
    private Statistics statistics;


    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Player player = (Player) obj;
        return this.id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return (31 * ((this.principal == null) ? 0 : this.principal.hashCode()) +
                31 * ((this.statistics == null) ? 0 : this.statistics.hashCode()));
    }
}
