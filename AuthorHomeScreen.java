package com.maeganlucas.BookNook;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class AuthorHomeScreen extends BorderPane {

    private MenuBar menuBar;
    private MenuItem menuCreate, menuUpdate, menuDelete, menuReviews, menuLogout, menuBack, menuUpdateProfile, menuDeleteProfile;

    public AuthorHomeScreen() {
        createAuthorHomeScreen();

    }

    public void createAuthorHomeScreen() {
        this.setStyle("-fx-background-color: antiquewhite;");
        menuBar = new MenuBar();
        Menu books = new Menu("Books");
        menuCreate = new MenuItem("Create Book");
        menuUpdate = new MenuItem("Update Book");
        menuDelete = new MenuItem("Delete Book");
        Menu reviews = new Menu("Reviews");
        menuReviews = new MenuItem("See Reviews");
        Menu logout = new Menu("Logout");
        menuBack = new MenuItem("Back to Home");
        menuLogout = new MenuItem("Logout");
        Menu settings = new Menu("Settings");
        menuUpdateProfile = new MenuItem("Update Profile");
        menuDeleteProfile = new MenuItem("Delete Profile");

        books.getItems().addAll(menuCreate, menuUpdate, menuDelete);
        reviews.getItems().add(menuReviews);
        logout.getItems().addAll(menuBack, menuLogout);
        settings.getItems().addAll(menuUpdateProfile, menuDeleteProfile);
        menuBar.getMenus().addAll(books, reviews, settings, logout);
        this.setTop(menuBar);

    }

    public MenuItem getMenuCreate() {
        return menuCreate;
    }

    public MenuItem getMenuUpdate() {
        return menuUpdate;
    }

    public MenuItem getMenuDelete() {
        return menuDelete;
    }

    public MenuItem getMenuReviews() {
        return menuReviews;
    }

    public MenuItem getMenuUpdateProfile() {
        return menuUpdateProfile;
    }

    public MenuItem getMenuDeleteProfile() {
        return menuDeleteProfile;
    }

    public MenuItem getMenuLogout() {
        return menuLogout;
    }

    public MenuItem getMenuBack() {
        return menuBack;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
