package util;

public enum ExceptionMessage {
    NO_SUCH_BIKE_WITH_ID("A bike with this ID doesn't exist."),
    BIKE_ALREADY_EXISTS("A bike with this ID already exists."),
    ERROR_LOADING_PROPERTIES("Error loading system properties file"),
    CLIENT_ALREADY_EXISTS("A client with this ID already exists."),
    NO_SUCH_CLIENT_WITH_ID("A client with this ID doesn't exist.");

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}