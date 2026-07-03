package exceptions;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    private final Map<String, String> fieldErrors;
    private final LocalDateTime timestamp;

    public ErrorResponse(int status, String message, Map<String, String> fieldErrors, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.fieldErrors = fieldErrors;
        this.timestamp = timestamp;
    }
    
}
