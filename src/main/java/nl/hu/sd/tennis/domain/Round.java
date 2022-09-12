package nl.hu.sd.tennis.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.sd.tennis.domain.exception.InvalidAction;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Entity
public class Round {
    @Id
    @GeneratedValue
    private int id;

    private int serverScore = 0;

    private int receiverScore = 0;

    @OneToOne
    private Player server;

    @OneToOne
    private Player receiver;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @ElementCollection
    private List<String> scoreBoardServer = new ArrayList<>();

    @ElementCollection
    private List<String> scoreBoardReceiver = new ArrayList<>();

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
    }

    public void playing(Player player1, Player player2) {
        status = GameStatus.PLAYING;

        if (!(serverScore > 4) && !(receiverScore > 4)) {
            if (randomNumber() == 1) {
                serverScore = player1.getScore();
                serverScore = serverScore + 1;
                scoreBoardServer.add(translatePoint(serverScore));
            } else {
                receiverScore = player2.getScore();
                receiverScore = receiverScore + 1;
                scoreBoardReceiver.add(translatePoint(receiverScore));
            }
        }
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

    private int randomNumber() {
        Random r = new Random();

        return r.nextInt(2);
    }
}
