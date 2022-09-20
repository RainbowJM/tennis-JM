package nl.hu.sd.tennis.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private Player server;

    private Player receiver;

    private Round round;

    @BeforeEach
    void setUp(){
        server = new Player("Juan");
        receiver = new Player("Lucas");

        round= new Round();
    }

    @Test
    void start() {
        round.start();
        int serverScore = server.getScore();
        int receiverScore = receiver.getScore();

        assertEquals(0, serverScore);
        assertEquals(0, receiverScore);
    }

    @Test
    void playing(){
        round.playing(server,receiver);
        String status = round.getStatus().toString();
        int receiverScore = round.getReceiverScore();
        int serverScore = round.getServerScore();

        assertEquals("PLAYING", status);
    }
}