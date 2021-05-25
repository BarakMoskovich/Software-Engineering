package exceptions;

public class StringLengthException extends Exception{
    private String message;

    public StringLengthException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}