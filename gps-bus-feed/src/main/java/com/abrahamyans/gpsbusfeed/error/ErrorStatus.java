package com.abrahamyans.gpsbusfeed.error;

/**
 * @author Samvel Abrahamyan
 */

public enum ErrorStatus {
    GOOGLE_PLAY_SERVICES_UNAVAILABLE ("Could not connect to Google Play Services"),
    API_CONNECTION_FAILURE("Failed to conncet to location API"),
    NOT_ENOUGH_PERMISSIONS("Not enough permissions for using location services");

    private String defaultMessage;

    ErrorStatus(String message){
        this.defaultMessage = message;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
