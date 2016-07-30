package com.wolasoft.fiatlux.enums;

/**
 * HttpStatus enum
 */
public enum HttpStatus {
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    CREATED(201, "Created"),
    OK(200, "OK"),
    UNAUTHORIZED(401, "Unauthorized"),
    UNKNONW(-1, "Unknown");

    private int statusCode;

    private String description;

    HttpStatus(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getDescription() {
        return this.description;
    }
}
