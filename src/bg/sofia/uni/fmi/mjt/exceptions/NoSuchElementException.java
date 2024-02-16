package bg.sofia.uni.fmi.mjt.exceptions;

public class NoSuchElementException extends RuntimeException {  // to-do change name
    public NoSuchElementException(String message) {
        super(message);
    }

    public NoSuchElementException(String message, Throwable cause) {
        super(message, cause);
    }
}
