package net.engineeringdigest.journalApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import net.engineeringdigest.journalApp.constants.ApiStatus;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final int status;
    private final String message;
    private final T data;
    private final String dataType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private final ZonedDateTime timestamp;
    private final long epochMillis;

    private ApiResponse(ApiStatus status, String message, T data) {
        this.status      = status.getCode();
        this.message     = message != null ? message : status.getMessage();
        this.data        = data;
        this.dataType    = data != null ? data.getClass().getSimpleName() : null;
        Instant now      = Instant.now();
        this.timestamp   = now.atZone(ZoneOffset.UTC);
        this.epochMillis = now.toEpochMilli();
    }
    // ── Factory methods ───────────────────────────────────────

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(ApiStatus.SUCCESS, null, data);
    }

    public static <T> ApiResponse<T> success(String message ,T data){
        return new ApiResponse<>(ApiStatus.SUCCESS, message, data);
    }

    public static <T> ApiResponse<T> created(String message ,T data){
        return new ApiResponse<>(ApiStatus.CREATED, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ApiStatus.BAD_REQUEST, message, null);
    }

    public static <T> ApiResponse<T> of(ApiStatus status, String message, T data){
        return new ApiResponse<>(status, message, data);
    }

}
