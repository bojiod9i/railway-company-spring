package ru.tsystems.railway.exception;


public class BuyTicketException extends Exception {

    private Reason reason;

    /**
     * Creates a new instance of
     * <code>BuyTicketException</code> without detail message.
     */
    public BuyTicketException() {
    }

    /**
     * Constructs an instance of
     * <code>BuyTicketException</code> with the specified detail parameter.
     *
     * @param reason the detail about exception.
     */
    public BuyTicketException(Reason reason) {
        this.reason = reason;
    }

    public enum Reason {
        EXPIRED_TIME, NO_AVAILABLE_SEATS, PASSENGER_ALREADY_REGISTER
    }

    /**
     * Get reason throwing exception
     * @return reason
     */
    public Reason getReason() {
        return reason;
    }
}
