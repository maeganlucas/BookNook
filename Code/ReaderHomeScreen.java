package com.maeganlucas.BookNook;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class ReaderHomeScreen extends BorderPane {
    private MenuBar menu;
    private MenuItem menuBook,menuRead, menuReading, menuTBR;
    private MenuItem menuSeeReviews, menuUpdateReview, menuDeleteReview;
    private MenuItem menuUpdateProfile, menuDeleteProfile;
    private MenuItem menuLogout;
    public ReaderHomeScreen() {
        createReaderHomeScreen();
    }

    public void createReaderHomeScreen() {
        this.setStyle("-fx-background-color: antiquewhite;");
        menu = new MenuBar();
        Menu menuBooks = new Menu("Books");
        menuBook = new MenuItem("Search Books");
        menuRead = new MenuItem("Read Books");
        menuReading = new MenuItem("Currently Reading");
        menuTBR = new MenuItem("TBR");
        menuBooks.getItems().addAll(menuBook, menuRead, menuReading, menuTBR);
        Menu menuReviews =  new Menu("Reviews");
        menuUpdateReview = new MenuItem("Update Review");
        menuDeleteReview = new MenuItem("Delete Review");
        menuSeeReviews = new MenuItem("See Reviews");
        menuReviews.getItems().addAll(menuUpdateReview, menuDeleteReview, menuSeeReviews);
        Menu menuSettings = new Menu("Settings");
        menuUpdateProfile = new MenuItem("Update Profile");
        menuDeleteProfile = new MenuItem("Delete Profile");
        menuSettings.getItems().addAll(menuUpdateProfile, menuDeleteProfile);
        Menu logout = new Menu("Logout");
        menuLogout = new MenuItem("Logout");
        logout.getItems().addAll(menuLogout);
        menu.getMenus().addAll(menuBooks, menuReviews, menuSettings, logout);
        this.setTop(menu);
    }
    public MenuItem getMenuBook() {
        return menuBook;
    }
    public MenuItem getMenuRead() {
        return menuRead;
    }
    public MenuItem getMenuReading() {
        return menuReading;
    }
    public MenuItem getMenuTBR() { return menuTBR; }
    public MenuItem getMenuUpdateReview() {
        return menuUpdateReview;
    }
    public MenuItem getMenuDeleteReview() {
        return menuDeleteReview;
    }
    public MenuItem getMenuUpdateProfile() {
        return menuUpdateProfile;
    }
    public MenuItem getMenuDeleteProfile() {
        return menuDeleteProfile;
    }
    public MenuItem getMenuSeeReviews() {
        return menuSeeReviews;
    }
    public MenuItem getMenuLogout() {
        return menuLogout;
    }
    public MenuBar getMenu() {
        return menu;
    }
}
