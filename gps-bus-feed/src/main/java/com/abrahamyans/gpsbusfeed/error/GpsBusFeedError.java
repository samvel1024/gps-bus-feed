package com.abrahamyans.gpsbusfeed.error;

/**
 * @author Samvel Abrahamyan
 */

public class GpsBusFeedError {
    private String message;
    private ErrorStatus status;

    public GpsBusFeedError(ErrorStatus status){
        this(status, null);
    }

    public GpsBusFeedError(ErrorStatus status, String message){
        this.status = status;
        this.message = message != null ?message : status.getDefaultMessage();
    }

    public String getMessage() {
        return message;
    }

    public ErrorStatus getStatus() {
        return status;
    }

}
