package util;

public enum ExceptionMessage {
    NO_SUCH_BIKE_WITH_ID("A bike with this ID doesn't exist."),
    BIKE_ALREADY_EXISTS("A bike with this ID already exists."),
    ERROR_LOADING_PROPERTIES("Error loading system properties file");

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}