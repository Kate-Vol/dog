package dog.exeption_handling;

public class NoSuchDogException extends RuntimeException{

    public NoSuchDogException(String message) {
        super(message);
    }
}
