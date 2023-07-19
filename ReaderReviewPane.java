package com.maeganlucas.BookNook;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ReaderReviewPane extends BorderPane {
    Pane pane = new Pane();
    private TextField dsMonth = new TextField();
    private TextField dsDay = new TextField();
    private TextField dsYear = new TextField();
    private TextField dfMonth = new TextField();
    private TextField dfDay = new TextField();
    private TextField dfYear = new TextField();
    private Button getReviews = new Button("Get Review Breakdown");

    public ReaderReviewPane() {
        createReaderReviewPane();
    }
    public void createReaderReviewPane() {
        pane.setStyle("-fx-background-color: antiquewhite;");
        Label enter = new Label("Please enter dates to see the review breakdown:");
        enter.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        enter.layoutXProperty().bind(pane.widthProperty().subtract(enter.widthProperty()).divide(2));
        enter.setLayoutY(25);
        pane.getChildren().add(enter);

        Label dateStartedText = new Label("Start: ");
        dateStartedText.setLayoutY(enter.getLayoutY() + 50);
        dateStartedText.setLayoutX(510);
        dsMonth.setLayoutX(dateStartedText.getLayoutX() + 50);
        dsMonth.setLayoutY(dateStartedText.getLayoutY() - 3);
        dsMonth.setPrefWidth(30);
        dsMonth.setAlignment(Pos.CENTER);
        Label date1 = new Label("/ ");
        date1.setLayoutX(dsMonth.getLayoutX() + 45);
        date1.setLayoutY(dateStartedText.getLayoutY());
        dsDay.setLayoutX(dsMonth.getLayoutX() + 60);
        dsDay.setLayoutY(dateStartedText.getLayoutY() - 3);
        dsDay.setPrefWidth(30);
        dsDay.setAlignment(Pos.CENTER);
        Label date2 = new Label("/ ");
        date2.setLayoutX(dsDay.getLayoutX() + 45);
        date2.setLayoutY(dateStartedText.getLayoutY());
        dsYear.setLayoutX(dsDay.getLayoutX() + 60);
        dsYear.setLayoutY(dateStartedText.getLayoutY() - 3);
        dsYear.setPrefWidth(50);
        dsYear.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(dateStartedText, dsMonth, date1, dsDay, date2, dsYear);

        Label dateFinishedText = new Label("End: ");
        dateFinishedText.setLayoutY(dateStartedText.getLayoutY());
        dateFinishedText.setLayoutX(dsYear.getLayoutX() + 100);
        dfMonth.setLayoutX(dateFinishedText.getLayoutX() + 50);
        dfMonth.setLayoutY(dateFinishedText.getLayoutY() - 3);
        dfMonth.setPrefWidth(30);
        dfMonth.setAlignment(Pos.CENTER);
        Label date3 = new Label("/ ");
        date3.setLayoutX(dfMonth.getLayoutX() + 45);
        date3.setLayoutY(dateStartedText.getLayoutY());
        dfDay.setLayoutX(dfMonth.getLayoutX() + 60);
        dfDay.setLayoutY(dateFinishedText.getLayoutY() - 3);
        dfDay.setPrefWidth(30);
        dfDay.setAlignment(Pos.CENTER);
        Label date4 = new Label("/ ");
        date4.setLayoutX(dfDay.getLayoutX() + 45);
        date4.setLayoutY(dateFinishedText.getLayoutY());
        dfYear.setLayoutX(dfDay.getLayoutX() + 60);
        dfYear.setLayoutY(dateFinishedText.getLayoutY() - 3);
        dfYear.setPrefWidth(50);
        dfYear.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(dateFinishedText, dfMonth, date3, dfDay, date4, dfYear);

        getReviews.layoutXProperty().bind(pane.widthProperty().subtract(getReviews.widthProperty()).divide(2));
        getReviews.setLayoutY(dfYear.getLayoutY() + 75);
        pane.getChildren().add(getReviews);
        this.setTop(pane);
    }

    public TextField getDsMonth() {
        return dsMonth;
    }

    public TextField getDsDay() {
        return dsDay;
    }

    public TextField getDsYear() {
        return dsYear;
    }

    public TextField getDfMonth() {
        return dfMonth;
    }

    public TextField getDfDay() {
        return dfDay;
    }

    public TextField getDfYear() {
        return dfYear;
    }

    public Button getGetReviews() {
        return getReviews;
    }

    public Pane getPane() {
        return pane;
    }
}
