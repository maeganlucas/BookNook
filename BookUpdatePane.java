package com.maeganlucas.BookNook;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class BookUpdatePane extends Pane {

    private TextField title, author, series, volume, publisher, ISBN, categories;
    private TextArea synopsis;
    private ComboBox genre;
    private Button updateBook;
    private String isbn;
    private Manager mgr = new Manager();


    public BookUpdatePane() {
        createBookUpdatePane();
    }

    public void createBookUpdatePane() {
        this.setStyle("-fx-background-color: antiquewhite;");
        Label titleLabel = new Label("Title");
        titleLabel.setLayoutY(50);
        titleLabel.setLayoutX(100);
        title = new TextField();
        title.setLayoutY(titleLabel.getLayoutY()-3);
        title.setLayoutX(titleLabel.getLayoutX() + 75);
        this.getChildren().addAll(title, titleLabel);
        Label authorLabel = new Label("Author");
        authorLabel.setLayoutX(titleLabel.getLayoutX());
        authorLabel.setLayoutY((titleLabel.getLayoutY() + 50 ));
        author = new TextField();
        author.setLayoutX(authorLabel.getLayoutX() + 75);
        author.setLayoutY(authorLabel.getLayoutY() - 3);
        this.getChildren().addAll(author, authorLabel);
        Label seriesLabel = new Label("Series");
        seriesLabel.setLayoutX(authorLabel.getLayoutX());
        seriesLabel.setLayoutY(authorLabel.getLayoutY() + 50);
        series = new TextField();
        series.setLayoutX(seriesLabel.getLayoutX() + 75);
        series.setLayoutY((seriesLabel.getLayoutY() - 3));
        series.setText(mgr.getBookDetails()[2]);
        this.getChildren().addAll(series, seriesLabel);
        Label volumeLabel = new Label("Volume");
        volumeLabel.setLayoutX(seriesLabel.getLayoutX() + 450);
        volumeLabel.setLayoutY(seriesLabel.getLayoutY());
        volume = new TextField();
        volume.setLayoutX(volumeLabel.getLayoutX() + 75);
        volume.setLayoutY(volumeLabel.getLayoutY() - 3);
        this.getChildren().addAll(volume, volumeLabel);
        Label ISBNLabel = new Label("ISBN");
        ISBNLabel.setLayoutX(seriesLabel.getLayoutX());
        ISBNLabel.setLayoutY(seriesLabel.getLayoutY() + 50);
        ISBN = new TextField();
        ISBN.setLayoutX(ISBNLabel.getLayoutX() + 75);
        ISBN.setLayoutY(ISBNLabel.getLayoutY() - 3);
        this.getChildren().addAll(ISBN, ISBNLabel);
        Label genreLabel = new Label("Genre");
        genreLabel.setLayoutX(ISBNLabel.getLayoutX());
        genreLabel.setLayoutY(ISBNLabel.getLayoutY() + 50);
        genre = new ComboBox();
        genre.getItems().addAll(
                "Romance",
                "Fantasy",
                "Mystery",
                "Thriller",
                "Historical Fiction",
                "Science Fiction"
        );
        genre.setLayoutX(genreLabel.getLayoutX() + 75);
        genre.setLayoutY(genreLabel.getLayoutY() - 3);
        this.getChildren().addAll(genre, genreLabel);
        Label categoriesLabel = new Label("Categories");
        categoriesLabel.setLayoutX(volumeLabel.getLayoutX());
        categoriesLabel.setLayoutY(volumeLabel.getLayoutY() + 100);
        categories = new TextField();
        categories.setLayoutX(categoriesLabel.getLayoutX() + 75);
        categories.setLayoutY(categoriesLabel.getLayoutY() - 3);
        this.getChildren().addAll(categories, categoriesLabel);
        Label publisherLabel = new Label("Publisher");
        publisherLabel.setLayoutX(genreLabel.getLayoutX());
        publisherLabel.setLayoutY(genreLabel.getLayoutY() + 50);
        publisher = new TextField();
        publisher.setLayoutX(publisherLabel.getLayoutX() + 75);
        publisher.setLayoutY(publisherLabel.getLayoutY() - 3);
        this.getChildren().addAll(publisher, publisherLabel);
        Label synopsisLabel = new Label("Synopsis");
        synopsisLabel.setLayoutX(publisherLabel.getLayoutX());
        synopsisLabel.setLayoutY(publisherLabel.getLayoutY() + 50);
        synopsis = new TextArea();
        synopsis.setLayoutX(synopsisLabel.getLayoutX() + 75);
        synopsis.setLayoutY(synopsisLabel.getLayoutY() - 3);
        synopsis.setPrefWidth(600);
        synopsis.setPrefHeight(150);
        synopsis.setWrapText(true);
        this.getChildren().addAll(synopsis, synopsisLabel);
        updateBook = new Button("Update Book");
        updateBook.setLayoutX(425);
        updateBook.setLayoutY(600);
        this.getChildren().add(updateBook);
    }

    public void setISBN(String isbn) {
        this.isbn = isbn;

    }

    public TextField getTitle() {
        return title;
    }

    public TextField getAuthor() {
        return author;
    }

    public TextField getSeries() {
        return series;
    }

    public TextField getVolume() {
        return volume;
    }

    public TextField getPublisher() {
        return publisher;
    }

    public TextField getISBN() {
        return ISBN;
    }

    public TextField getCategories() {
        return categories;
    }

    public TextArea getSynopsis() {
        return synopsis;
    }

    public Button getUpdateBook() {
        return updateBook;
    }
    public String getIsbn() {
        return isbn;
    }

    public ComboBox getGenre() {
        return genre;
    }
}
