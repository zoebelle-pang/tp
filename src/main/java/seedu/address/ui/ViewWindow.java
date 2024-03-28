package seedu.address.ui;

import org.apache.commons.lang3.tuple.Pair;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollBar;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;


public class ViewWindow extends UiPart<Stage> {
    LocalDateTime dateFocus;
    LocalDateTime today;
    private final Logic logic;

    private static final Logger logger = LogsCenter.getLogger(ViewWindow.class);
    private static final String FXML = "Calendar.fxml";

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

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

    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        Map<Integer, List<Person>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();

        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }

        int dateOffset = LocalDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0)
                .getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.WHITE);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        date.setFill(Color.WHITE);
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<Person> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null) {
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<Person> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            DateTime dateTimeStr = calendarActivities.get(k).getDateTimes();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr.value, formatter);
            int hour = dateTime.getHour();
            int minute = dateTime.getMinute();
            Text text = new Text("Name: " + calendarActivities.get(k).getName() + "\n" + "Address: "
                    + calendarActivities.get(k).getAddress() + "\n" + "Subject: "
                    + calendarActivities.get(k).getSubject() + "\n");
            text.setFont(Font.font("Segoe UI Light", FontWeight.THIN, 12)); // Set font to Garamond, bold, size 14
            text.setStroke(Color.WHITE); // Set stroke color to black
            text.setStrokeWidth(1); // Increase stroke width for a thicker appearance
            text.setFill(Color.WHITE); // Set text color to blue

            text.setWrappingWidth(rectangleWidth*0.9);


            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setStyle("-fx-background-color: #515658; -fx-background-radius: 5;");
        calendarActivityBox.setMaxWidth(rectangleWidth);
        calendarActivityBox.setAlignment(Pos.TOP_LEFT);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(calendarActivityBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(rectangleWidth, rectangleHeight);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        stackPane.getChildren().add(scrollPane);

    }

    private Map<Integer, List<Person>> getCalendarActivitiesMonth(LocalDateTime dateFocus) {
        List<Person> calendarActivities = logic.getFilteredPersonList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        Map<Integer, List<Person>> calendarActivityMap = new HashMap<>();

        for (Person person: calendarActivities) {
            Set<DateTime> activityDate = person.getDateTimes();

            for (DateTime dateTimeStr : activityDate) {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr.value, formatter);
                int year = dateTime.getYear();
                int month = dateTime.getMonthValue();
                int day = dateTime.getDayOfMonth();
                int hour = dateTime.getHour();
                int minute = dateTime.getMinute();

                if (year > dateFocus.getYear() && month > dateFocus.getMonthValue()) {
                    break;
                }

                if (year == dateFocus.getYear() && month == dateFocus.getMonthValue()) {

                    if (!calendarActivityMap.containsKey(day)) {
                        //calendarActivityMap.put(day, List.of(person));
                        calendarActivityMap.put(day, Pair.of(List.of(person), List.of(hour, minute)));
                    } else {
                        List<Person> OldListByDate = calendarActivityMap.get(day);

                        List<Person> newList = new ArrayList<>(OldListByDate);
                        newList.add(person);
                        calendarActivityMap.put(day, newList);
                    }
                }
            }
        }
        return calendarActivityMap;
    }
}
