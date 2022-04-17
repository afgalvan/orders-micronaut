package tech.afgalvan.products.controllers.responses;

public class UpdatedProductResponse implements Response {
    private String message;
    private boolean wasUpdated;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isWasUpdated() {
        return wasUpdated;
    }

    public void setWasUpdated(boolean wasUpdated) {
        this.wasUpdated = wasUpdated;
    }
}
