package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendarController {

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private GridPane calendarGrid;

    @FXML
    public void initialize() {
        // Padding year and month selectors
        populateYearAndMonthSelectors();

        // Updating calendar
        updateCalendar(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        // Add year and month listener
        yearComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateCalendar(newVal, getMonthNumber(monthComboBox.getValue()));
            }
        });

        monthComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateCalendar(yearComboBox.getValue(), getMonthNumber(newVal));
            }
        });
    }

    private void populateYearAndMonthSelectors() {
        for (int year = LocalDate.now().getYear() - 10; year <= LocalDate.now().getYear() + 10; year++) {
            yearComboBox.getItems().add(year);
        }
        yearComboBox.setValue(LocalDate.now().getYear());

        for (Month month : Month.values()) {
            monthComboBox.getItems().add(month.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        monthComboBox.setValue(Month.of(LocalDate.now().getMonthValue()).getDisplayName(TextStyle.FULL, Locale.getDefault()));
    }

    private int getMonthNumber(String monthName) {
        return Month.valueOf(monthName.toUpperCase(Locale.ROOT)).getValue();
    }

    private void updateCalendar(int year, int month) {
        calendarGrid.getChildren().clear(); // Clear current date

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        int daysInMonth = yearMonth.lengthOfMonth();

        // Set rows and columns of the grid.
        setupGridPane();

        for (int i = 0; i < firstDayOfWeek - 1; i++) {
            calendarGrid.add(new Label(""), i, 0);
        }

        // Padding date
        for (int day = 1, gridCount = firstDayOfWeek; day <= daysInMonth; day++, gridCount++) {
            Label dayLabel = new Label(Integer.toString(day));
            dayLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            dayLabel.setStyle("-fx-border-color: black; -fx-alignment: center;");
            int row = (gridCount - 1) / 7;
            int col = (gridCount - 1) % 7;
            calendarGrid.add(dayLabel, col, row);

            // Add click event handler
            dayLabel.setOnMouseClicked(event -> {
                // Load and display exampler.fxml
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("example.fxml"));
                    VBox root = loader.load();
                    Scene scene = new Scene(root);
                    // Get the stage from the current dayLabel
                    Stage stage = (Stage) dayLabel.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void setupGridPane() {
        calendarGrid.getRowConstraints().clear();
        calendarGrid.getColumnConstraints().clear();
        for (int i = 0; i < 6; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30);
            calendarGrid.getRowConstraints().add(rowConstraints);
        }
        for (int i = 0; i < 7; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPrefWidth(30);
            calendarGrid.getColumnConstraints().add(colConstraints);
        }
    }
}
