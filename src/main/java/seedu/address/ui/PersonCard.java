package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {
    private static final String FXML = "PersonListCard.fxml";
    private static final String PHONE_DESCRIPTION = "Phone: ";
    private static final String ADDRESS_DESCRIPTION = "Address: ";
    private static final String EMAIL_DESCRIPTION = "Email: ";
    private static final String SUBJECT_DESCRIPTION = "Subject: ";
    private static final String GRADE_DESCRIPTION = "Grade: ";
    private static final String ATTENDANCE_DESCRIPTION = "Last class attendance: ";
    private static final String PAYMENT_DESCRIPTION = "Current monthly fees status: ";
    private static final String CELL_SMALL_LABEL_CLASS = "cell_small_label";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private final NoteWindow noteWindow;
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private HBox phone;
    @FXML
    private HBox address;
    @FXML
    private HBox email;
    @FXML
    private HBox subject;
    @FXML
    private HBox grade;
    @FXML
    private HBox attendance;
    @FXML
    private HBox payment;
    @FXML
    private Button noteButton;
    @FXML
    private HBox dateTimes;
    @FXML
    private Label dateTimeDescription;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;

        Stage stage = new Stage();
        stage.setMaxWidth(400);
        this.noteWindow = new NoteWindow(stage, person);

        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setField(phone, PHONE_DESCRIPTION, person.getPhone().value);
        setField(address, ADDRESS_DESCRIPTION, person.getAddress().value);
        setField(email, EMAIL_DESCRIPTION, person.getEmail().value);
        setField(attendance, ATTENDANCE_DESCRIPTION, person.getAttendance().value);
        setField(payment, PAYMENT_DESCRIPTION, person.getPayment().value);
        setField(subject, SUBJECT_DESCRIPTION, person.getSubject().value);
        setField(grade, GRADE_DESCRIPTION, person.getGrade().value);
        person.getDateTimes().stream()
                .sorted(Comparator.comparing(dateTime -> dateTime.value))
                .forEach(dateTime -> {
                    Label dateTimeLabel = new Label(LocalDateTime.parse(dateTime.value,
                                    DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm"))
                            .format(DateTimeFormatter.ofPattern("MMM d uuuu h:mma")));
                    dateTimeLabel.getStyleClass().add(CELL_SMALL_LABEL_CLASS);
                    dateTimes.getChildren()
                        .add(dateTimeLabel);
                });
    }

    public void setField(HBox hbox, String description, String value) {
        Label descriptionLabel = new Label(description);
        descriptionLabel.getStyleClass().add(CELL_SMALL_LABEL_CLASS);
        HBox.setHgrow(descriptionLabel, Priority.NEVER);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add(CELL_SMALL_LABEL_CLASS);
        HBox.setHgrow(valueLabel, Priority.NEVER);
        hbox.getChildren().addAll(descriptionLabel, spacer, valueLabel);
    }

    /**
     * Opens the note window or focuses on it if it's already opened.
     */
    @FXML
    public void handleNote() {
        if (!noteWindow.isShowing()) {
            noteWindow.show();
        } else {
            noteWindow.focus();
        }
    }
}
