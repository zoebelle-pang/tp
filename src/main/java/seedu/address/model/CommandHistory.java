package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;

/**
 * Command history.
 */
public class CommandHistory implements ReadOnlyCommandHistory {

    private final CommandList commandList;

    /**
     * Constructor for a new Command History instance.
     */
    public CommandHistory() {
        commandList = new CommandList();
    };

    /**
     * Constructor that takes in a command history to be copied over.
     *
     * @param toBeCopied Command history to be copied over.
     */
    public CommandHistory(ReadOnlyCommandHistory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Reset command history.
     *
     * @param newData New history.
     */
    public void resetData(ReadOnlyCommandHistory newData) {
        requireNonNull(newData);

        setHistory(newData.getCommandHistory());
    }

    public void setHistory(List<Command> commands) {
        this.commandList.setList(commands);
    }

    public ObservableList<Command> getCommandHistory() {
        return commandList.asUnmodifiableObservableList();
    }

    public void add(Command command) {
        commandList.add(command);
    }
}
