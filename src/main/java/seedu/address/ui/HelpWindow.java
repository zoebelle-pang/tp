package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL =
            "https://ay2324s2-cs2103-f15-2.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE =
            "Here are the list of commands that are available for use: \n"
                    + "    - Viewing help : 'help'\n\n"
                    + "    - Adding a student: 'add'\n"
                    + "       Usage: 'add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [g/GRADE] [s/SUBJECT] "
                    + "[at/ATTENDANCE] [pa/PAYMENT] [d/DATETIME] [t/TAG]…'\n\n"
                    + "    - Listing all persons : 'list'\n\n"
                    + "    - Editing a person : 'edit'\n"
                    + "       Usage: 'edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/GRADE] [s/SUBJECT] "
                    + "[at/ATTENDANCE] [pa/PAYMENT] [d/DATETIME] [t/TAG]…'\n\n"
                    + "    - Locating persons by name: 'find'\n"
                    + "       Usage: 'find KEYWORD [MORE_KEYWORDS]'\n\n"
                    + "    - Deleting a person : 'delete'\n"
                    + "       Usage: 'delete INDEX'\n\n"
                    + "    - Filtering by Grade / Subject: 'filter'\n"
                    + "       Usage: 'filter [g/GRADE] [s/SUBJECT]'\n\n"
                    + "    - Categorising by Payment: 'payment'\n"
                    + "       Usage: 'payment pa/PAYMENT'\n\n"
                    + "    - Viewing Calendar : 'view'\n\n"
                    + "    - Showing list of previously successful commands : 'history'\n\n"
                    + "    - Executing command in command history list : 'history'\n"
                    + "       Usage: 'history [INDEX]'\n\n"
                    + "    - Clearing all entries : 'clear'\n\n"
                    + "    - Exiting the program : 'exit'\n\n"
                    + "You can view the complete user guide at "
                    + USERGUIDE_URL;



    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
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
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
