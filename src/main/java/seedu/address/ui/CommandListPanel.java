package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.model.person.Person;

import java.util.logging.Logger;

/**
 * Panel containing the list of commands.
 */
public class CommandListPanel extends UiPart<Region> {
    private static final String FXML = "CommandListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandListPanel.class);

    @FXML
    private ListView<Command> commandListView;

    /**
     * Creates a {@code commandListPanel} with the given {@code ObservableList}.
     */
    public CommandListPanel(ObservableList<Command> commandList) {
        super(FXML);
        commandListView.setItems(commandList);
        commandListView.setCellFactory(listView -> new commandListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Command} using a {@code CommandCard}.
     */
    class commandListViewCell extends ListCell<Command> {
        @Override
        protected void updateItem(Command command, boolean empty) {
            super.updateItem(command, empty);

            if (empty || command == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CommandCard(command, getIndex() + 1).getRoot());
            }
        }
    }

}
