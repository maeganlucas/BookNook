package com.maeganlucas.BookNook;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
public class ReaderProfileUpdate extends Pane {

    private TextField name = new TextField();
    private TextField username = new TextField();
    private TextField password = new TextField();
    private Button update = new Button("Update Profile");

    public ReaderProfileUpdate() {
        createReaderProfileUpdate();
    }

    public void createReaderProfileUpdate() {
        Label edit = new Label("Please edit any or all fields!");
        edit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        edit.layoutXProperty().bind(this.widthProperty().subtract(edit.widthProperty()).divide(2));
        edit.setLayoutY(100);

        this.setStyle("-fx-background-color: antiquewhite;");
        name.layoutXProperty().bind(this.widthProperty().subtract(name.widthProperty()).divide(2));
        name.setLayoutY(200.0);

        Label nameLabel = new Label("Preferred Name");
        nameLabel.layoutXProperty().bind(this.widthProperty().subtract(name.widthProperty()).divide(2));
        nameLabel.setLayoutY(name.getLayoutY() - 20.0);

        username.layoutXProperty().bind(this.widthProperty().subtract(username.widthProperty()).divide(2));
        username.setLayoutY(name.getLayoutY() + 75.0);

        Label usernameLabel = new Label("Username");
        usernameLabel.layoutXProperty().bind(this.widthProperty().subtract(username.widthProperty()).divide(2));
        usernameLabel.setLayoutY(username.getLayoutY() - 20.0);

        password.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        password.setLayoutY(username.getLayoutY() + 75.0);

        Label passwordLabel = new Label("Password");
        passwordLabel.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        passwordLabel.setLayoutY(password.getLayoutY() - 20.0);

        update.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        update.setLayoutY(password.getLayoutY() + 75.0);

        this.getChildren().addAll(name, nameLabel, username, usernameLabel, password, passwordLabel, edit, update);

    }
    public TextField getUsername() {
        return username;
    }
    public TextField getName() {
        return name;
    }
    public TextField getPassword() {
        return password;
    }
    public Button getUpdate() {
        return update;
    }
}
