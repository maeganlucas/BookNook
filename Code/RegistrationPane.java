package com.maeganlucas.BookNook;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
public class RegistrationPane extends Pane {

    private Button register = new Button("Create User!");
    private TextField name = new TextField();
    private TextField username = new TextField();
    private TextField password = new TextField();
    private CheckBox author = new CheckBox();
    private CheckBox reader = new CheckBox();
    public RegistrationPane() {
        createRegistrationPane();
    }
    public void createRegistrationPane() {
        this.setStyle("-fx-background-color: antiquewhite;");
        Label welcomeText = new Label("Welcome To");
        Font font = new Font("Century Schoolbook", 48);
        welcomeText.setFont(font);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.layoutXProperty().bind(this.widthProperty().subtract(welcomeText.widthProperty()).divide(2));

        Image image = new Image(getClass().getResourceAsStream("BookNookLogo.png"));
        ImageView view = new ImageView();
        view.setImage(image);
        view.layoutXProperty().bind(this.widthProperty().subtract(view.getFitWidth()).divide(3));
        view.setLayoutY(welcomeText.getLayoutY() + 50);
        view.setFitHeight(500);
        view.setFitWidth(500);
        view.setSmooth(true);
        view.setCache(true);
        view.setPreserveRatio(true);
        this.getChildren().add(view);

        name.layoutXProperty().bind(this.widthProperty().subtract(name.widthProperty()).divide(2));
        name.setLayoutY(welcomeText.getLayoutY() + 200.0);

        Label nameLabel = new Label("Please enter your preferred name:");
        nameLabel.layoutXProperty().bind(this.widthProperty().subtract(name.widthProperty()).divide(2));
        nameLabel.setLayoutY(name.getLayoutY() - 20.0);

        username.layoutXProperty().bind(this.widthProperty().subtract(username.widthProperty()).divide(2));
        username.setLayoutY(name.getLayoutY() + 75.0);

        Label usernameLabel = new Label("Please enter your username:");
        usernameLabel.layoutXProperty().bind(this.widthProperty().subtract(username.widthProperty()).divide(2));
        usernameLabel.setLayoutY(username.getLayoutY() - 20.0);

        password.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        password.setLayoutY(username.getLayoutY() + 75.0);

        Label passwordLabel = new Label("Please enter your password:");
        passwordLabel.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        passwordLabel.setLayoutY(password.getLayoutY() - 20.0);

        Label role = new Label("Are you an author or a reader?");
        role.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        role.setLayoutY(password.getLayoutY() + 50.0);

        author.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        author.setLayoutY(role.getLayoutY() + 20.0);

        Label authorLabel = new Label("Author");
        authorLabel.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(1.85));
        authorLabel.setLayoutY(author.getLayoutY());

        reader.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        reader.setLayoutY(author.getLayoutY() + 20.0);

        Label readerLabel = new Label("Reader");
        readerLabel.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(1.85));
        readerLabel.setLayoutY(reader.getLayoutY());


        register.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        register.setLayoutY(reader.getLayoutY() + 75.0);

        this.getChildren().add(welcomeText);
        this.getChildren().addAll(name, nameLabel);
        this.getChildren().addAll(username, usernameLabel, password, passwordLabel);
        this.getChildren().addAll(role, author, authorLabel, reader, readerLabel);
        this.getChildren().add(register);
    }

    public Button getRegister() {
        return register;
    }

    public TextField getName() {
        return name;
    }

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    public CheckBox getAuthor() {
        return author;
    }

    public CheckBox getReader() {
        return reader;
    }
}

