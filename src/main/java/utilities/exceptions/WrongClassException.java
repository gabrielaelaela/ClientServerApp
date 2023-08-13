package utilities.exceptions;

public class WrongClassException extends RuntimeException {
    public WrongClassException(String input) {
        super(input);
    }
}
