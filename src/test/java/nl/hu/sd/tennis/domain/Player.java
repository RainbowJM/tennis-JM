package nl.hu.sd.tennis.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    @DisplayName("")
    void nameEqual(){
        Player player = new Player("Jason");

        assertEquals("Jason", player.getName());
    }
}
