package nl.hu.sd.tennis.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Player {
    @Id
    @GeneratedValue
    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private int score = 0;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }
}
