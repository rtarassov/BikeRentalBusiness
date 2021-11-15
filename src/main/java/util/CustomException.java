package util;

public class CustomException extends Exception {

    private ExceptionMessage message;

    public CustomException(ExceptionMessage message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message.getMessage();
    }

}