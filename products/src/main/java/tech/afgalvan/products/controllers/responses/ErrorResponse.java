package tech.afgalvan.products.controllers.responses;

public class ErrorResponse implements Response {
    private int error;
    private String message;

    public ErrorResponse(String message) {
        this.error = 404;
        this.message = message;
    }

    public ErrorResponse(int error, String message) {
        this.error = error;
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
