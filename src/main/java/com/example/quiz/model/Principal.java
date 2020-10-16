package com.example.quiz.model;

import com.example.quiz.model.enumeration.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "principal")
@Getter
@Setter
@NoArgsConstructor
public class Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "passw")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public Principal(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Principal principal = (Principal) obj;
        return this.username != null && this.username.equals(principal.username) && this.password != null &&
                this.password.equals(principal.password) && this.role == principal.role;
    }

    @Override
    public int hashCode() {
        return (31 * ((this.username == null) ? 0 : this.username.hashCode()) +
                31 * ((this.password == null) ? 0 : this.password.hashCode()) +
                31 * ((this.role == null) ? 0 : this.role.hashCode()));
    }
}