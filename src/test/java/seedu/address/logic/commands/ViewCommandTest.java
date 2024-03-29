package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewCommand.SHOWING_SCHEDULE_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ViewCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_view_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_SCHEDULE_MESSAGE, false, false, true);
        assertCommandSuccess(new ViewCommand(), model, expectedCommandResult, expectedModel);
    }
}
