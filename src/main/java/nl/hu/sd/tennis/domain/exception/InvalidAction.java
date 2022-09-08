package nl.hu.sd.tennis.domain.exception;

public class InvalidAction extends RuntimeException{
    public InvalidAction(String message){
        super(message);
    }
}
