package nl.hu.sd.tennis.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Point {
    @Id
    private int id;

    private int score = 0;


    public Point() {
        // Hibernate
    }

    public String translatePoint(int score) {
        switch (score) {
            case 3:
                return "Forty";
            case 2:
                return "Thirty";
            case 1:
                return "Fifteen";
            case 0:
                return "Love";
        }
        throw new IllegalArgumentException("Illegal score: " + score);
    }
}
