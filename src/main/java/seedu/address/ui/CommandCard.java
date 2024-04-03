package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.Command;

/**
 * An UI component that displays information of a {@code Command}.
 */
public class CommandCard extends UiPart<Region> {

    private static final String FXML = "CommandListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Command command;

    @FXML
    private HBox cardPane;
    @FXML
    private Label commandStr;
    @FXML
    private Label id;

    /**
     * Creates a {@code CommandCode} with the given {@code Command} and index to display.
     */
    public CommandCard(Command command, int displayedIndex) {
        super(FXML);
        this.command = command;
        id.setText(displayedIndex + ". ");
        commandStr.setText(command.toString());
    }
}
