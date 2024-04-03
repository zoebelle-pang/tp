package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;

/**
 * Unmodifiable view of a command history
 */
public interface ReadOnlyCommandHistory {
    /**
     * Returns an unmodifiable view of the persons list.
     */
    ObservableList<Command> getCommandHistory();
}
