package net.engineeringdigest.journalApp.constants;

import lombok.*;

@Getter
public enum ApiStatus {

    // 2xx
    SUCCESS(200, "Success"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),

    // 4xx
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409, "Conflict"),
    UNPROCESSABLE(422, "Unprocessable Entity"),

    // 5xx
    INTERNAL_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable");

    private final int code;
    private final String message;

    ApiStatus(int code, String message){
        this.code = code;
        this.message = message;
    }
}
