package com.maeganlucas.BookNook;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.ArrayList;

public class MainUI extends Application {
    private BorderPane pane = new BorderPane();
    private GridPane authorPane = new GridPane();

    private GridPane readerPane = new GridPane();
    private LoginScreen loginPane = new LoginScreen();
    private String username;



    private AuthorHomeScreen authorHomeScreen = new AuthorHomeScreen();
    private AuthorProfileUpdate authorProfileUpdate = new AuthorProfileUpdate();
    private ReaderHomeScreen readerHomeScreen = new ReaderHomeScreen();
    private ReaderProfileUpdate readerProfileUpdate = new ReaderProfileUpdate();
    private Manager mgr = new Manager();
    private String[] args;


    private RegistrationPane registrationPane = new RegistrationPane();

    private BookCreationPane bookCreationPane = new BookCreationPane();
    private BookUpdatePane bookUpdatePane = new BookUpdatePane();
    private BookSearchPane bookSearchPane = new BookSearchPane();
    private ReaderReviewPane readerReviewPane = new ReaderReviewPane();
    private Popup updatePopup = new Popup();
    private Popup deletePopup = new Popup();
    private Popup deleteProfilePopup = new Popup();
    private ReadingStatusPane readingStatusPane = new ReadingStatusPane();
    private String readingStatusISBN;
    final PieChart storylineChart = new PieChart();
    final PieChart plotChart = new PieChart();
    final PieChart settingChart = new PieChart();
    final PieChart spiceChart = new PieChart();
    final PieChart charactersChart = new PieChart();
    final PieChart wbChart = new PieChart();
    final PieChart wsChart = new PieChart();
    final PieChart genres = new PieChart();


    int user;



    public MainUI() {
        createGUI();
        mgr.main(args);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Book Nook");
        Scene scene = new Scene(pane, 1000, 750);
        stage.setScene(scene);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        stage.setX(screen.getMinX());
        stage.setY(screen.getMinY());
        stage.setWidth(screen.getWidth());
        stage.setHeight(screen.getHeight());
        stage.show();

        authorHomeScreen.getMenuUpdate().setOnAction(e4 -> {
            updatePopup.getContent().clear();
            deletePopup.hide();
            createUpdate();
            updatePopup.show(stage);
            authorHomeScreen.setCenter(null);

        });

        authorHomeScreen.getMenuDelete().setOnAction(e5 -> {
            deletePopup.getContent().clear();
            updatePopup.hide();
            deletePopup.setHeight(200);
            deletePopup.setWidth(200);
            Label text = new Label("Please enter the ISBN of the book to delete:");
            TextField ISBN = new TextField();
            Button delete = new Button("Delete");
            delete.setLayoutX(ISBN.getLayoutX() + 148);
            delete.setLayoutY(ISBN.getLayoutY() + 100);
            text.setLayoutX(65);
            text.setLayoutY(0);
            ISBN.setLayoutX(100);
            ISBN.setLayoutY(text.getLayoutY() + 50);
            deletePopup.getContent().addAll(text, ISBN, delete);
            deletePopup.setAnchorX(575);
            deletePopup.setAnchorY(325);
            deletePopup.show(stage);
            authorHomeScreen.setCenter(null);
            delete.setOnAction(e -> {
                if (ISBN.getText().length() != 13) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Uh oh...");
                    error.setHeaderText("Invalid ISBN");
                    error.setContentText("Please enter a valid ISBN. They are strings of 13 numbers.");
                    error.show();
                    error.setY(500);
                } else {
                    String isbn = ISBN.getText();
                    deletePopup.hide();
                    mgr.deleteBook(isbn);
                    removeBooks();
                    printBooks();
                }
            });

        });

