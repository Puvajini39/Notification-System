package model;

public class Notification {
    private String recipientId;
    private String message;
    private String timestamp;

    public Notification(String recipientId, String message, String timestamp) {
        this.recipientId = recipientId;
        this.message = message;
        this.timestamp = timestamp;
    }


    public String getRecipientId() {
        return recipientId;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
