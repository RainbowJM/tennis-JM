package nl.hu.sd.tennis.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getId() {
    }

    @Test
    @DisplayName("")
    void getName() {
        Player player = new Player("Jason");

        assertEquals("Jason", player.getName());
    }

    @Test
    void getScore() {
    }

    @Test
    void setId() {
    }

    @Test
    void setName() {
    }

    @Test
    void setScore() {
    }
}