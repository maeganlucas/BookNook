package com.maeganlucas.BookNook;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class BookSearchPane extends BorderPane {
    private Pane searchBar = new Pane();
    private TextField search = new TextField();
    private Button searchButton = new Button("Search");

    public BookSearchPane() {
        createBookSearchPane();
    }

    public void createBookSearchPane() {
        this.setStyle("-fx-background-color: antiquewhite;");
        Label searchBooks = new Label("Search for a book:");
        searchBooks.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        searchBooks.layoutXProperty().bind(this.widthProperty().subtract(searchBooks.widthProperty()).divide(4.25));
        searchBooks.setLayoutY(25);
        search = new TextField();
        search.setPrefWidth(500);
        search.layoutXProperty().bind(this.widthProperty().subtract(searchBooks.widthProperty()).divide(3));
        search.setLayoutY(searchBooks.getLayoutY() - 3);
        searchButton.layoutXProperty().bind(this.widthProperty().subtract(searchButton.widthProperty()).divide(1.45));
        searchButton.setLayoutY(searchBooks.getLayoutY() - 3);

        searchBar.getChildren().addAll(searchBooks, search, searchButton);
        this.setTop(searchBar);
    }

    public TextField getSearch() {
        return search;
    }

    public Button getSearchButton() {
        return searchButton;
    }
}
