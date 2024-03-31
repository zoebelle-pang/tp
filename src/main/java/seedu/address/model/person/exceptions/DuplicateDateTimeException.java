package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate DateTime.
 */
public class DuplicateDateTimeException extends RuntimeException {
    public DuplicateDateTimeException() {
        super("Operation would result in 2 student having the same tutoring slot");
    }
}

