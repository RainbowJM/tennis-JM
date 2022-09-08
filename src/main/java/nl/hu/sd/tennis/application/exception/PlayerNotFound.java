package nl.hu.sd.tennis.application.exception;

public class PlayerNotFound extends RuntimeException {
    public PlayerNotFound(int id) {
        super("Player with id" + id + " was not found");
    }
}
