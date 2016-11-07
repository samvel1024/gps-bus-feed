package com.abrahamyans.gpsbusfeed.client.observer.event;

/**
 * @author Samvel Abrahamyan
 */

public class GpsBusFeedErrorEvent {
    private String message;
    private ErrorStatus status;

    public GpsBusFeedErrorEvent(ErrorStatus status) {
        this(status, null);
    }

    public GpsBusFeedErrorEvent(ErrorStatus status, String message) {
        this.status = status;
        this.message = message != null ? message : status.getDefaultMessage();
    }

    public String getMessage() {
        return message;
    }

    public ErrorStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "GpsBusFeedErrorEvent{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
