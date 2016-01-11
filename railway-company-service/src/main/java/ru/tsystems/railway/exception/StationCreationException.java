package ru.tsystems.railway.exception;

import ru.tsystems.railway.domain.service.Station;

/**
 * Thrown when the {@link Station station} creation fails.
 *
 * @author lazukovvs@gmail.com
 */
public class StationCreationException extends Exception {

    /**
     * Creates a new instance of
     * <code>StationCreationException</code> without detail message.
     */
    public StationCreationException() {
    }

    /**
     * Constructs an instance of
     * <code>StationCreationException</code> with the specified detail message.
     *
     * @param message the detail message.
     */
    public StationCreationException(String message) {
        super(message);
    }
}
