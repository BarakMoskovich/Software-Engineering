package exceptions;

public class UnderAgeException extends Exception{
    private String message;

    public UnderAgeException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}