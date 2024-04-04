package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Controller for a note page
 */
public class NoteWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(NoteWindow.class);
    private static final String FXML = "NoteWindow.fxml";
    @FXML
    private VBox noteContainer;
    @FXML
    private Label noteName;
    @FXML
    private Label noteText;

    /**
     * Creates a new NoteWindow.
     *
     * @param person The person whose note is to be displayed.
     */
    public NoteWindow(Stage root, Person person) {
        super(FXML, root);
        noteName.setText(String.format("%s's note\n\n", person.getName().fullName));
        noteText.setText(person.getNote().value);
        noteText.setMinWidth(400);
        noteText.setMaxWidth(400);
        noteText.setWrapText(true);
    }

    /**
     * Shows the note window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing student note.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the note window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the note window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the note window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}