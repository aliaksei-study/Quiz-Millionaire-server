package com.example.quiz.model.enumeration;

import java.io.Serializable;

public enum Difficulty implements Serializable {
    EASY(10000),
    MEDIUM(50000),
    HARD(100000),
    NIGHTMARE(200000);

    private int cost;

    Difficulty(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
