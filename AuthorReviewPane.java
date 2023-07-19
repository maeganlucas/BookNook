package com.maeganlucas.BookNook;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AuthorReviewPane extends BorderPane {
    private ComboBox book = new ComboBox();
    private Pane chooseBook = new Pane();
    private Manager mgr = new Manager();
    private String[] args;
    private String username;
    private Button seeReviews = new Button("See Reviews");

    public AuthorReviewPane(String username) {
        this.username = username;
        createAuthorReviewPane();
    }
    public void createAuthorReviewPane() {
        mgr.main(args);
        this.setStyle("-fx-background-color: antiquewhite;");
        Label choose = new Label("Choose book to see reviews: ");
        choose.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        choose.layoutXProperty().bind(this.widthProperty().subtract(choose.widthProperty()).divide(3.25));
        choose.setLayoutY(25);
        mgr.printAuthorsBooks(username);
        for(int i = 0; i < mgr.getAuthorsBooks().size(); i++){
            String[] books = mgr.getAuthorsBooks().get(i);
            String title = books[0];
            String ISBN = books[3];
            String newBook = title + " : " + ISBN;
            book.getItems().add(newBook);
        }
        book.layoutXProperty().bind(this.widthProperty().subtract(choose.widthProperty()).divide(2.25));
        book.setLayoutY(choose.getLayoutY() - 3);
        seeReviews.layoutXProperty().bind(this.widthProperty().subtract(book.widthProperty()).divide(1.45));
        seeReviews.setLayoutY(choose.getLayoutY() - 3);
        chooseBook.getChildren().addAll(book, choose, seeReviews);
        this.setTop(chooseBook);
    }

    public ComboBox getBook() {
        return book;
    }
    public Button getSeeReviews() {
        return seeReviews;
    }
}
