package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String SHOWING_SCHEDULE_MESSAGE = "Opened schedule window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_SCHEDULE_MESSAGE, false, false, true);
    }
}
