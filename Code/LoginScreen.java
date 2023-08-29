package com.maeganlucas.BookNook;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class LoginScreen extends Pane {
    private Button login = new Button("Login");
    private Button newUser = new Button("Click here to create a new user");
    private TextField username = new TextField("Username");
    private TextField password = new TextField("Password");

    public LoginScreen() {
        createHomeScreen();
    }
    public void createHomeScreen() {
        this.setStyle("-fx-background-color: antiquewhite;");
        Image image = new Image(getClass().getResourceAsStream("BookNookLogo.png"));
        ImageView view = new ImageView();
        view.setImage(image);
        view.layoutXProperty().bind(this.widthProperty().subtract(view.getFitWidth()).divide(3));
        view.setLayoutY(100.0);
        view.setFitHeight(500);
        view.setFitWidth(500);
        view.setSmooth(true);
        view.setCache(true);
        view.setPreserveRatio(true);

        username.layoutXProperty().bind(this.widthProperty().subtract(username.widthProperty()).divide(2));
        username.setLayoutY(view.getLayoutY() + 150.0);

        password.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2));
        password.setLayoutY(username.getLayoutY() + 35.0);

        newUser.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(2.25));
        newUser.setLayoutY(password.getLayoutY() + 75.0);

        login.layoutXProperty().bind(this.widthProperty().subtract(password.widthProperty()).divide(1.65));
        login.setLayoutY(newUser.getLayoutY());

        this.getChildren().add(view);
        this.getChildren().addAll(username, password);
        this.getChildren().addAll(newUser, login);
    }

    public Button getNewUser() {
        return newUser;
    }
    public Button getLogin() {
        return login;
    }
    public TextField getUsername() {
        return username;
    }
    public TextField getPassword() {
        return password;
    }
}
