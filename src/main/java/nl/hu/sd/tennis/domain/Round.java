package nl.hu.sd.tennis.domain;

import nl.hu.sd.tennis.domain.exception.InvalidAction;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Round {
    @Id
    @GeneratedValue
    private int id;

    private int score = 0;

    @OneToOne
    private Player server;

    @OneToOne
    private Player receiver;

    @OneToOne
    private Point point;

    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.START;



    @OneToMany(cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private final List<SetRound> setRounds = new ArrayList<>();

    public Round() {
        // This is for Hibernate
    }

    public Round(Player server, Player receiver) {
        this.server = server;
        this.receiver = receiver;

    }

    public void start() {
        if (status == GameStatus.PLAYING) {
            throw new InvalidAction("Game is already playing");
        }

        status = GameStatus.START;

        if (randomNumber() == 1) {
            int serverScore = server.getScore();
            serverScore = serverScore + 1;
        } else {
            int receiverScore = receiver.getScore();
            receiverScore = receiverScore + 1;
        }

        getPoint().translatePoint(score);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    private int randomNumber() {
        Random r = new Random();

        return r.nextInt(2);
    }
}
