package de.thu.thutorium.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a meeting is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MeetingNotFoundException extends RuntimeException {

    /**
     * Constructs a new MeetingNotFoundException with the specified detail message.
     *
     * @param message the detail message, which provides information about the exception.
     */
    public MeetingNotFoundException(String message) {
        super(message);
    }

}

