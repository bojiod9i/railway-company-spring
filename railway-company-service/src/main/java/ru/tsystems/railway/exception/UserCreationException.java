package ru.tsystems.railway.exception;

import ru.tsystems.railway.domain.accounts.User;

/**
 * Thrown when the {@link User user} creation fails.
 *
 * @author lazukovvs@gmail.com
 */
public class UserCreationException extends Exception {

    /**
     * Creates a new instance of
     * <code>UserCreationException</code> without detail message.
     */
    public UserCreationException() {
    }

    /**
     * Constructs an instance of
     * <code>UserCreationException</code> with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserCreationException(String message) {
        super(message);
    }
}
