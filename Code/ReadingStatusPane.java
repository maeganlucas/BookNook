package com.maeganlucas.BookNook;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ReadingStatusPane extends Pane {
    private Button search = new Button("Search");
    private TextField ISBN = new TextField();
    private  ComboBox readingStatus = new ComboBox();
    private Button setReadingStatus = new Button("Set Reading Status");
    private Button editReadingStatus = new Button("Edit Reading Status");
    private Label setReadingStatusText = new Label("Please select/edit your reading status:");
    private Button removeStatus = new Button("Delete Status");

    public ReadingStatusPane() {
        createReadingStatusPane();
    }

    public void createReadingStatusPane() {
        this.setStyle("-fx-background-color: antiquewhite;");
        Label text = new Label("Please enter the ISBN of the book to set/change reading status");
        text.layoutXProperty().bind(this.widthProperty().subtract(text.widthProperty()).divide(2));
        text.setLayoutY(100);
        ISBN.layoutXProperty().bind(this.widthProperty().subtract(ISBN.widthProperty()).divide(2));
        ISBN.setLayoutY(text.getLayoutY() + 50);
        search.layoutXProperty().bind(this.widthProperty().subtract(search.widthProperty()).divide(2));
        search.setLayoutY(ISBN.getLayoutY() + 100);
        setReadingStatusText.layoutXProperty().bind(this.widthProperty().subtract(setReadingStatusText.widthProperty()).divide(2));
        setReadingStatusText.setLayoutY(ISBN.getLayoutY() + 50);
        this.getChildren().addAll(text, ISBN, search, setReadingStatusText);
        setReadingStatusText.setVisible(false);
        readingStatus.getItems().addAll(
                "Read",
                "Reading",
                "TBR"
        );
        readingStatus.setPrefWidth(250);
        readingStatus.layoutXProperty().bind(this.widthProperty().subtract(readingStatus.widthProperty()).divide(2));
        readingStatus.setLayoutY(this.getISBN().getLayoutY() + 100);
        setReadingStatus.layoutXProperty().bind(this.widthProperty().subtract(setReadingStatus.widthProperty()).divide(2.5));
        setReadingStatus.setLayoutY(readingStatus.getLayoutY() + 100);
        editReadingStatus.layoutXProperty().bind(this.widthProperty().subtract(setReadingStatus.widthProperty()).divide(2.0));
        editReadingStatus.setLayoutY(setReadingStatus.getLayoutY());
        removeStatus.layoutXProperty().bind(this.widthProperty().subtract(setReadingStatus.widthProperty()).divide(1.65));
        removeStatus.setLayoutY(setReadingStatus.getLayoutY());
        readingStatus.setVisible(false);
        setReadingStatus.setVisible(false);
        editReadingStatus.setVisible(false);
        removeStatus.setVisible(false);
        this.getChildren().addAll(readingStatus, setReadingStatus, editReadingStatus, removeStatus);
    }

    public Button getSearch() {
        return search;
    }
    public TextField getISBN() {
        return ISBN;
    }
    public Button getSetReadingStatus() {
        return setReadingStatus;
    }
    public ComboBox getReadingStatus() {
        return readingStatus;
    }
    public Label getSetReadingStatusText() {
        return setReadingStatusText;
    }
    public Button getEditReadingStatus() {
        return editReadingStatus;
    }
    public Button getRemoveStatus() {
        return removeStatus;
    }
}
