package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;


/**
 * A command list that tracks and returns the list when needed. Guarantees immutability.
 */
public class CommandList implements Iterable<Command> {

    private static final int THRESHOLD = 10;
    private final ObservableList<Command> internalList = FXCollections.observableArrayList();

    private final ObservableList<Command> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds the command into the command list for tracking.
     *
     * @param toAdd Command to add.
     */
    public void add(Command toAdd) {
        requireNonNull(toAdd);
        if (internalList.size() >= THRESHOLD) {
            internalList.remove(0);
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Command> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public void setList(List<Command> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement);
    }

    @Override
    public Iterator<Command> iterator() {
        return internalList.iterator();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