        authorHomeScreen.getMenuDeleteProfile().setOnAction(e9 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.getContent().clear();
            createDeletePopup();
            deleteProfilePopup.show(stage);
        });

        readerHomeScreen.getMenuUpdateReview().setOnAction(e19 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            updatePopup.getContent().clear();
            createUpdate();
            updatePopup.show(stage);
            readerHomeScreen.setCenter(null);
        });

        readerHomeScreen.getMenuDeleteReview().setOnAction(e ->  {
            deletePopup.getContent().clear();
            updatePopup.hide();
            deletePopup.setHeight(200);
            deletePopup.setWidth(200);
            Label text = new Label("Please enter the ISBN of the review to delete:");
            TextField ISBN = new TextField();
            Button delete = new Button("Delete");
            delete.setLayoutX(ISBN.getLayoutX() + 148);
            delete.setLayoutY(ISBN.getLayoutY() + 100);
            text.setLayoutX(65);
            text.setLayoutY(0);
            ISBN.setLayoutX(100);
            ISBN.setLayoutY(text.getLayoutY() + 50);
            deletePopup.getContent().addAll(text, ISBN, delete);
            deletePopup.setAnchorX(575);
            deletePopup.setAnchorY(325);
            deletePopup.show(stage);
            readerHomeScreen.setCenter(null);
            delete.setOnAction(e2 -> {
                if (ISBN.getText().length() != 13) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Uh oh...");
                    error.setHeaderText("Invalid ISBN");
                    error.setContentText("Please enter a valid ISBN. They are strings of 13 numbers.");
                    error.show();
                    error.setY(500);
                } else {
                    deletePopup.hide();
                    mgr.deleteReview(username, ISBN.getText());
                    removeBooks();
                    printReadBooks();
                    readerHomeScreen.setCenter(readerPane);
                    readerPane.requestFocus();
                }
            });
        });

        readerHomeScreen.getMenuDeleteProfile().setOnAction(e9 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.getContent().clear();
            createDeletePopup();
            deleteProfilePopup.show(stage);
        });

        authorHomeScreen.getMenuReviews().setOnAction((e7 -> {
            AuthorReviewPane authorReview = new AuthorReviewPane(username);
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            authorHomeScreen.setCenter(authorReview);
            authorReview.requestFocus();

            authorReview.getSeeReviews().setOnAction(e8 -> {
                String[] book = authorReview.getBook().getValue().toString().split(" : ");
                ArrayList<int[]> reviews = mgr.getAuthorReviews(book[1]);
                Pane charts = new Pane();
                createCharts(reviews);
                charts.getChildren().addAll(storylineChart, plotChart, settingChart, spiceChart, charactersChart, wbChart, wsChart);
                authorReview.setCenter(charts);
                charts.requestFocus();
                final Label caption = new Label("");
                caption.setTextFill(Color.BLACK);
                caption.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                Label storylineText = new Label("Storyline");
                storylineText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                storylineText.setLayoutX(82);
                storylineText.setLayoutY(10);
                charts.getChildren().add(storylineText);
                for (final PieChart.Data data : storylineChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e -> {
                                caption.setLayoutX(storylineChart.getLayoutX() + 210);
                                caption.setLayoutY(storylineChart.getLayoutY() + 93);
                                caption.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption);
                    });
                }

                Label plotText = new Label("Plot");
                plotText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                plotText.setLayoutX(storylineText.getLayoutX() + 15);
                plotText.setLayoutY(storylineText.getLayoutY() + 210);
                charts.getChildren().add(plotText);
                final Label caption2 = new Label("");
                caption2.setTextFill(Color.BLACK);
                caption2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : plotChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e -> {
                                caption2.setLayoutX(plotChart.getLayoutX() + 210);
                                caption2.setLayoutY(plotChart.getLayoutY() + 93);
                                caption2.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption2);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption2);
                    });
                }

                Label spiceText = new Label("Spice");
                spiceText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                spiceText.setLayoutX(storylineText.getLayoutX() + 610);
                spiceText.setLayoutY(storylineText.getLayoutY());
                charts.getChildren().add(spiceText);
                final Label caption4 = new Label("");
                caption4.setTextFill(Color.BLACK);
                caption4.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : spiceChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e -> {
                                caption4.setLayoutX(spiceChart.getLayoutX() + 210);
                                caption4.setLayoutY(spiceChart.getLayoutY() + 93);
                                caption4.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption4);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption4);
                    });
                }

                Label charactersText = new Label("Characters");
                charactersText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                charactersText.setLayoutX(plotText.getLayoutX() + 575);
                charactersText.setLayoutY(plotText.getLayoutY());
                charts.getChildren().add(charactersText);
                final Label caption5 = new Label("");
                caption5.setTextFill(Color.BLACK);
                caption5.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : charactersChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e -> {
                                caption5.setLayoutX(charactersChart.getLayoutX() + 210);
                                caption5.setLayoutY(charactersChart.getLayoutY() + 93);
                                caption5.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption5);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption5);
                    });
                }

                Label settingText = new Label("Setting");
                settingText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                settingText.setLayoutX(charactersText.getLayoutX() + 17);
                settingText.setLayoutY(charactersText.getLayoutY() + 210);
                charts.getChildren().add(settingText);
                final Label caption3 = new Label("");
                caption3.setTextFill(Color.BLACK);
                caption3.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : settingChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e -> {
                                caption3.setLayoutX(settingChart.getLayoutX() + 210);
                                caption3.setLayoutY(settingChart.getLayoutY() + 93);
                                caption3.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption3);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption3);
                    });
                }

                Label wbText = new Label("World Building");
                wbText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                wbText.setLayoutX(spiceText.getLayoutX() + 565);
                wbText.setLayoutY(spiceText.getLayoutY());
                charts.getChildren().add(wbText);
                final Label caption6 = new Label("");
                caption6.setTextFill(Color.BLACK);
                caption6.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : wbChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e -> {
                                caption6.setLayoutX(wbChart.getLayoutX() + 210);
                                caption6.setLayoutY(wbChart.getLayoutY() + 93);
                                caption6.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption6);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption6);
                    });
                }

                Label wsText = new Label("Writing Style");
                wsText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                wsText.setLayoutX(charactersText.getLayoutX() + 595);
                wsText.setLayoutY(charactersText.getLayoutY());
                charts.getChildren().add(wsText);
                final Label caption7 = new Label("");
                caption7.setTextFill(Color.BLACK);
                caption7.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : wsChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e -> {
                                caption7.setLayoutX(wsChart.getLayoutX() + 210);
                                caption7.setLayoutY(wsChart.getLayoutY() + 93);
                                caption7.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption7);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption7);
                    });
                }

            });
        }));

        readerHomeScreen.getMenuSeeReviews().setOnAction(e -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            readerReviewPane.setTop(readerReviewPane.getPane());
            readerReviewPane.setCenter(null);
            readerHomeScreen.setCenter(readerReviewPane);
            readerReviewPane.requestFocus();

            readerReviewPane.getGetReviews().setOnAction(e8 -> {
                String dsMonth = readerReviewPane.getDsMonth().getText();
                String dsDay = readerReviewPane.getDsDay().getText();
                String dsYear = readerReviewPane.getDsYear().getText();
                String dateStart = dsYear + "-" + dsMonth + "-" + dsDay;
                String dfMonth = readerReviewPane.getDfMonth().getText();
                String dfDay = readerReviewPane.getDfDay().getText();
                String dfYear = readerReviewPane.getDfYear().getText();
                String dateFinish = dfYear + "-" + dfMonth + "-" + dfDay;
                ArrayList<int[]> reviews = mgr.getReaderReviews(username, dateStart, dateFinish);
                Pane charts = new Pane();
                createCharts(reviews);
                charts.getChildren().addAll(storylineChart, plotChart, settingChart, spiceChart, charactersChart, wbChart, wsChart);
                readerReviewPane.setCenter(charts);
                charts.requestFocus();
                final Label caption = new Label("");
                caption.setTextFill(Color.BLACK);
                caption.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                Label storylineText = new Label("Storyline");
                storylineText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                storylineText.setLayoutX(82);
                storylineText.setLayoutY(10);
                charts.getChildren().add(storylineText);
                for (final PieChart.Data data : storylineChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e1 -> {
                                caption.setLayoutX(storylineChart.getLayoutX() + 210);
                                caption.setLayoutY(storylineChart.getLayoutY() + 93);
                                caption.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption);
                    });
                }

                Label plotText = new Label("Plot");
                plotText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                plotText.setLayoutX(storylineText.getLayoutX() + 15);
                plotText.setLayoutY(storylineText.getLayoutY() + 210);
                charts.getChildren().add(plotText);
                final Label caption2 = new Label("");
                caption2.setTextFill(Color.BLACK);
                caption2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : plotChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e1 -> {
                                caption2.setLayoutX(plotChart.getLayoutX() + 210);
                                caption2.setLayoutY(plotChart.getLayoutY() + 93);
                                caption2.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption2);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption2);
                    });
                }

                Label spiceText = new Label("Spice");
                spiceText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                spiceText.setLayoutX(storylineText.getLayoutX() + 610);
                spiceText.setLayoutY(storylineText.getLayoutY());
                charts.getChildren().add(spiceText);
                final Label caption4 = new Label("");
                caption4.setTextFill(Color.BLACK);
                caption4.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : spiceChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e1 -> {
                                caption4.setLayoutX(spiceChart.getLayoutX() + 210);
                                caption4.setLayoutY(spiceChart.getLayoutY() + 93);
                                caption4.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption4);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption4);
                    });
                }

                Label charactersText = new Label("Characters");
                charactersText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                charactersText.setLayoutX(plotText.getLayoutX() + 575);
                charactersText.setLayoutY(plotText.getLayoutY());
                charts.getChildren().add(charactersText);
                final Label caption5 = new Label("");
                caption5.setTextFill(Color.BLACK);
                caption5.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : charactersChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e1 -> {
                                caption5.setLayoutX(charactersChart.getLayoutX() + 210);
                                caption5.setLayoutY(charactersChart.getLayoutY() + 93);
                                caption5.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption5);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption5);
                    });
                }

                Label settingText = new Label("Setting");
                settingText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                settingText.setLayoutX(charactersText.getLayoutX() + 17);
                settingText.setLayoutY(charactersText.getLayoutY() + 210);
                charts.getChildren().add(settingText);
                final Label caption3 = new Label("");
                caption3.setTextFill(Color.BLACK);
                caption3.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : settingChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e1 -> {
                                caption3.setLayoutX(settingChart.getLayoutX() + 210);
                                caption3.setLayoutY(settingChart.getLayoutY() + 93);
                                caption3.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption3);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption3);
                    });
                }

                Label wbText = new Label("World Building");
                wbText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                wbText.setLayoutX(spiceText.getLayoutX() + 565);
                wbText.setLayoutY(spiceText.getLayoutY());
                charts.getChildren().add(wbText);
                final Label caption6 = new Label("");
                caption6.setTextFill(Color.BLACK);
                caption6.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : wbChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e1 -> {
                                caption6.setLayoutX(wbChart.getLayoutX() + 210);
                                caption6.setLayoutY(wbChart.getLayoutY() + 93);
                                caption6.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption6);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption6);
                    });
                }

                Label wsText = new Label("Writing Style");
                wsText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                wsText.setLayoutX(charactersText.getLayoutX() + 595);
                wsText.setLayoutY(charactersText.getLayoutY());
                charts.getChildren().add(wsText);
                final Label caption7 = new Label("");
                caption7.setTextFill(Color.BLACK);
                caption7.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : wsChart.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e1 -> {
                                caption7.setLayoutX(wsChart.getLayoutX() + 210);
                                caption7.setLayoutY(wsChart.getLayoutY() + 93);
                                caption7.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption7);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption7);
                    });
                }
                Label num = new Label("You've read " + mgr.getNumberBooks(username, dateStart, dateFinish) + " book(s) between " + dsMonth + "/" + dsDay + "/" + dsYear + " and " + dfMonth + "/" + dfDay + "/" + dfYear + "!");
                num.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
                num.setLayoutY(10);
                Pane top = new Pane();
                num.layoutXProperty().bind(top.widthProperty().subtract(num.widthProperty()).divide(2));
                top.getChildren().add(num);
                readerReviewPane.setTop(top);
                readerReviewPane.getDsDay().setText("");
                readerReviewPane.getDsMonth().setText("");
                readerReviewPane.getDsYear().setText("");
                readerReviewPane.getDfDay().setText("");
                readerReviewPane.getDfMonth().setText("");
                readerReviewPane.getDfYear().setText("");

                int[] g = reviews.get(7);
                ObservableList<PieChart.Data> genreData =
                        FXCollections.observableArrayList(
                                new PieChart.Data("Romance", g[0]),
                                new PieChart.Data("Fantasy", g[1]),
                                new PieChart.Data("Mystery", g[2]),
                                new PieChart.Data("Thriller", g[3]),
                                new PieChart.Data("Historical Fiction", g[4]),
                                new PieChart.Data("Science Fiction", g[5]));
                genres.setMaxSize(200, 200);
                genres.setData(genreData);
                genres.setClockwise(true);
                genres.setLabelsVisible(true);
                genres.setLabelLineLength(200);
                genres.setLegendVisible(false);
                genres.setLegendSide(Side.RIGHT);
                genres.setLayoutX(plotChart.getLayoutX() + 275);
                genres.setLayoutY(plotChart.getLayoutY() + 210);

                charts.getChildren().add(genres);
                settingChart.setLayoutX(charactersChart.getLayoutX() + 275);
                settingChart.setLayoutY(charactersChart.getLayoutY() + 210);
                settingText.setLayoutY(charactersText.getLayoutY() + 210);
                settingText.setLayoutX(charactersText.getLayoutX() + 293);

                Label genreText = new Label("Genres");
                genreText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                genreText.setLayoutX(plotText.getLayoutX() + 265);
                genreText.setLayoutY(charactersText.getLayoutY() + 210);
                charts.getChildren().add(genreText);
                final Label caption8 = new Label("");
                caption8.setTextFill(Color.BLACK);
                caption8.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
                for (final PieChart.Data data : genres.getData()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                            e1 -> {
                                caption8.setLayoutX(genres.getLayoutX() + 210);
                                caption8.setLayoutY(genres.getLayoutY() + 93);
                                caption8.setText(String.valueOf((int) data.getPieValue()));
                                charts.getChildren().add(caption8);
                            });

                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e1 -> {
                        charts.getChildren().remove(caption8);
                    });
                }

            });
        });

    }

    public void createGUI() {
        pane.setStyle("-fx-background-color: antiquewhite;");
        pane.setCenter(loginPane);
        loginPane.requestFocus();
        loginPane.getNewUser().setOnAction(e -> {
            pane.setCenter(registrationPane);
            registrationPane.requestFocus();

        });
        registrationPane.getRegister().setOnAction(e -> {
            if(registrationPane.getAuthor().isSelected()){
                user = 1;
            } else if(registrationPane.getReader().isSelected()){
                user = 2;
            }
            int duplicate = mgr.verify(user, registrationPane.getUsername().getText());
            if (duplicate == 0) {
                mgr.createUser(registrationPane.getUsername().getText(), registrationPane.getPassword().getText(), registrationPane.getName().getText(), user);
                pane.setCenter(loginPane);
                loginPane.requestFocus();
                Alert welcome = new Alert(Alert.AlertType.INFORMATION);
                welcome.setHeaderText("Welcome to Book Nook!");
                welcome.setContentText("Your user was created! Please login to your profile.");
                welcome.show();
                registrationPane.getName().setText("");
                registrationPane.getUsername().setText("");
                registrationPane.getPassword().setText("");
                registrationPane.getAuthor().setSelected(false);
                registrationPane.getReader().setSelected(false);
            } else {
                Alert taken = new Alert(Alert.AlertType.ERROR);
                taken.setTitle("Uh Oh...");
                taken.setHeaderText("User Taken");
                taken.setContentText("The username you entered is already taken. Please change the username and try again.");
                taken.show();
                registrationPane.getUsername().setText("");
                registrationPane.getPassword().setText("");
                registrationPane.getName().setText("");
                registrationPane.getAuthor().setSelected(false);
                registrationPane.getReader().setSelected(false);
            }

        });

        loginPane.getLogin().setOnAction(e -> {
            user = mgr.checkUser(loginPane.getUsername().getText(), loginPane.getPassword().getText());
            if (user == 1) {
                authorHomeScreen.setTop(authorHomeScreen.getMenuBar());
                username = loginPane.getUsername().getText();
                pane.setCenter(authorHomeScreen);
                authorHomeScreen.requestFocus();
                printBooks();
            } else if (user == 2) {
                readerHomeScreen.setTop(readerHomeScreen.getMenu());
                username = loginPane.getUsername().getText();
                pane.setCenter(readerHomeScreen);
                readerHomeScreen.requestFocus();
                printReadBooks();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Wrong username or password!");
                error.setHeaderText("Wrong username or password!");
                error.setContentText(("Please try again."));
                error.show();
            }

        });

        authorHomeScreen.getMenuLogout().setOnAction(e1 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            loginPane.getUsername().setText("Username");
            loginPane.getPassword().setText("Password");
            removeBooks();
            authorHomeScreen.setCenter(loginPane);
            authorHomeScreen.setTop(null);
            loginPane.requestFocus();
        });

        authorHomeScreen.getMenuCreate().setOnAction(e2 -> {
            deleteProfilePopup.hide();
            updatePopup.hide();
            deletePopup.hide();
            authorHomeScreen.setCenter(bookCreationPane);
            bookCreationPane.requestFocus();
        });

        bookCreationPane.getCreateBook().setOnAction(e3 -> {
            if (bookCreationPane.getISBN().getText().length() != 13) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Uh oh...");
                error.setHeaderText("Invalid ISBN");
                error.setContentText("Please enter a valid ISBN. They are strings of 13 numbers.");
                error.show();
            } else {
                String title = bookCreationPane.getTitle().getText();
                String author = bookCreationPane.getAuthor().getText();
                String series = bookCreationPane.getSeries().getText();
                Float volume = Float.parseFloat(bookCreationPane.getVolume().getText());
                String ISBN = bookCreationPane.getISBN().getText();
                String genre = String.valueOf(bookCreationPane.getGenre().getValue());
                String categories = bookCreationPane.getCategories().getText();
                String publisher = bookCreationPane.getPublisher().getText();
                String synopsis = bookCreationPane.getSynopsis().getText();
                mgr.createBook(title, author, series, volume, ISBN, genre, categories, publisher, synopsis, username);
                bookCreationPane.getTitle().setText("");
                bookCreationPane.getAuthor().setText("");
                bookCreationPane.getSeries().setText("");
                bookCreationPane.getVolume().setText("");
                bookCreationPane.getGenre().setValue("");
                bookCreationPane.getISBN().setText("");
                bookCreationPane.getCategories().setText("");
                bookCreationPane.getPublisher().setText("");
                bookCreationPane.getSynopsis().setText("");
                removeBooks();
                printBooks();
            }
        });

        bookUpdatePane.getUpdateBook().setOnAction(e5 -> {
            if (bookUpdatePane.getISBN().getText().length() != 13) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Uh oh...");
                error.setHeaderText("Invalid ISBN");
                error.setContentText("Please enter a valid ISBN. They are strings of 13 numbers.");
                error.show();
                error.setY(500);
            } else {
                String title = bookUpdatePane.getTitle().getText();
                String author = bookUpdatePane.getAuthor().getText();
                String series = bookUpdatePane.getSeries().getText();
                Float volume = Float.parseFloat(bookUpdatePane.getVolume().getText());
                String ISBN = bookUpdatePane.getISBN().getText();
                String genre = String.valueOf(bookUpdatePane.getGenre().getValue());
                String categories = bookUpdatePane.getCategories().getText();
                String publisher = bookUpdatePane.getPublisher().getText();
                String synopsis = bookUpdatePane.getSynopsis().getText();
                String oldISBN = bookUpdatePane.getIsbn();
                mgr.updateBook(title, author, series, volume, ISBN, genre, categories, publisher, synopsis, oldISBN, username);
                removeBooks();
                printBooks();
            }
        });

        authorHomeScreen.getMenuBack().setOnAction(e6 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            removeBooks();
            printBooks();
        });


        authorHomeScreen.getMenuUpdateProfile().setOnAction(e8 -> {
            updatePopup.hide();
            deletePopup.hide();
            fillProfile();
            authorHomeScreen.setCenter(authorProfileUpdate);
            authorProfileUpdate.requestFocus();
        });

        authorProfileUpdate.getUpdate().setOnAction(e9 -> {
            String name = authorProfileUpdate.getName().getText();
            String newUsername = authorProfileUpdate.getUsername().getText();
            String password = authorProfileUpdate.getPassword().getText();
            mgr.updateAuthor(name, newUsername, password, username);
            mgr.setNameOfUser(name);
            removeBooks();
            printBooks();
            authorHomeScreen.setCenter(authorPane);
            authorPane.requestFocus();
            Alert updated = new Alert(Alert.AlertType.CONFIRMATION);
            updated.setHeaderText("Updated!");
            updated.setContentText("Your profile information was successfully updated!");
            updated.show();
        });

        readerHomeScreen.getMenuLogout().setOnAction(e10 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            loginPane.getUsername().setText("Username");
            loginPane.getPassword().setText("Password");
            readerHomeScreen.setCenter(loginPane);
            readerHomeScreen.setTop(null);
            readerReviewPane.setTop(readerReviewPane.getPane());
            readerReviewPane.setCenter(null);
            removeBooks();
            loginPane.requestFocus();
            readerReviewPane.getDsMonth().setText("");
            readerReviewPane.getDsYear().setText("");
            readerReviewPane.getDsDay().setText("");
            readerReviewPane.getDfMonth().setText("");
            readerReviewPane.getDfYear().setText("");
            readerReviewPane.getDfDay().setText("");
        });

        readerHomeScreen.getMenuRead().setOnAction(e11 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            removeBooks();
            printReadBooks();
        });

        readerHomeScreen.getMenuReading().setOnAction(e12 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            removeBooks();
            printReadingBooks();
        });

        readerHomeScreen.getMenuTBR().setOnAction(e13 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            removeBooks();
            printTBR();
        });

        readerHomeScreen.getMenuBook().setOnAction(e14 -> {
            updatePopup.hide();
            deletePopup.hide();
            deleteProfilePopup.hide();
            readerHomeScreen.setCenter(bookSearchPane);
        });

        bookSearchPane.getSearchButton().setOnAction(e15 -> {
            searchBooks();
        });

       readingStatusPane.getSearch().setOnAction(e16 -> {
           readingStatusPane.getReadingStatus().setValue("");
           readingStatusPane.getSearch().setVisible(false);
           readingStatusPane.getSetReadingStatus().setVisible(true);
           readingStatusPane.getReadingStatus().setVisible(true);
           readingStatusPane.getSetReadingStatusText().setVisible(true);
           readingStatusPane.getEditReadingStatus().setVisible(true);
           readingStatusPane.getRemoveStatus().setVisible(true);
           String status = mgr.getReadingStatus(username, readingStatusISBN);
           readingStatusPane.getReadingStatus().setValue(status);
       });

       readingStatusPane.getSetReadingStatus().setOnAction(e17 -> {
           if (readingStatusPane.getISBN().getText().length() != 13) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Uh oh...");
                error.setHeaderText("Invalid ISBN");
                error.setContentText("Please enter a valid ISBN. They are strings of 13 numbers.");
                error.show();
           } else {
               mgr.setReadingStatus(username, readingStatusPane.getISBN().getText(), String.valueOf(readingStatusPane.getReadingStatus().getValue()));
               removeBooks();
               printReadBooks();
               if(String.valueOf(readingStatusPane.getReadingStatus().getValue()).equalsIgnoreCase("read")){
                   createReviewPane();
               } else {
                   readerHomeScreen.setCenter(readerPane);
                   readerPane.requestFocus();
               }
               readingStatusPane.getReadingStatus().setValue("");
           }

       });

       readingStatusPane.getEditReadingStatus().setOnAction(e18 -> {
           mgr.updateReadingStatus(username, readingStatusPane.getISBN().getText(), String.valueOf(readingStatusPane.getReadingStatus().getValue()));
           removeBooks();
           printReadBooks();
           if(String.valueOf(readingStatusPane.getReadingStatus().getValue()).equalsIgnoreCase("read")){
               createReviewPane();
           } else {
               readerHomeScreen.setCenter(readerPane);
               readerPane.requestFocus();
           }
           readingStatusPane.getReadingStatus().setValue("");
       });

       readingStatusPane.getRemoveStatus().setOnAction(e -> {
           mgr.removeReadingStatus(username, readingStatusPane.getISBN().getText());
           removeBooks();
           printReadBooks();
           readerHomeScreen.setCenter(readerPane);
       });

        readerHomeScreen.getMenuUpdateProfile().setOnAction(e8 -> {
            updatePopup.hide();
            deletePopup.hide();
            fillProfile();
            readerHomeScreen.setCenter(readerProfileUpdate);
            readerProfileUpdate.requestFocus();
        });

        readerProfileUpdate.getUpdate().setOnAction(e -> {
            String name = readerProfileUpdate.getName().getText();
            String newUsername = readerProfileUpdate.getUsername().getText();
            String password = readerProfileUpdate.getPassword().getText();
            mgr.updateReader(name, newUsername, password, username);
            mgr.setNameOfUser(name);
            removeBooks();
            printReadBooks();
            readerHomeScreen.setCenter(readerPane);
            authorPane.requestFocus();
            Alert updated = new Alert(Alert.AlertType.CONFIRMATION);
            updated.setHeaderText("Updated!");
            updated.setContentText("Your profile information was successfully updated!");
            updated.show();
        });


    }

    public void fillProfile() {
        String[] profile;
        if(user == 1) {
            profile = mgr.printProfile(username, user);
            authorProfileUpdate.getName().setText(profile[0]);
            authorProfileUpdate.getUsername().setText(profile[1]);
            authorProfileUpdate.getPassword().setText(profile[2]);
        } else {
            profile = mgr.printProfile(username, user);
            readerProfileUpdate.getName().setText(profile[0]);
            readerProfileUpdate.getUsername().setText(profile[1]);
            readerProfileUpdate.getPassword().setText(profile[2]);
        }

    }


    public void fillBoxes(){
        bookUpdatePane.getTitle().setText(mgr.getBookDetails()[0]);
        bookUpdatePane.getAuthor().setText(mgr.getBookDetails()[1]);
        bookUpdatePane.getSeries().setText(mgr.getBookDetails()[2]);
        bookUpdatePane.getVolume().setText(String.valueOf(mgr.getBookVolume()));
        bookUpdatePane.getISBN().setText(bookUpdatePane.getIsbn());
        bookUpdatePane.getGenre().setValue(mgr.getBookDetails()[3]);
        bookUpdatePane.getCategories().setText(mgr.getBookDetails()[4]);
        bookUpdatePane.getPublisher().setText(mgr.getBookDetails()[5]);
        bookUpdatePane.getSynopsis().setText(mgr.getBookDetails()[6]);
    }

    public void removeBooks() {
        if(user == 1) {
            authorPane.getChildren().clear();
        } else {
            readerPane.getChildren().clear();
            mgr.getTBRBooks().clear();
            mgr.getReadBooks().clear();
            mgr.getReadingBooks().clear();
        }


    }

    public void printBooks() {
        Label name = new Label(mgr.getNameOfUser() + "'s Books");
        name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        authorPane.setStyle("-fx-background-color: antiquewhite;");
        authorPane.add(name, 0, 0);
        Label title = new Label("Title");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label series = new Label("Series");
        series.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label volume = new Label("Volume");
        volume.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label isbn = new Label("ISBN");
        isbn.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label publisher = new Label("Publisher");
        publisher.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label blank = new Label("");
        authorPane.add(blank, 0, 1);
        authorPane.setHgap(125);
        authorPane.add(title, 0,2);
        authorPane.add(series, 1,2);
        authorPane.add(volume, 2,2);
        authorPane.add(isbn, 3,2);
        authorPane.add(publisher, 4,2);
        mgr.printAuthorsBooks(username);
        int j = 3;
        for(int i = 0; i < mgr.getAuthorsBooks().size(); i++) {
            String[] book = mgr.getAuthorsBooks().get(i);
            Label newTitle = new Label(book[0]);
            Label newSeries = new Label(book[1]);
            Label newVolume = new Label(book[2]);
            Label newISBN = new Label(book[3]);
            Label newPublisher = new Label(book[4]);
            authorPane.add(newTitle, 0, j);
            authorPane.add(newSeries, 1, j);
            authorPane.add(newVolume, 2, j);
            authorPane.add(newISBN, 3, j);
            authorPane.add(newPublisher, 4, j);
            j++;
        }
        authorHomeScreen.setCenter(authorPane);
        authorPane.requestFocus();
    }

    public void createDeletePopup() {
        Label goodbye = new Label("We're sad to see you go :(");
        goodbye.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        goodbye.setLayoutY(0);
        goodbye.layoutXProperty().bind(deleteProfilePopup.widthProperty().subtract(goodbye.widthProperty()).divide(2));
        deleteProfilePopup.setAnchorX(575);
        deleteProfilePopup.setAnchorY(325);
        authorHomeScreen.setCenter(null);
        readerHomeScreen.setCenter(null);
        deleteProfilePopup.getContent().addAll(goodbye);
        if (user == 1) {
            Label authorText = new Label("Please note that any books already added to Book Nook will not be deleted.");
            authorText.layoutXProperty().bind(deleteProfilePopup.widthProperty().subtract(authorText.widthProperty()).divide(2));
            authorText.setLayoutY(goodbye.getLayoutY() + 35);
            deleteProfilePopup.getContent().add(authorText);
        }

        Label confirm = new Label("Are you sure you want to delete your profile?");
        confirm.layoutXProperty().bind(deleteProfilePopup.widthProperty().subtract(confirm.widthProperty()).divide(2));
        confirm.setLayoutY(goodbye.getLayoutY() + 100);
        deleteProfilePopup.getContent().add(confirm);

        if (user != 1) {
            goodbye.layoutXProperty().bind(deleteProfilePopup.widthProperty().subtract(goodbye.widthProperty()).divide(1.01));
            confirm.layoutXProperty().bind(deleteProfilePopup.widthProperty().subtract(confirm.widthProperty()).divide(1.01));
        }

        Button delete = new Button("Delete Profile");
        delete.setLayoutY(confirm.getLayoutY() + 50);
        delete.setLayoutX(85);

        Button cancel = new Button("Keep Profile");
        cancel.setLayoutY(delete.getLayoutY());
        cancel.setLayoutX(delete.getLayoutX() + 150);

        deleteProfilePopup.getContent().addAll(delete, cancel);

        cancel.setOnAction(e -> {
            if(user == 1) {
                deleteProfilePopup.hide();
                removeBooks();
                printBooks();
                authorHomeScreen.setCenter(authorPane);
                authorPane.requestFocus();
            } else {
                deleteProfilePopup.hide();
                removeBooks();
                printReadBooks();
                readerHomeScreen.setCenter(readerPane);
                readerPane.requestFocus();
            }

        });

        delete.setOnAction(e2 -> {
            deleteProfilePopup.hide();
            mgr.deleteProfile(username, user);
            loginPane.getUsername().setText("Username");
            loginPane.getPassword().setText("Password");
            if (user == 1) {
                authorHomeScreen.setCenter(loginPane);
                authorHomeScreen.setTop(null);
                removeBooks();
            } else {
                readerHomeScreen.setCenter(loginPane);
                readerHomeScreen.setTop(null);
                removeBooks();
            }
            loginPane.requestFocus();
            Alert deleted = new Alert(Alert.AlertType.INFORMATION);
            deleted.setHeaderText("Profile Deleted");
            deleted.setContentText("Your profile was successfully deleted.");
            deleted.show();
        });
    }

    public void printReadBooks() {
        Label name = new Label(mgr.getNameOfUser() + "'s Read Books");
        name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        readerPane.setStyle("-fx-background-color: antiquewhite;");
        readerPane.add(name, 0, 0);
        Label title = new Label("Title");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label author = new Label("Author");
        author.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label series = new Label("Series");
        series.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label volume = new Label("Volume");
        volume.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label isbn = new Label("ISBN");
        isbn.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label publisher = new Label("Publisher");
        publisher.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label blank = new Label("");
        readerPane.add(blank, 0, 1);
        readerPane.setHgap(100);
        readerPane.add(title, 0,2);
        readerPane.add(author, 1, 2);
        readerPane.add(series, 2,2);
        readerPane.add(volume, 3,2);
        readerPane.add(isbn, 4,2);
        readerPane.add(publisher, 5,2);
        mgr.pullReadBooks(username);
        int j = 3;
        for(int i = 0; i < mgr.getReadBooks().size(); i++) {
            String[] book = mgr.getReadBooks().get(i);
            Label newTitle = new Label(book[0]);
            Label newAuthor = new Label(book[1]);
            Label newSeries = new Label(book[2]);
            Label newVolume = new Label(book[3]);
            Label newISBN = new Label(book[4]);
            Label newPublisher = new Label(book[5]);
            readerPane.add(newTitle, 0, j);
            readerPane.add(newAuthor, 1, j);
            readerPane.add(newSeries, 2, j);
            readerPane.add(newVolume, 3, j);
            readerPane.add(newISBN, 4, j);
            readerPane.add(newPublisher, 5, j);
            j++;
        }
        readerHomeScreen.setCenter(readerPane);
        readerPane.requestFocus();
    }

    public void printTBR() {
        Label name = new Label(mgr.getNameOfUser() + "'s TBR");
        name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        readerPane.setStyle("-fx-background-color: antiquewhite;");
        readerPane.add(name, 0, 0);
        Label title = new Label("Title");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label author = new Label("Author");
        author.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label series = new Label("Series");
        series.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label volume = new Label("Volume");
        volume.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label isbn = new Label("ISBN");
        isbn.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label publisher = new Label("Publisher");
        publisher.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label blank = new Label("");
        readerPane.add(blank, 0, 1);
        readerPane.setHgap(100);
        readerPane.add(title, 0,2);
        readerPane.add(author, 1, 2);
        readerPane.add(series, 2,2);
        readerPane.add(volume, 3,2);
        readerPane.add(isbn, 4,2);
        readerPane.add(publisher, 5,2);
        mgr.pullTBR(username);
        int j = 3;
        for(int i = 0; i < mgr.getTBRBooks().size(); i++) {
            String[] book = mgr.getTBRBooks().get(i);
            Label newTitle = new Label(book[0]);
            Label newAuthor = new Label(book[1]);
            Label newSeries = new Label(book[2]);
            Label newVolume = new Label(book[3]);
            Label newISBN = new Label(book[4]);
            Label newPublisher = new Label(book[5]);
            readerPane.add(newTitle, 0, j);
            readerPane.add(newAuthor, 1, j);
            readerPane.add(newSeries, 2, j);
            readerPane.add(newVolume, 3, j);
            readerPane.add(newISBN, 4, j);
            readerPane.add(newPublisher, 5, j);
            j++;
        }
        readerHomeScreen.setCenter(readerPane);
        readerPane.requestFocus();
    }

    public void printReadingBooks() {
        Label name = new Label(mgr.getNameOfUser() + "'s Currently Reading");
        name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        readerPane.setStyle("-fx-background-color: antiquewhite;");
        readerPane.add(name, 0, 0);
        Label title = new Label("Title");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label author = new Label("Author");
        author.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label series = new Label("Series");
        series.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label volume = new Label("Volume");
        volume.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label isbn = new Label("ISBN");
        isbn.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label publisher = new Label("Publisher");
        publisher.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label blank = new Label("");
        readerPane.add(blank, 0, 1);
        readerPane.setHgap(100);
        readerPane.add(title, 0,2);
        readerPane.add(author, 1, 2);
        readerPane.add(series, 2,2);
        readerPane.add(volume, 3,2);
        readerPane.add(isbn, 4,2);
        readerPane.add(publisher, 5,2);
        mgr.pullReadingBooks(username);
        int j = 3;
        for(int i = 0; i < mgr.getReadingBooks().size(); i++) {
            String[] book = mgr.getReadingBooks().get(i);
            Label newTitle = new Label(book[0]);
            Label newAuthor = new Label(book[1]);
            Label newSeries = new Label(book[2]);
            Label newVolume = new Label(book[3]);
            Label newISBN = new Label(book[4]);
            Label newPublisher = new Label(book[5]);
            readerPane.add(newTitle, 0, j);
            readerPane.add(newAuthor, 1, j);
            readerPane.add(newSeries, 2, j);
            readerPane.add(newVolume, 3, j);
            readerPane.add(newISBN, 4, j);
            readerPane.add(newPublisher, 5, j);
            j++;
        }
        readerHomeScreen.setCenter(readerPane);
        readerPane.requestFocus();
    }

    public void searchBooks() {
        readingStatusISBN = null;
        String search = bookSearchPane.getSearch().getText();
        mgr.pullSearch(search);
        GridPane pane = new GridPane();
        pane.getChildren().clear();
        pane.setStyle("-fx-background-color: antiquewhite;");
        Label title = new Label("Title");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label author = new Label("Author");
        author.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label series = new Label("Series");
        series.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label volume = new Label("Volume");
        volume.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label isbn = new Label("ISBN");
        isbn.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label publisher = new Label("Publisher");
        publisher.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label synopsis = new Label("Synopsis");
        synopsis.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label readingStatus = new Label("Reading Status");
        readingStatus.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        Label blank = new Label("");
        Label blank2 = new Label("");
        pane.add(blank, 0, 0);
        pane.add(blank2, 0, 2);
        pane.setHgap(75);
        pane.setVgap(50);
        pane.add(title, 0,1);
        pane.add(author, 1, 1);
        pane.add(series, 2,1);
        pane.add(volume, 3,1);
        pane.add(isbn, 4,1);
        pane.add(publisher, 5,1);
        pane.add(synopsis, 6, 1);
        pane.add(readingStatus, 7, 1);
        int k = 1;
        int j = 2;
        Button[] buttons = new Button[mgr.getSearch().size()];
        for(int i = 0; i < mgr.getSearch().size(); i++) {
            String[] result = mgr.getSearch().get(i);
            Label newTitle = new Label(result[0]);
            Label newAuthor = new Label(result[1]);
            Label newSeries = new Label(result[2]);
            Label newVolume = new Label(result[3]);
            Label newISBN = new Label(result[4]);
            Label newPublisher = new Label(result[5]);
            TextArea newSynopsis = new TextArea();
            newSynopsis.setPrefWidth(400);
            newSynopsis.setText(result[6]);
            newSynopsis.setWrapText(true);
            newSynopsis.setEditable(false);
            Button addReadingStatus = new Button("Select Reading Status");
            buttons[i] = addReadingStatus;
            pane.add(newTitle, 0, j);
            pane.add(newAuthor, 1, j);
            pane.add(newSeries, 2, j);
            pane.add(newVolume, 3, j);
            pane.add(newISBN, 4, j);
            pane.add(newPublisher, 5, j);
            pane.add(newSynopsis, 6, j);
            pane.add(buttons[i], 7, j);
            j++;
        }


        bookSearchPane.setCenter(pane);
        pane.requestFocus();

        for(int i = 0; i < buttons.length; i++) {
            int finalI = i;
            buttons[i].setOnAction(e ->{
                String[] book = mgr.getSearch().get(finalI);
                readingStatusISBN = book[4];
                mgr.getSearch().clear();
                readerHomeScreen.setCenter(readingStatusPane);
                readingStatusPane.getISBN().setText(readingStatusISBN);
                readingStatusPane.requestFocus();
                bookSearchPane.getSearch().setText("");
                pane.getChildren().clear();
                readingStatusPane.getReadingStatus().setValue("");
                readingStatusPane.getSearch().setVisible(true);
                readingStatusPane.getSetReadingStatus().setVisible(false);
                readingStatusPane.getReadingStatus().setVisible(false);
                readingStatusPane.getSetReadingStatusText().setVisible(false);
                readingStatusPane.getEditReadingStatus().setVisible(false);
                readingStatusPane.getRemoveStatus().setVisible(false);
            });

        }
    }

    public void createReviewPane() {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: antiquewhite;");
        mgr.recallBook(readingStatusISBN);
        Label name = new Label(mgr.getNameOfUser() + "'s Review of " + mgr.getBookDetails()[0] + ":");
        name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        name.setLayoutY(10);
        name.setLayoutX(10);
        Label zeroText = new Label("Please enter 0 if you choose not to review or category is not applicable.");
        zeroText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        zeroText.setLayoutY(name.getLayoutY() + 25);
        zeroText.setLayoutX(10);
        pane.getChildren().addAll(name, zeroText);

        Label storylineText = new Label("Storyline: ");
        storylineText.setLayoutY(zeroText.getLayoutY() + 50);
        storylineText.setLayoutX(10);
        TextField storyline = new TextField();
        storyline.setLayoutX(storylineText.getLayoutX() + 70);
        storyline.setLayoutY(storylineText.getLayoutY() - 3);
        storyline.setPrefWidth(50);
        storyline.setAlignment(Pos.CENTER);
        Label storylineFraction = new Label("/ 5.00");
        storylineFraction.setLayoutX(storyline.getLayoutX() + 60);
        storylineFraction.setLayoutY(storylineText.getLayoutY());
        pane.getChildren().addAll(storylineText, storyline, storylineFraction);

        Label plotText = new Label("Plot: ");
        plotText.setLayoutY(storylineText.getLayoutY() + 50);
        plotText.setLayoutX(10);
        TextField plot = new TextField();
        plot.setLayoutX(plotText.getLayoutX() + 70);
        plot.setLayoutY(plotText.getLayoutY() - 3);
        plot.setPrefWidth(50);
        plot.setAlignment(Pos.CENTER);
        Label plotFraction = new Label("/ 5.00");
        plotFraction.setLayoutX(plot.getLayoutX() + 60);
        plotFraction.setLayoutY(plotText.getLayoutY());
        pane.getChildren().addAll(plotText, plot, plotFraction);

        Label settingText = new Label("Setting: ");
        settingText.setLayoutY(plotText.getLayoutY() + 50);
        settingText.setLayoutX(10);
        TextField setting = new TextField();
        setting.setLayoutX(settingText.getLayoutX() + 70);
        setting.setLayoutY(settingText.getLayoutY() - 3);
        setting.setPrefWidth(50);
        setting.setAlignment(Pos.CENTER);
        Label settingFraction = new Label("/ 5.00");
        settingFraction.setLayoutX(setting.getLayoutX() + 60);
        settingFraction.setLayoutY(settingText.getLayoutY());
        pane.getChildren().addAll(settingText, setting, settingFraction);

        Label spiceText = new Label("Spice: ");
        spiceText.setLayoutY(settingText.getLayoutY() + 50);
        spiceText.setLayoutX(10);
        TextField spice = new TextField();
        spice.setLayoutX(spiceText.getLayoutX() + 70);
        spice.setLayoutY(spiceText.getLayoutY() - 3);
        spice.setPrefWidth(50);
        spice.setAlignment(Pos.CENTER);
        Label spiceFraction = new Label("/ 5.00");
        spiceFraction.setLayoutX(spice.getLayoutX() + 60);
        spiceFraction.setLayoutY(spiceText.getLayoutY());
        pane.getChildren().addAll(spiceText, spice, spiceFraction);

        Label charactersText = new Label("Characters: ");
        charactersText.setLayoutY(spiceText.getLayoutY() + 50);
        charactersText.setLayoutX(10);
        TextField characters = new TextField();
        characters.setLayoutX(charactersText.getLayoutX() + 70);
        characters.setLayoutY(charactersText.getLayoutY() - 3);
        characters.setPrefWidth(50);
        characters.setAlignment(Pos.CENTER);
        Label charactersFraction = new Label("/ 5.00");
        charactersFraction.setLayoutX(characters.getLayoutX() + 60);
        charactersFraction.setLayoutY(charactersText.getLayoutY());
        pane.getChildren().addAll(charactersText, characters, charactersFraction);

        Label worldBuildingText = new Label("World Building: ");
        worldBuildingText.setLayoutY(charactersText.getLayoutY() + 50);
        worldBuildingText.setLayoutX(10);
        TextField worldBuilding = new TextField();
        worldBuilding.setLayoutX(worldBuildingText.getLayoutX() + 90);
        worldBuilding.setLayoutY(worldBuildingText.getLayoutY() - 3);
        worldBuilding.setPrefWidth(50);
        worldBuilding.setAlignment(Pos.CENTER);
        Label worldBuildingFraction = new Label("/ 5.00");
        worldBuildingFraction.setLayoutX(worldBuilding.getLayoutX() + 60);
        worldBuildingFraction.setLayoutY(worldBuildingText.getLayoutY());
        pane.getChildren().addAll(worldBuildingText, worldBuilding, worldBuildingFraction);

        Label writingStyleText = new Label("Writing Style: ");
        writingStyleText.setLayoutY(worldBuildingText.getLayoutY() + 50);
        writingStyleText.setLayoutX(10);
        TextField writingStyle = new TextField();
        writingStyle.setLayoutX(writingStyleText.getLayoutX() + 90);
        writingStyle.setLayoutY(writingStyleText.getLayoutY() - 3);
        writingStyle.setPrefWidth(50);
        writingStyle.setAlignment(Pos.CENTER);
        Label writingStyleFraction = new Label("/ 5.00");
        writingStyleFraction.setLayoutX(writingStyle.getLayoutX() + 60);
        writingStyleFraction.setLayoutY(writingStyleText.getLayoutY());
        pane.getChildren().addAll(writingStyleText, writingStyle, writingStyleFraction);

        Label dateInstructions = new Label("Please enter a two digit month, two digit day, and four digit year.");
        dateInstructions.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        dateInstructions.setLayoutX(10);
        dateInstructions.setLayoutY(writingStyleText.getLayoutY() + 50);
        pane.getChildren().add(dateInstructions);

        Label dateStartedText = new Label("Date Started: ");
        dateStartedText.setLayoutY(dateInstructions.getLayoutY() + 50);
        dateStartedText.setLayoutX(10);
        TextField dateStartedMonth = new TextField();
        dateStartedMonth.setLayoutX(dateStartedText.getLayoutX() + 90);
        dateStartedMonth.setLayoutY(dateStartedText.getLayoutY() - 3);
        dateStartedMonth.setPrefWidth(30);
        dateStartedMonth.setAlignment(Pos.CENTER);
        Label date1 = new Label("/ ");
        date1.setLayoutX(dateStartedMonth.getLayoutX() + 45);
        date1.setLayoutY(dateStartedText.getLayoutY());
        TextField dateStartedDay = new TextField();
        dateStartedDay.setLayoutX(dateStartedMonth.getLayoutX() + 60);
        dateStartedDay.setLayoutY(dateStartedText.getLayoutY() - 3);
        dateStartedDay.setPrefWidth(30);
        dateStartedDay.setAlignment(Pos.CENTER);
        Label date2 = new Label("/ ");
        date2.setLayoutX(dateStartedDay.getLayoutX() + 45);
        date2.setLayoutY(dateStartedText.getLayoutY());
        TextField dateStartedYear = new TextField();
        dateStartedYear.setLayoutX(dateStartedDay.getLayoutX() + 60);
        dateStartedYear.setLayoutY(dateStartedText.getLayoutY() - 3);
        dateStartedYear.setPrefWidth(50);
        dateStartedYear.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(dateStartedText, dateStartedMonth, date1, dateStartedDay, date2, dateStartedYear);

        Label dateFinishedText = new Label("Date Finished: ");
        dateFinishedText.setLayoutY(dateStartedText.getLayoutY() + 50);
        dateFinishedText.setLayoutX(10);
        TextField dateFinishedMonth = new TextField();
        dateFinishedMonth.setLayoutX(dateFinishedText.getLayoutX() + 90);
        dateFinishedMonth.setLayoutY(dateFinishedText.getLayoutY() - 3);
        dateFinishedMonth.setPrefWidth(30);
        dateFinishedMonth.setAlignment(Pos.CENTER);
        Label date3 = new Label("/ ");
        date3.setLayoutX(dateStartedMonth.getLayoutX() + 45);
        date3.setLayoutY(dateStartedText.getLayoutY());
        TextField dateFinishedDay = new TextField();
        dateFinishedDay.setLayoutX(dateFinishedMonth.getLayoutX() + 60);
        dateFinishedDay.setLayoutY(dateFinishedText.getLayoutY() - 3);
        dateFinishedDay.setPrefWidth(30);
        dateFinishedDay.setAlignment(Pos.CENTER);
        Label date4 = new Label("/ ");
        date4.setLayoutX(dateFinishedDay.getLayoutX() + 45);
        date4.setLayoutY(dateFinishedText.getLayoutY());
        TextField dateFinishedYear = new TextField();
        dateFinishedYear.setLayoutX(dateFinishedDay.getLayoutX() + 60);
        dateFinishedYear.setLayoutY(dateFinishedText.getLayoutY() - 3);
        dateFinishedYear.setPrefWidth(50);
        dateFinishedYear.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(dateFinishedText, dateFinishedMonth, date3, dateFinishedDay, date4, dateFinishedYear);

        Label thoughtsText = new Label("Thoughts:");
        thoughtsText.setLayoutY(settingText.getLayoutY());
        thoughtsText.setLayoutX(settingText.getLayoutX() + 600);
        TextArea thoughts = new TextArea();
        thoughts.setWrapText(true);
        thoughts.setPrefHeight(150);
        thoughts.setPrefWidth(500);
        thoughts.setLayoutX(thoughtsText.getLayoutX());
        thoughts.setLayoutY(thoughtsText.getLayoutY() + 20);
        pane.getChildren().addAll(thoughtsText, thoughts);

        Button createReview = new Button("Done!");
        createReview.setLayoutY(dateFinishedText.getLayoutY());
        createReview.setLayoutX(thoughtsText.getLayoutX());
        pane.getChildren().add(createReview);

        createReview.setOnAction(e -> {
            Float storylineRating = Float.parseFloat(storyline.getText());
            Float plotRating = Float.parseFloat(plot.getText());
            Float settingRating = Float.parseFloat(setting.getText());
            Float spiceRating = Float.parseFloat(spice.getText());
            Float characterRating = Float.parseFloat(characters.getText());
            Float wbRating = Float.parseFloat(worldBuilding.getText());
            Float wsRating = Float.parseFloat(writingStyle.getText());
            String startedMonth = dateStartedMonth.getText();
            String startedDay = dateStartedDay.getText();
            String startedYear = dateStartedYear.getText();
            String dateStarted = startedYear + "-" + startedMonth + "-" + startedDay;
            String finishedMonth = dateFinishedMonth.getText();
            String finishedDay = dateFinishedDay.getText();
            String finishedYear = dateFinishedYear.getText();
            String dateFinished = finishedYear + "-" + finishedMonth + "-" + finishedDay;
            String writtenReview;
            if(thoughts.getText().equals(null)) {
                writtenReview = null;
            } else {
                writtenReview = thoughts.getText();
            }
            mgr.createReview(username, readingStatusISBN, storylineRating, plotRating, settingRating, spiceRating, characterRating, wbRating, wsRating, dateStarted, dateFinished, writtenReview);
            removeBooks();
            printReadBooks();
            readerHomeScreen.setCenter(readerPane);
            readerPane.requestFocus();
        });

        readerHomeScreen.setCenter(pane);
        pane.requestFocus();
    }

    public void updateReview(String isbn) {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: antiquewhite;");
        mgr.recallBook(isbn);
        Label name = new Label(mgr.getNameOfUser() + "'s Review of " + mgr.getBookDetails()[0] + ":");
        name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        name.setLayoutY(10);
        name.setLayoutX(10);
        Label zeroText = new Label("Please enter 0 if you choose not to review or category is not applicable.");
        zeroText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        zeroText.setLayoutY(name.getLayoutY() + 25);
        zeroText.setLayoutX(10);
        pane.getChildren().addAll(name, zeroText);

        Label storylineText = new Label("Storyline: ");
        storylineText.setLayoutY(zeroText.getLayoutY() + 50);
        storylineText.setLayoutX(10);
        TextField storyline = new TextField();
        storyline.setLayoutX(storylineText.getLayoutX() + 70);
        storyline.setLayoutY(storylineText.getLayoutY() - 3);
        storyline.setPrefWidth(50);
        storyline.setAlignment(Pos.CENTER);
        Label storylineFraction = new Label("/ 5.00");
        storylineFraction.setLayoutX(storyline.getLayoutX() + 60);
        storylineFraction.setLayoutY(storylineText.getLayoutY());
        pane.getChildren().addAll(storylineText, storyline, storylineFraction);

        Label plotText = new Label("Plot: ");
        plotText.setLayoutY(storylineText.getLayoutY() + 50);
        plotText.setLayoutX(10);
        TextField plot = new TextField();
        plot.setLayoutX(plotText.getLayoutX() + 70);
        plot.setLayoutY(plotText.getLayoutY() - 3);
        plot.setPrefWidth(50);
        plot.setAlignment(Pos.CENTER);
        Label plotFraction = new Label("/ 5.00");
        plotFraction.setLayoutX(plot.getLayoutX() + 60);
        plotFraction.setLayoutY(plotText.getLayoutY());
        pane.getChildren().addAll(plotText, plot, plotFraction);

        Label settingText = new Label("Setting: ");
        settingText.setLayoutY(plotText.getLayoutY() + 50);
        settingText.setLayoutX(10);
        TextField setting = new TextField();
        setting.setLayoutX(settingText.getLayoutX() + 70);
        setting.setLayoutY(settingText.getLayoutY() - 3);
        setting.setPrefWidth(50);
        setting.setAlignment(Pos.CENTER);
        Label settingFraction = new Label("/ 5.00");
        settingFraction.setLayoutX(setting.getLayoutX() + 60);
        settingFraction.setLayoutY(settingText.getLayoutY());
        pane.getChildren().addAll(settingText, setting, settingFraction);

        Label spiceText = new Label("Spice: ");
        spiceText.setLayoutY(settingText.getLayoutY() + 50);
        spiceText.setLayoutX(10);
        TextField spice = new TextField();
        spice.setLayoutX(spiceText.getLayoutX() + 70);
        spice.setLayoutY(spiceText.getLayoutY() - 3);
        spice.setPrefWidth(50);
        spice.setAlignment(Pos.CENTER);
        Label spiceFraction = new Label("/ 5.00");
        spiceFraction.setLayoutX(spice.getLayoutX() + 60);
        spiceFraction.setLayoutY(spiceText.getLayoutY());
        pane.getChildren().addAll(spiceText, spice, spiceFraction);

        Label charactersText = new Label("Characters: ");
        charactersText.setLayoutY(spiceText.getLayoutY() + 50);
        charactersText.setLayoutX(10);
        TextField characters = new TextField();
        characters.setLayoutX(charactersText.getLayoutX() + 70);
        characters.setLayoutY(charactersText.getLayoutY() - 3);
        characters.setPrefWidth(50);
        characters.setAlignment(Pos.CENTER);
        Label charactersFraction = new Label("/ 5.00");
        charactersFraction.setLayoutX(characters.getLayoutX() + 60);
        charactersFraction.setLayoutY(charactersText.getLayoutY());
        pane.getChildren().addAll(charactersText, characters, charactersFraction);

        Label worldBuildingText = new Label("World Building: ");
        worldBuildingText.setLayoutY(charactersText.getLayoutY() + 50);
        worldBuildingText.setLayoutX(10);
        TextField worldBuilding = new TextField();
        worldBuilding.setLayoutX(worldBuildingText.getLayoutX() + 90);
        worldBuilding.setLayoutY(worldBuildingText.getLayoutY() - 3);
        worldBuilding.setPrefWidth(50);
        worldBuilding.setAlignment(Pos.CENTER);
        Label worldBuildingFraction = new Label("/ 5.00");
        worldBuildingFraction.setLayoutX(worldBuilding.getLayoutX() + 60);
        worldBuildingFraction.setLayoutY(worldBuildingText.getLayoutY());
        pane.getChildren().addAll(worldBuildingText, worldBuilding, worldBuildingFraction);

        Label writingStyleText = new Label("Writing Style: ");
        writingStyleText.setLayoutY(worldBuildingText.getLayoutY() + 50);
        writingStyleText.setLayoutX(10);
        TextField writingStyle = new TextField();
        writingStyle.setLayoutX(writingStyleText.getLayoutX() + 90);
        writingStyle.setLayoutY(writingStyleText.getLayoutY() - 3);
        writingStyle.setPrefWidth(50);
        writingStyle.setAlignment(Pos.CENTER);
        Label writingStyleFraction = new Label("/ 5.00");
        writingStyleFraction.setLayoutX(writingStyle.getLayoutX() + 60);
        writingStyleFraction.setLayoutY(writingStyleText.getLayoutY());
        pane.getChildren().addAll(writingStyleText, writingStyle, writingStyleFraction);

        Label dateInstructions = new Label("Please enter a two digit month, two digit day, and four digit year.");
        dateInstructions.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
        dateInstructions.setLayoutX(10);
        dateInstructions.setLayoutY(writingStyleText.getLayoutY() + 50);
        pane.getChildren().add(dateInstructions);

        Label dateStartedText = new Label("Date Started: ");
        dateStartedText.setLayoutY(dateInstructions.getLayoutY() + 50);
        dateStartedText.setLayoutX(10);
        TextField dateStartedMonth = new TextField();
        dateStartedMonth.setLayoutX(dateStartedText.getLayoutX() + 90);
        dateStartedMonth.setLayoutY(dateStartedText.getLayoutY() - 3);
        dateStartedMonth.setPrefWidth(30);
        dateStartedMonth.setAlignment(Pos.CENTER);
        Label date1 = new Label("/ ");
        date1.setLayoutX(dateStartedMonth.getLayoutX() + 45);
        date1.setLayoutY(dateStartedText.getLayoutY());
        TextField dateStartedDay = new TextField();
        dateStartedDay.setLayoutX(dateStartedMonth.getLayoutX() + 60);
        dateStartedDay.setLayoutY(dateStartedText.getLayoutY() - 3);
        dateStartedDay.setPrefWidth(30);
        dateStartedDay.setAlignment(Pos.CENTER);
        Label date2 = new Label("/ ");
        date2.setLayoutX(dateStartedDay.getLayoutX() + 45);
        date2.setLayoutY(dateStartedText.getLayoutY());
        TextField dateStartedYear = new TextField();
        dateStartedYear.setLayoutX(dateStartedDay.getLayoutX() + 60);
        dateStartedYear.setLayoutY(dateStartedText.getLayoutY() - 3);
        dateStartedYear.setPrefWidth(50);
        dateStartedYear.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(dateStartedText, dateStartedMonth, date1, dateStartedDay, date2, dateStartedYear);

        Label dateFinishedText = new Label("Date Finished: ");
        dateFinishedText.setLayoutY(dateStartedText.getLayoutY() + 50);
        dateFinishedText.setLayoutX(10);
        TextField dateFinishedMonth = new TextField();
        dateFinishedMonth.setLayoutX(dateFinishedText.getLayoutX() + 90);
        dateFinishedMonth.setLayoutY(dateFinishedText.getLayoutY() - 3);
        dateFinishedMonth.setPrefWidth(30);
        dateFinishedMonth.setAlignment(Pos.CENTER);
        Label date3 = new Label("/ ");
        date3.setLayoutX(dateStartedMonth.getLayoutX() + 45);
        date3.setLayoutY(dateStartedText.getLayoutY());
        TextField dateFinishedDay = new TextField();
        dateFinishedDay.setLayoutX(dateFinishedMonth.getLayoutX() + 60);
        dateFinishedDay.setLayoutY(dateFinishedText.getLayoutY() - 3);
        dateFinishedDay.setPrefWidth(30);
        dateFinishedDay.setAlignment(Pos.CENTER);
        Label date4 = new Label("/ ");
        date4.setLayoutX(dateFinishedDay.getLayoutX() + 45);
        date4.setLayoutY(dateFinishedText.getLayoutY());
        TextField dateFinishedYear = new TextField();
        dateFinishedYear.setLayoutX(dateFinishedDay.getLayoutX() + 60);
        dateFinishedYear.setLayoutY(dateFinishedText.getLayoutY() - 3);
        dateFinishedYear.setPrefWidth(50);
        dateFinishedYear.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(dateFinishedText, dateFinishedMonth, date3, dateFinishedDay, date4, dateFinishedYear);

        Label thoughtsText = new Label("Thoughts:");
        thoughtsText.setLayoutY(settingText.getLayoutY());
        thoughtsText.setLayoutX(settingText.getLayoutX() + 600);
        TextArea thoughts = new TextArea();
        thoughts.setWrapText(true);
        thoughts.setPrefHeight(150);
        thoughts.setPrefWidth(500);
        thoughts.setLayoutX(thoughtsText.getLayoutX());
        thoughts.setLayoutY(thoughtsText.getLayoutY() + 20);
        pane.getChildren().addAll(thoughtsText, thoughts);

        Button updateReview = new Button("Done!");
        updateReview.setLayoutY(dateFinishedText.getLayoutY());
        updateReview.setLayoutX(thoughtsText.getLayoutX());
        pane.getChildren().add(updateReview);

        String[] review = mgr.pullReviewDetails(username, isbn);
        storyline.setText(review[0]);
        plot.setText(review[1]);
        setting.setText(review[2]);
        spice.setText(review[3]);
        characters.setText(review[4]);
        worldBuilding.setText(review[5]);
        writingStyle.setText(review[6]);
        String[] dateStarted = review[7].split("-");
        dateStartedYear.setText(dateStarted[0]);
        dateStartedMonth.setText(dateStarted[1]);
        dateStartedDay.setText(dateStarted[2]);
        String[] dateFinished = review[8].split("-");
        dateFinishedYear.setText(dateFinished[0]);
        dateFinishedMonth.setText(dateFinished[1]);
        dateFinishedDay.setText(dateFinished[2]);
        thoughts.setText(review[9]);

        updateReview.setOnAction(e -> {
            Float storylineRating = Float.parseFloat(storyline.getText());
            Float plotRating = Float.parseFloat(plot.getText());
            Float settingRating = Float.parseFloat(setting.getText());
            Float spiceRating = Float.parseFloat(spice.getText());
            Float characterRating = Float.parseFloat(characters.getText());
            Float wbRating = Float.parseFloat(worldBuilding.getText());
            Float wsRating = Float.parseFloat(writingStyle.getText());
            String startedMonth = dateStartedMonth.getText();
            String startedDay = dateStartedDay.getText();
            String startedYear = dateStartedYear.getText();
            String newDateStarted = startedYear + "-" + startedMonth + "-" + startedDay;
            String finishedMonth = dateFinishedMonth.getText();
            String finishedDay = dateFinishedDay.getText();
            String finishedYear = dateFinishedYear.getText();
            String newDateFinished = finishedYear + "-" + finishedMonth + "-" + finishedDay;
            String writtenReview;
            writtenReview = thoughts.getText();
            mgr.updateReview(username, isbn, storylineRating, plotRating, settingRating, spiceRating, characterRating, wbRating, wsRating, newDateStarted, newDateFinished, writtenReview);
            removeBooks();
            printReadBooks();
            readerHomeScreen.setCenter(readerPane);
            readerPane.requestFocus();
        });

        readerHomeScreen.setCenter(pane);
        pane.requestFocus();
    }

    public void createUpdate() {
        updatePopup.setHeight(200);
        updatePopup.setWidth(200);
        updatePopup.setAnchorX(575);
        updatePopup.setAnchorY(325);
        if(user == 1) {
            Label text = new Label("Please enter the ISBN of the book to update:");
            TextField ISBN = new TextField();
            Button update = new Button("Update");
            update.setLayoutX(ISBN.getLayoutX() + 148);
            update.setLayoutY(ISBN.getLayoutY() + 100);
            text.setLayoutX(65);
            text.setLayoutY(0);
            ISBN.setLayoutX(100);
            ISBN.setLayoutY(text.getLayoutY() + 50);
            updatePopup.getContent().addAll(text, ISBN, update);
            update.setOnAction(e -> {
                if (ISBN.getText().length() != 13) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Uh oh...");
                    error.setHeaderText("Invalid ISBN");
                    error.setContentText("Please enter a valid ISBN. They are strings of 13 numbers.");
                    error.show();
                } else {
                    String isbn = ISBN.getText();
                    bookUpdatePane.setISBN(isbn);
                    updatePopup.hide();
                    mgr.recallBook(isbn);
                    fillBoxes();
                    authorHomeScreen.setCenter(bookUpdatePane);
                    ISBN.setText("");
                }
            });
        } else {
            Label text = new Label("Please enter the ISBN of the book to update:");
            TextField ISBN = new TextField();
            Button update = new Button("Update");
            update.setLayoutX(ISBN.getLayoutX() + 148);
            update.setLayoutY(ISBN.getLayoutY() + 100);
            text.setLayoutX(65);
            text.setLayoutY(0);
            ISBN.setLayoutX(100);
            ISBN.setLayoutY(text.getLayoutY() + 50);
            updatePopup.getContent().addAll(text, ISBN, update);
            update.setOnAction(e -> {
                if (ISBN.getText().length() != 13) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Uh oh...");
                    error.setHeaderText("Invalid ISBN");
                    error.setContentText("Please enter a valid ISBN. They are strings of 13 numbers.");
                    error.show();
                    error.setY(500);
                } else {
                    String isbn = ISBN.getText();
                    updatePopup.hide();
                    updateReview(isbn);
                    ISBN.setText("");
                }
            });
        }
    }

    public void createCharts(ArrayList reviews) {
        int[] storyline = (int[]) reviews.get(0);
        int[] plot = (int[]) reviews.get(1);
        int[] setting = (int[]) reviews.get(2);
        int[] spice = (int[]) reviews.get(3);
        int[] characters = (int[]) reviews.get(4);
        int[] wb = (int[]) reviews.get(5);
        int[] ws = (int[]) reviews.get(6);


        ObservableList<PieChart.Data> storylineData =
                FXCollections.observableArrayList(
                        new PieChart.Data("0", storyline[0]),
                        new PieChart.Data("1", storyline[1]),
                        new PieChart.Data("2", storyline[2]),
                        new PieChart.Data("3", storyline[3]),
                        new PieChart.Data("4", storyline[4]),
                        new PieChart.Data("5", storyline[5]));
        storylineChart.setMaxSize(200, 200);
        storylineChart.setData(storylineData);
        storylineChart.setClockwise(true);
        storylineChart.setLabelsVisible(true);
        storylineChart.setLegendSide(Side.RIGHT);
        storylineChart.setLegendVisible(true);
        storylineChart.setLayoutX(10);
        storylineChart.setLayoutY(20);

        ObservableList<PieChart.Data> plotData =
                FXCollections.observableArrayList(
                        new PieChart.Data("0", plot[0]),
                        new PieChart.Data("1", plot[1]),
                        new PieChart.Data("2", plot[2]),
                        new PieChart.Data("3", plot[3]),
                        new PieChart.Data("4", plot[4]),
                        new PieChart.Data("5", plot[5]));
        plotChart.setMaxSize(200, 200);
        plotChart.setData(plotData);
        plotChart.setClockwise(true);
        plotChart.setLabelsVisible(true);
        plotChart.setLegendVisible(true);
        plotChart.setLegendSide(Side.RIGHT);
        plotChart.setLayoutX(storylineChart.getLayoutX());
        plotChart.setLayoutY(storylineChart.getLayoutY() + 210);

        ObservableList<PieChart.Data> spiceData =
                FXCollections.observableArrayList(
                        new PieChart.Data("0", spice[0]),
                        new PieChart.Data("1", spice[1]),
                        new PieChart.Data("2", spice[2]),
                        new PieChart.Data("3", spice[3]),
                        new PieChart.Data("4", spice[4]),
                        new PieChart.Data("5", spice[5]));
        spiceChart.setMaxSize(200, 200);
        spiceChart.setData(spiceData);
        spiceChart.setClockwise(true);
        spiceChart.setLabelsVisible(true);
        spiceChart.setLegendVisible(true);
        spiceChart.setLegendSide(Side.RIGHT);
        spiceChart.setLayoutX(storylineChart.getLayoutX() + 600);
        spiceChart.setLayoutY(storylineChart.getLayoutY());

        ObservableList<PieChart.Data> charactersData =
                FXCollections.observableArrayList(
                        new PieChart.Data("0", characters[0]),
                        new PieChart.Data("1", characters[1]),
                        new PieChart.Data("2", characters[2]),
                        new PieChart.Data("3", characters[3]),
                        new PieChart.Data("4", characters[4]),
                        new PieChart.Data("5", characters[5]));
        charactersChart.setMaxSize(200, 200);
        charactersChart.setData(charactersData);
        charactersChart.setClockwise(true);
        charactersChart.setLabelsVisible(true);
        charactersChart.setLegendVisible(true);
        charactersChart.setLegendSide(Side.RIGHT);
        charactersChart.setLayoutX(plotChart.getLayoutX() + 600);
        charactersChart.setLayoutY(plotChart.getLayoutY());

        ObservableList<PieChart.Data> settingData =
                FXCollections.observableArrayList(
                        new PieChart.Data("0", setting[0]),
                        new PieChart.Data("1", setting[1]),
                        new PieChart.Data("2", setting[2]),
                        new PieChart.Data("3", setting[3]),
                        new PieChart.Data("4", setting[4]),
                        new PieChart.Data("5", setting[5]));
        settingChart.setMaxSize(200, 200);
        settingChart.setData(settingData);
        settingChart.setClockwise(true);
        settingChart.setLabelsVisible(true);
        settingChart.setLegendVisible(true);
        settingChart.setLegendSide(Side.RIGHT);
        settingChart.setLayoutX(charactersChart.getLayoutX());
        settingChart.setLayoutY(charactersChart.getLayoutY() + 210);

        ObservableList<PieChart.Data> wbData =
                FXCollections.observableArrayList(
                        new PieChart.Data("0", wb[0]),
                        new PieChart.Data("1", wb[1]),
                        new PieChart.Data("2", wb[2]),
                        new PieChart.Data("3", wb[3]),
                        new PieChart.Data("4", wb[4]),
                        new PieChart.Data("5", wb[5]));
        wbChart.setMaxSize(200, 200);
        wbChart.setData(wbData);
        wbChart.setClockwise(true);
        wbChart.setLabelsVisible(true);
        wbChart.setLegendVisible(true);
        wbChart.setLegendSide(Side.RIGHT);
        wbChart.setLayoutX(spiceChart.getLayoutX() + 600);
        wbChart.setLayoutY(spiceChart.getLayoutY());

        ObservableList<PieChart.Data> wsData =
                FXCollections.observableArrayList(
                        new PieChart.Data("0", ws[0]),
                        new PieChart.Data("1", ws[1]),
                        new PieChart.Data("2", ws[2]),
                        new PieChart.Data("3", ws[3]),
                        new PieChart.Data("4", ws[4]),
                        new PieChart.Data("5", ws[5]));
        wsChart.setMaxSize(200, 200);
        wsChart.setData(wsData);
        wsChart.setClockwise(true);
        wsChart.setLabelsVisible(true);
        wsChart.setLegendVisible(true);
        wsChart.setLegendSide(Side.RIGHT);
        wsChart.setLayoutX(charactersChart.getLayoutX() + 600);
        wsChart.setLayoutY(charactersChart.getLayoutY());
    }
}
