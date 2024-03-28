package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.apache.commons.lang3.tuple.Pair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.person.DateTime;
import seedu.address.model.person.Person;

/**
 * Controller for a view schedule page
 */
public class ViewWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(ViewWindow.class);
    private static final String FXML = "Calendar.fxml";
    private final Logic logic;
    private LocalDateTime dateFocus;
    private LocalDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    /**
     * Creates a {@code viewWindow} with the given {@code Stage} and {@code Logic}.
     */
    public ViewWindow(Stage root, Logic logic) {
        super(FXML, root);
        this.logic = logic;
        dateFocus = LocalDateTime.now();
        today = LocalDateTime.now();
        drawCalendar();
    }

    public ViewWindow(Logic logic) {
        this(new Stage(), logic);
    }

    /**
     * Shows the view schedule window.
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
        logger.fine("Showing schedule.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void hide() {
        getRoot().hide();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        Map<Integer, TreeSet<Pair<Person, List<Integer>>>> mapDaysToSessions = createSessionMap(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();

        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }

        int dateOffset = LocalDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0)
                .getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.WHITE);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        date.setFill(Color.WHITE);
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        TreeSet<Pair<Person, List<Integer>>> tutorSessions = mapDaysToSessions.get(currentDate);
                        if (tutorSessions != null) {
                            createSessionsOnDay(tutorSessions, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth()
                            && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createSessionsOnDay(TreeSet<Pair<Person, List<Integer>>> tutorSessions, double rectangleHeight,
                                     double rectangleWidth, StackPane stackPane) {
        VBox sessionsBox = new VBox();
        for (Pair<Person, List<Integer>> personAndTime : tutorSessions) {
            Person person = personAndTime.getLeft();
            List<Integer> hourAndMinute = personAndTime.getRight();
            String min = hourAndMinute.get(1) < 10 ? "0" + hourAndMinute.get(1) : "" + hourAndMinute.get(1);
            Text text = new Text("Name: " + person.getName() + "\n" + "Address: " + person.getAddress()
                    + "\n" + "Subject: " + person.getSubject() + "\n" + "Time: "
                    + hourAndMinute.get(0) + ":" + min + "\n");
            text.setFont(Font.font("Segoe UI Light", FontWeight.THIN, 12));
            text.setStroke(Color.WHITE);
            text.setStrokeWidth(1);
            text.setFill(Color.WHITE);

            text.setWrappingWidth(rectangleWidth * 0.9);


            sessionsBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                System.out.println(text.getText());
            });
        }
        sessionsBox.setStyle("-fx-background-color: #515658; -fx-background-radius: 5;");
        sessionsBox.setMaxWidth(rectangleWidth);
        sessionsBox.setAlignment(Pos.TOP_LEFT);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(sessionsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(rectangleWidth, rectangleHeight);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        stackPane.getChildren().add(scrollPane);

    }

    private Map<Integer, TreeSet<Pair<Person, List<Integer>>>> createSessionMap(LocalDateTime dateFocus) {
        List<Person> persons = logic.getFilteredPersonList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        Map<Integer, TreeSet<Pair<Person, List<Integer>>>> mapDaysToSessions = new HashMap<>();

        for (Person person: persons) {
            Set<DateTime> dateTimes = person.getDateTimes();

            for (DateTime dateTimeStr : dateTimes) {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr.value, formatter);
                int year = dateTime.getYear();
                int month = dateTime.getMonthValue();
                int day = dateTime.getDayOfMonth();
                int hour = dateTime.getHour();
                int minute = dateTime.getMinute();

                if (year > dateFocus.getYear() || (year == dateFocus.getYear() && month > dateFocus.getMonthValue())) {
                    break;
                }

                if (year == dateFocus.getYear() && month == dateFocus.getMonthValue()) {
                    TreeSet<Pair<Person, List<Integer>>> sessionSet = mapDaysToSessions.computeIfAbsent(day,
                            k -> new TreeSet<>(new HourMinuteComparator()));
                    Pair<Person, List<Integer>> personAndTime = Pair.of(person, List.of(hour, minute));
                    sessionSet.add(personAndTime);
                    mapDaysToSessions.put(day, sessionSet);
                }
            }
        }
        return mapDaysToSessions;
    }

    private static class HourMinuteComparator implements Comparator<Pair<Person, List<Integer>>> {
        @Override
        public int compare(Pair<Person, List<Integer>> pair1, Pair<Person, List<Integer>> pair2) {
            int hour1 = pair1.getRight().get(0);
            int minute1 = pair1.getRight().get(1);
            int hour2 = pair2.getRight().get(0);
            int minute2 = pair2.getRight().get(1);
            if (hour1 != hour2) {
                return Integer.compare(hour1, hour2);
            }
            return Integer.compare(minute1, minute2);
        }
    }
}
