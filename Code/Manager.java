package com.maeganlucas.BookNook;

import java.sql.*;
import java.util.ArrayList;
public class Manager {
    private static Connection connection = null;
    private ArrayList authorsBooks = new ArrayList<String[]>();
    private ArrayList readBooks = new ArrayList<String[]>();
    private ArrayList readingBooks = new ArrayList<String[]>();
    private ArrayList tbrBooks = new ArrayList<String[]>();
    private ArrayList search = new ArrayList<String[]>();
    private static String nameOfUser;
    private int entry;
    private String[] bookDetails = new String[8];
    private float bookVolume;



    public static void main(String[] args) {

        String url = "jdbc:mariadb://localhost:3306/BookNook";
        String user = "root";
        String password = "******"; //Password removed for privacy purposes

        try{
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int checkUser(String username, String password) {
        try {
            String query1 = "SELECT username, password, name FROM Authors WHERE username=? AND password=?";
            String query2 = "SELECT username, password, name FROM Readers WHERE username=? AND password=?";
            PreparedStatement stmt1 = connection.prepareStatement(query1);
            PreparedStatement stmt2 = connection.prepareStatement(query2);
            stmt1.setString(1, username);
            stmt1.setString(2, password);
            stmt2.setString(1, username);
            stmt2.setString(2, password);
            ResultSet result1 = stmt1.executeQuery();
            ResultSet result2 = stmt2.executeQuery();


            while(result1.next()){
                String testUsername = result1.getString("username");
                String testPassword = result1.getString("password");
                if((testUsername.equals(username)) && (testPassword.equals(password))){
                    nameOfUser = result1.getString("name");
                    return 1;
                }
            }

            while (result2.next()){
                String testUsername = result2.getString("username");
                String testPassword = result2.getString("password");
                if((testUsername.equals(username)) && (testPassword.equals(password))) {
                    nameOfUser = result2.getString("name");
                    return 2;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void printAuthorsBooks(String username) {
        try {
            if(!authorsBooks.isEmpty()){
                authorsBooks.clear();
            }
            String query = "SELECT ISBN, title, author, series, volume, publisher, genre, categories, synopsis FROM (BookAuthor ba NATURAL JOIN BookDetails bd) WHERE ba.authorUsername = ? AND ba.title = bd.title AND ba.author = bd.author";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            while(result.next()){
                String[] book = new String[5];
                book[0] = result.getString("title");
                book[1] = result.getString("series");
                book[2] = String.valueOf(result.getFloat("volume"));
                book[3] = result.getString("ISBN");
                book[4] = result.getString("publisher");

                authorsBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String[] printProfile(String username, int user) {
        String[] profile = new String[3];
        if (user == 1) {
            try {
                String query = "SELECT * FROM Authors WHERE username = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet results = stmt.executeQuery();
                while (results.next()) {
                    profile[0] = results.getString("name");
                    profile[1] = results.getString("username");
                    profile[2] = results.getString("password");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                String query = "SELECT * FROM Readers WHERE username = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet results = stmt.executeQuery();
                while (results.next()) {
                    profile[0] = results.getString("name");
                    profile[1] = results.getString("username");
                    profile[2] = results.getString("password");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return profile;
    }

    public void createUser(String username, String password, String name, int user) {
        try {
            if(user == 1) {
                String authorQuery = "INSERT into Authors VALUES (?, ?, ?)";
                PreparedStatement authorStatement = connection.prepareStatement(authorQuery);
                authorStatement.setString(1, username);
                authorStatement.setString(2, password);
                authorStatement.setString(3, name);
                int rows = authorStatement.executeUpdate();
                if(rows != 1) {
                    System.out.print("not updated");
                }
            } else if (user == 2) {
                String readerQuery = "INSERT into Readers VALUES (?, ?, ?)";
                PreparedStatement readerStatement = connection.prepareStatement(readerQuery);
                readerStatement.setString(1, username);
                readerStatement.setString(2, password);
                readerStatement.setString(3, name);
                int rows = readerStatement.executeUpdate();
                if(rows != 1) {
                    System.out.print("not updated");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createBook(String title, String author, String series, Float volume, String ISBN, String genre, String categories, String publisher, String synopsis, String username) {
        try {
            String query = "SELECT * FROM BookAuthor WHERE ISBN = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ISBN);
            ResultSet results = statement.executeQuery();
            if(results.next()){
                String update = "UPDATE BookAuthor SET authorUsername = ? WHERE ISBN = ?";
                PreparedStatement stmt = connection.prepareStatement(update);
                stmt.setString(1, username);
                stmt.setString(2, ISBN);
                stmt.executeUpdate();
            } else {
                String baQuery = "INSERT into BookAuthor VALUES (?, ?, ?, ?, ?)";
                PreparedStatement baStmt = connection.prepareStatement(baQuery);
                baStmt.setString(1, ISBN);
                baStmt.setString(2, title);
                baStmt.setString(3, author);
                baStmt.setString(4, publisher);
                baStmt.setString(5, username);
                int baRow = baStmt.executeUpdate();
                if(baRow != 1) {
                    System.out.println("not updated");
                }

                String bdQuery = "INSERT into BookDetails VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement bdStmt = connection.prepareStatement(bdQuery);
                bdStmt.setString(1, title);
                bdStmt.setString(2, author);
                bdStmt.setString(3, series);
                bdStmt.setFloat(4, volume);
                bdStmt.setString(5, genre);
                bdStmt.setString(6, categories);
                bdStmt.setString(7, synopsis);
                int bdRow = bdStmt.executeUpdate();
                if(bdRow != 1) {
                    System.out.println("not updated");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void recallBook(String ISBN) {
        try {
            String query = "SELECT title, author, series, volume, ISBN, genre, categories, publisher, synopsis FROM (BookAuthor ba natural join BookDetails bd) WHERE ba.title = bd.title AND ba.author = bd.author AND ba.ISBN = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, ISBN);
            ResultSet results = stmt.executeQuery();
            while(results.next()){
                bookDetails[0] =  results.getString("title");
                bookDetails[1] = results.getString("author");
                bookDetails[2] = results.getString("series");
                bookVolume = results.getFloat("volume");
                bookDetails[3] = results.getString("genre");
                bookDetails[4] = results.getString("categories");
                bookDetails[5] = results.getString("publisher");
                bookDetails[6] = results.getString("synopsis");
            }
        } catch (SQLException e) {

        }
    }

    public void updateBook(String title, String author, String series, Float volume, String ISBN, String genre, String categories, String publisher, String synopsis, String oldISBN, String username) {
        try {
            String query = "UPDATE (BookAuthor ba NATURAL JOIN BookDetails bd) SET title = ?, author = ?, series = ?, volume = ?, ISBN = ?, genre = ?, categories = ?, publisher = ?, synopsis = ? WHERE ISBN = ? AND ba.authorUsername = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, series);
            stmt.setFloat(4, volume);
            stmt.setString(5, ISBN);
            stmt.setString(6, genre);
            stmt.setString(7, categories);
            stmt.setString(8, publisher);
            stmt.setString(9, synopsis);
            stmt.setString(10, oldISBN);
            stmt.setString(11, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String ISBN) {
        try {
            String query1 = "DELETE ba.*, bd.* FROM BookAuthor ba, BookDetails bd WHERE ba.title = bd.title AND ba.author = bd.author AND ba.isbn = ?";
            PreparedStatement stmt1 = connection.prepareStatement(query1);
            stmt1.setString(1, ISBN);
            stmt1.executeUpdate();
            String query2 = "DELETE r.*, wr.* FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID and r.ISBN = ?";
            PreparedStatement stmt2 = connection.prepareStatement(query2);
            stmt2.setString(1, ISBN);
            stmt2.executeUpdate();
            String query3 = "DELETE rb.* FROM ReadsBook rb WHERE rb.ISBN = ?";
            PreparedStatement stmt3 = connection.prepareStatement(query3);
            stmt3.setString(1, ISBN);
            stmt3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAuthor(String name, String username, String password, String oldUsername) {
        try {
            String query1 = "UPDATE Authors SET name = ?, username = ?, password = ? WHERE username = ?";
            PreparedStatement stmt1 = connection.prepareStatement(query1);
            stmt1.setString(1, name);
            stmt1.setString(2, username);
            stmt1.setString(3, password);
            stmt1.setString(4, oldUsername);
            stmt1.executeUpdate();

            String query2 = "UPDATE BookAuthor SET authorUsername = ? WHERE authorUsername = ?";
            PreparedStatement stmt2 = connection.prepareStatement(query2);
            stmt2.setString(1, username);
            stmt2.setString(2, oldUsername);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReader(String name, String username, String password, String oldUsername) {
        try {
            String query1 = "UPDATE Readers SET name = ?, username = ?, password = ? WHERE username = ?";
            PreparedStatement stmt1 = connection.prepareStatement(query1);
            stmt1.setString(1, name);
            stmt1.setString(2, username);
            stmt1.setString(3, password);
            stmt1.setString(4, oldUsername);
            stmt1.executeUpdate();

            String query2 = "UPDATE ReadsBook SET username = ? WHERE username = ?";
            PreparedStatement stmt2 = connection.prepareStatement(query2);
            stmt2.setString(1, username);
            stmt2.setString(2, oldUsername);
            stmt2.executeUpdate();

            String query3 = "UPDATE WritesReview SET username = ? WHERE username = ?";
            PreparedStatement stmt3 = connection.prepareStatement(query3);
            stmt3.setString(1, username);
            stmt3.setString(2, oldUsername);
            stmt3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteProfile(String username, int user) {
        try {
            if (user == 1 ) {
                String query = "DELETE a.* FROM Authors a WHERE username = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.executeUpdate();

                String query2 = "UPDATE BookAuthor SET authorUsername = ? WHERE authorUsername = ?";
                PreparedStatement stmt2 = connection.prepareStatement(query2);
                stmt2.setString(1, null);
                stmt2.setString(2, username);
                stmt2.executeUpdate();
            } else {
                String query = "DELETE r.* FROM Readers r WHERE username = ?";
                PreparedStatement stmt1 = connection.prepareStatement(query);
                stmt1.setString(1, username);
                stmt1.executeUpdate();

                String query2 = "DELETE wr.*, r.* FROM WritesReview wr, Reviews r WHERE wr.reviewID = r.reviewID AND wr.username = ?";
                PreparedStatement stmt2 = connection.prepareStatement(query2);
                stmt2.setString(1, username);
                stmt2.executeUpdate();

                String query3 = "DELETE rb.* FROM ReadsBook rb WHERE username = ?";
                PreparedStatement stmt3 = connection.prepareStatement(query3);
                stmt3.setString(1, username);
                stmt3.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void pullReadBooks(String username) {
        try {
            String query = "SELECT ba.title, ba.author, bd.series, bd.volume, ba.ISBN, ba.publisher FROM ReadsBook rb, BookAuthor ba, BookDetails bd WHERE ba.title = bd.title AND ba.author = bd.author AND ba.ISBN = rb.ISBN AND username = ? and readingStatus = 'read'";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                String[] book = new String[6];
                book[0] = results.getString("title");
                book[1] = results.getString("author");
                book[2] = results.getString("series");
                book[3] = String.valueOf(results.getFloat("volume"));
                book[4] = results.getString("ISBN");
                book[5] = results.getString("publisher");
                readBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void pullReadingBooks(String username) {
        try {
            String query = "SELECT ba.title, ba.author, bd.series, bd.volume, ba.ISBN, ba.publisher FROM ReadsBook rb, BookAuthor ba, BookDetails bd WHERE ba.title = bd.title AND ba.author = bd.author AND ba.ISBN = rb.ISBN AND username = ? and readingStatus = 'reading'";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                String[] book = new String[6];
                book[0] = results.getString("title");
                book[1] = results.getString("author");
                book[2] = results.getString("series");
                book[3] = String.valueOf(results.getFloat("volume"));
                book[4] = results.getString("ISBN");
                book[5] = results.getString("publisher");
                readingBooks.add(book);
            }
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }

    public void pullTBR(String username) {
        try {
            String query = "SELECT ba.title, ba.author, bd.series, bd.volume, ba.ISBN, ba.publisher FROM ReadsBook rb, BookAuthor ba, BookDetails bd WHERE ba.title = bd.title AND ba.author = bd.author AND ba.ISBN = rb.ISBN AND username = ? and readingStatus = 'tbr'";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                String[] book = new String[6];
                book[0] = results.getString("title");
                book[1] = results.getString("author");
                book[2] = results.getString("series");
                book[3] = String.valueOf(results.getFloat("volume"));
                book[4] = results.getString("ISBN");
                book[5] = results.getString("publisher");
                tbrBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void pullSearch(String newSearch) {
        try {
            String searchString = "%" + newSearch + "%";
            String query = "SELECT ba.title, ba.author, bd.series, bd.volume, ba.ISBN, ba.publisher, bd.synopsis FROM BookAuthor ba, BookDetails bd WHERE ba.title = bd.title AND ba.author = bd.author AND ba.title LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchString);
            ResultSet results = stmt.executeQuery();
            while(results.next()) {
                String[] searchResult = new String[7];
                searchResult[0] = results.getString("ba.title");
                searchResult[1] = results.getString("ba.author");
                searchResult[2] = results.getString("bd.series");
                searchResult[3] = String.valueOf(results.getString("bd.volume"));
                searchResult[4] = results.getString("ba.ISBN");
                searchResult[5] = results.getString("ba.publisher");
                searchResult[6] = results.getString("bd.synopsis");
                search.add(searchResult);
            }

            String query2 = "SELECT ba.title, ba.author, bd.series, bd.volume, ba.ISBN, ba.publisher, bd.synopsis FROM BookAuthor ba, BookDetails bd WHERE ba.title = bd.title AND ba.author = bd.author AND ba.author LIKE ?";
            PreparedStatement stmt2 = connection.prepareStatement(query2);
            stmt2.setString(1, searchString);
            ResultSet results2 = stmt2.executeQuery();
            while(results2.next()) {
                String[] searchResult = new String[7];
                searchResult[0] = results2.getString("ba.title");
                searchResult[1] = results2.getString("ba.author");
                searchResult[2] = results2.getString("bd.series");
                searchResult[3] = String.valueOf(results2.getString("bd.volume"));
                searchResult[4] = results2.getString("ba.ISBN");
                searchResult[5] = results2.getString("ba.publisher");
                searchResult[6] = results2.getString("bd.synopsis");
                search.add(searchResult);
            }

            String query3 = "SELECT ba.title, ba.author, bd.series, bd.volume, ba.ISBN, ba.publisher, bd.synopsis FROM BookAuthor ba, BookDetails bd WHERE ba.title = bd.title AND ba.author = bd.author AND bd.series LIKE ?";
            PreparedStatement stmt3 = connection.prepareStatement(query3);
            stmt3.setString(1, searchString);
            ResultSet results3 = stmt3.executeQuery();
            while(results3.next()) {
                String[] searchResult = new String[7];
                searchResult[0] = results3.getString("ba.title");
                searchResult[1] = results3.getString("ba.author");
                searchResult[2] = results3.getString("bd.series");
                searchResult[3] = String.valueOf(results3.getString("bd.volume"));
                searchResult[4] = results3.getString("ba.ISBN");
                searchResult[5] = results3.getString("ba.publisher");
                searchResult[6] = results3.getString("bd.synopsis");
                search.add(searchResult);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getReadingStatus(String username, String ISBN) {
        String status = null;
        entry = 0;
        try {
            String query = "SELECT readingStatus FROM ReadsBook rb WHERE username = ? AND ISBN = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, ISBN);
            ResultSet results = stmt.executeQuery();
            while(results.next()) {
                String result = results.getString("readingStatus");

                if(result.equalsIgnoreCase("reading")) {
                    status = "Reading";
                } else if(result.equalsIgnoreCase("read")) {
                    status = "Read";
                } else if(result.equalsIgnoreCase("tbr")) {
                    status = "TBR";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    public void setReadingStatus(String username, String ISBN, String readingStatus) {
        try {
            String query = "INSERT INTO ReadsBook VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, ISBN);
            stmt.setString(3, readingStatus);
            int row = stmt.executeUpdate();
            if(row != 1) {
                System.out.println("not updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeReadingStatus(String username, String ISBN) {
        try {
            String query = "DELETE rb.* FROM ReadsBook rb WHERE username = ? AND ISBN = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, ISBN);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReadingStatus(String username, String ISBN, String readingStatus) {
        try {
            String query = "UPDATE ReadsBook SET readingStatus = ? WHERE username = ? AND ISBN = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, readingStatus);
            stmt.setString(2, username);
            stmt.setString(3, ISBN);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createReview(String username, String ISBN, Float storyline, Float plot, Float setting, Float spice, Float characters, Float wb, Float ws, String dateStarted, String dateFinished, String thoughts) {
        int oldReviewID = 0;
        try {
            String query = "SELECT MAX(reviewID) FROM Reviews";
            Statement stmt = connection.prepareStatement(query);
            ResultSet result = stmt.executeQuery(query);
            while(result.next()) {
                oldReviewID = result.getInt("MAX(reviewID)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int newReviewID = oldReviewID + 1;

        try {
            java.sql.Date ds = java.sql.Date.valueOf(dateStarted);
            java.sql.Date df = java.sql.Date.valueOf(dateFinished);
            String query1 = "INSERT INTO Reviews VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt1 = connection.prepareStatement(query1);
            stmt1.setInt(1, newReviewID);
            stmt1.setString(2, ISBN);
            stmt1.setFloat(3, storyline);
            stmt1.setFloat(4, plot);
            stmt1.setFloat(5, setting);
            stmt1.setFloat(6, spice);
            stmt1.setFloat(7, characters);
            stmt1.setFloat(8, wb);
            stmt1.setFloat(9, ws);
            stmt1.setDate(10, ds);
            stmt1.setDate(11, df);
            stmt1.setString(12, thoughts);
            int row = stmt1.executeUpdate();
            if(row != 1) {
                System.out.print("not updated");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        try {
            String query2 = "INSERT INTO WritesReview VALUES (?, ?)";
            PreparedStatement stmt2 = connection.prepareStatement(query2);
            stmt2.setString(1, username);
            stmt2.setInt(2, newReviewID);
            int row2 = stmt2.executeUpdate();
            if(row2 != 1) {
                System.out.print("not updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] pullReviewDetails(String username, String ISBN) {
        String[] review = new String[10];
        try {
            String query = "SELECT r.storylineRating, r.plotRating, r.settingRating, r.spiceRating, r.charactersRating, r.wbRating, r.wsRating, r.dateStarted, r.dateFinished, r.writtenReview from Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND wr.username = ? AND r.ISBN = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, ISBN);
            ResultSet results = stmt.executeQuery();
            while(results.next()) {
                review[0] = String.valueOf(results.getFloat("storylineRating"));
                review[1] = String.valueOf(results.getFloat("plotRating"));
                review[2] = String.valueOf(results.getFloat("settingRating"));
                review[3] = String.valueOf(results.getFloat("spiceRating"));
                review[4] = String.valueOf(results.getFloat("charactersRating"));
                review[5] = String.valueOf(results.getFloat("wbRating"));
                review[6] = String.valueOf(results.getFloat("wsRating"));
                review[7] = String.valueOf(results.getDate("dateStarted"));
                review[8] = String.valueOf(results.getDate("dateFinished"));
                review[9] = results.getString("writtenReview");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return review;
    }

    public void updateReview(String username, String ISBN, Float storyline, Float plot, Float setting, Float spice, Float characters, Float wb, Float ws, String dateStarted, String dateFinished, String thoughts) {
        int reviewID = 0;
        try {
            String find = "SELECT r.reviewID FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND r.ISBN = ? AND wr.username = ?";
            PreparedStatement findstmt = connection.prepareStatement(find);
            findstmt.setString(1, ISBN);
            findstmt.setString(2, username);
            ResultSet result = findstmt.executeQuery();
            while(result.next()) {
                reviewID = result.getInt("reviewID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String query = "UPDATE Reviews SET storylineRating = ?, plotRating = ?, settingRating = ?, spiceRating = ?, charactersRating = ?, wbRating = ?, wsRating = ?, dateStarted = ?, dateFinished = ?, writtenReview = ? WHERE reviewID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setFloat(1, storyline);
            stmt.setFloat(2, plot);
            stmt.setFloat(3, setting);
            stmt.setFloat(4, spice);
            stmt.setFloat(5, characters);
            stmt.setFloat(6, wb);
            stmt.setFloat(7, ws);
            java.sql.Date ds = java.sql.Date.valueOf(dateStarted);
            stmt.setDate(8, ds);
            java.sql.Date df = java.sql.Date.valueOf(dateFinished);
            stmt.setDate(9, df);
            stmt.setString(10, thoughts);
            stmt.setInt(11, reviewID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReview(String username, String ISBN) {
        try {
            String query = "DELETE r.*, wr.* FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND r.ISBN = ? AND wr.username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, ISBN);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int verify(int user, String username) {
        int duplicate = 0;
        try {
            if (user == 1) {
                String query = "SELECT username FROM Authors WHERE username = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet result = stmt.executeQuery();
                while (result.next()) {
                    duplicate = 1;
                }
            } else {
                String query = "SELECT username FROM Readers WHERE username = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet result = stmt.executeQuery();
                while (result.next()) {
                    duplicate = 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return duplicate;
    }

    public ArrayList<int[]> getAuthorReviews(String ISBN) {
        ArrayList reviews = new ArrayList<int[]>();
        int[] storylineReviews = new int[6];
        int[] plotReviews = new int[6];
        int[] settingReviews = new int[6];
        int[] spiceReviews = new int[6];
        int[] characterReviews = new int[6];
        int[] wbReviews = new int[6];
        int[] wsReviews = new int[6];

        try {
            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(reviewID) AS storyline FROM Reviews WHERE ISBN = ? AND storylineRating >= ? AND storylineRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, ISBN);
                stmt.setInt(2, i);
                stmt.setInt(3, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("storyline");
                    storylineReviews[i] = count;
                }
            }
            reviews.add(storylineReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(reviewID) AS plot FROM Reviews WHERE ISBN = ? AND plotRating >= ? AND plotRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, ISBN);
                stmt.setInt(2, i);
                stmt.setInt(3, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("plot");
                    plotReviews[i] = count;
                }
            }
            reviews.add(plotReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(reviewID) AS setting FROM Reviews WHERE ISBN = ? AND settingRating >= ? AND settingRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, ISBN);
                stmt.setInt(2, i);
                stmt.setInt(3, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("setting");
                    settingReviews[i] = count;
                }
            }
            reviews.add(settingReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(reviewID) AS spice FROM Reviews WHERE ISBN = ? AND spiceRating >= ? AND spiceRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, ISBN);
                stmt.setInt(2, i);
                stmt.setInt(3, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("spice");
                    spiceReviews[i] = count;
                }
            }
            reviews.add(spiceReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(reviewID) AS characters FROM Reviews WHERE ISBN = ? AND charactersRating >= ? AND charactersRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, ISBN);
                stmt.setInt(2, i);
                stmt.setInt(3, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("characters");
                    characterReviews[i] = count;
                }
            }
            reviews.add(characterReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(reviewID) AS wb FROM Reviews WHERE ISBN = ? AND wbRating >= ? AND wbRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, ISBN);
                stmt.setInt(2, i);
                stmt.setInt(3, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("wb");
                    wbReviews[i] = count;
                }
            }
            reviews.add(wbReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(reviewID) AS ws FROM Reviews WHERE ISBN = ? AND wsRating >= ? AND wsRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, ISBN);
                stmt.setInt(2, i);
                stmt.setInt(3, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("ws");
                    wsReviews[i] = count;
                }
            }
            reviews.add(wsReviews);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public ArrayList<int[]> getReaderReviews(String username, String dateStarted, String dateFinished) {
        ArrayList reviews = new ArrayList<int[]>();
        int[] storylineReviews = new int[6];
        int[] plotReviews = new int[6];
        int[] settingReviews = new int[6];
        int[] spiceReviews = new int[6];
        int[] characterReviews = new int[6];
        int[] wbReviews = new int[6];
        int[] wsReviews = new int[6];
        int[] genres = new int[6];
        java.sql.Date ds = java.sql.Date.valueOf(dateStarted);
        java.sql.Date df = java.sql.Date.valueOf(dateFinished);

        try {
            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(r.reviewID) AS storyline FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND storylineRating >= ? AND storylineRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setDate(2, ds);
                stmt.setDate(3, df);
                stmt.setInt(4, i);
                stmt.setInt(5, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("storyline");
                    storylineReviews[i] = count;
                }
            }
            reviews.add(storylineReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(r.reviewID) AS plot FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND plotRating >= ? AND plotRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setDate(2, ds);
                stmt.setDate(3, df);
                stmt.setInt(4, i);
                stmt.setInt(5, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("plot");
                    plotReviews[i] = count;
                }
            }
            reviews.add(plotReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(r.reviewID) AS setting FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND settingRating >= ? AND settingRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setDate(2, ds);
                stmt.setDate(3, df);
                stmt.setInt(4, i);
                stmt.setInt(5, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("setting");
                    settingReviews[i] = count;
                }
            }
            reviews.add(settingReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(r.reviewID) AS spice FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND spiceRating >= ? AND spiceRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setDate(2, ds);
                stmt.setDate(3, df);
                stmt.setInt(4, i);
                stmt.setInt(5, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("spice");
                    spiceReviews[i] = count;
                }
            }
            reviews.add(spiceReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(r.reviewID) AS characters FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND charactersRating >= ? AND charactersRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setDate(2, ds);
                stmt.setDate(3, df);
                stmt.setInt(4, i);
                stmt.setInt(5, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("characters");
                    characterReviews[i] = count;
                }
            }
            reviews.add(characterReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(r.reviewID) AS wb FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND wbRating >= ? AND wbRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setDate(2, ds);
                stmt.setDate(3, df);
                stmt.setInt(4, i);
                stmt.setInt(5, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("wb");
                    wbReviews[i] = count;
                }
            }
            reviews.add(wbReviews);

            for (int i = 0; i < 6; i++) {
                int k = i + 1;
                String query = "SELECT COUNT(r.reviewID) AS ws FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND wsRating >= ? AND wsRating < ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setDate(2, ds);
                stmt.setDate(3, df);
                stmt.setInt(4, i);
                stmt.setInt(5, k);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    int count = result.getInt("ws");
                    wsReviews[i] = count;
                }
            }
            reviews.add(wsReviews);

            String query1 = "SELECT COUNT(r.reviewID) AS romance FROM Reviews r, WritesReview wr, BookDetails bd, BookAuthor ba WHERE r.reviewID = wr.reviewID AND r.ISBN = ba.ISBN AND ba.title = bd.title AND ba.author = bd.author AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND bd.genre = 'romance'";
            PreparedStatement stmt1 = connection.prepareStatement(query1);
            stmt1.setString(1, username);
            stmt1.setDate(2, ds);
            stmt1.setDate(3, df);
            ResultSet result1 = stmt1.executeQuery();
            while (result1.next()) {
                genres[0] = result1.getInt("romance");
            }

            String query2 = "SELECT COUNT(r.reviewID) AS fantasy FROM Reviews r, WritesReview wr, BookDetails bd, BookAuthor ba WHERE r.reviewID = wr.reviewID AND r.ISBN = ba.ISBN AND ba.title = bd.title AND ba.author = bd.author AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND bd.genre = 'fantasy'";
            PreparedStatement stmt2 = connection.prepareStatement(query2);
            stmt2.setString(1, username);
            stmt2.setDate(2, ds);
            stmt2.setDate(3, df);
            ResultSet result2 = stmt2.executeQuery();
            while (result2.next()) {
                genres[1] = result2.getInt("fantasy");
            }

            String query3 = "SELECT COUNT(r.reviewID) AS mystery FROM Reviews r, WritesReview wr, BookDetails bd, BookAuthor ba WHERE r.reviewID = wr.reviewID AND r.ISBN = ba.ISBN AND ba.title = bd.title AND ba.author = bd.author AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND bd.genre = 'mystery'";
            PreparedStatement stmt3 = connection.prepareStatement(query3);
            stmt3.setString(1, username);
            stmt3.setDate(2, ds);
            stmt3.setDate(3, df);
            ResultSet result3 = stmt3.executeQuery();
            while (result3.next()) {
                genres[2] = result3.getInt("mystery");
            }

            String query4 = "SELECT COUNT(r.reviewID) AS thriller FROM Reviews r, WritesReview wr, BookDetails bd, BookAuthor ba WHERE r.reviewID = wr.reviewID AND r.ISBN = ba.ISBN AND ba.title = bd.title AND ba.author = bd.author AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND bd.genre = 'thriller'";
            PreparedStatement stmt4 = connection.prepareStatement(query4);
            stmt4.setString(1, username);
            stmt4.setDate(2, ds);
            stmt4.setDate(3, df);
            ResultSet result4 = stmt4.executeQuery();
            while (result4.next()) {
                genres[3] = result4.getInt("thriller");
            }

            String query5 = "SELECT COUNT(r.reviewID) AS historical FROM Reviews r, WritesReview wr, BookDetails bd, BookAuthor ba WHERE r.reviewID = wr.reviewID AND r.ISBN = ba.ISBN AND ba.title = bd.title AND ba.author = bd.author AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND bd.genre = 'historical fiction'";
            PreparedStatement stmt5 = connection.prepareStatement(query5);
            stmt5.setString(1, username);
            stmt5.setDate(2, ds);
            stmt5.setDate(3, df);
            ResultSet result5 = stmt5.executeQuery();
            while (result5.next()) {
                genres[4] = result5.getInt("historical");
            }

            String query6 = "SELECT COUNT(r.reviewID) AS science FROM Reviews r, WritesReview wr, BookDetails bd, BookAuthor ba WHERE r.reviewID = wr.reviewID AND r.ISBN = ba.ISBN AND ba.title = bd.title AND ba.author = bd.author AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ? AND bd.genre = 'science fiction'";
            PreparedStatement stmt6 = connection.prepareStatement(query6);
            stmt6.setString(1, username);
            stmt6.setDate(2, ds);
            stmt6.setDate(3, df);
            ResultSet result6 = stmt6.executeQuery();
            while (result6.next()) {
                genres[5] = result6.getInt("science");
            }

            reviews.add(genres);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public int getNumberBooks(String username, String dateStart, String dateFinish) {
        int numberBooks = 0;
        java.sql.Date ds = java.sql.Date.valueOf(dateStart);
        java.sql.Date df = java.sql.Date.valueOf(dateFinish);

        try {
            String query = "SELECT COUNT(r.reviewID) AS count FROM Reviews r, WritesReview wr WHERE r.reviewID = wr.reviewID AND wr.username = ? AND r.dateStarted >= ? AND r.dateFinished <= ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setDate(2, ds);
            stmt.setDate(3, df);
            ResultSet result = stmt.executeQuery();
            while(result.next()) {
                numberBooks = result.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numberBooks;
    }

    public ArrayList<String[]> getAuthorsBooks() {
        return authorsBooks;
    }

    public ArrayList<String[]> getReadBooks() {
        return readBooks;
    }

    public ArrayList<String[]> getReadingBooks() {
        return readingBooks;
    }

    public ArrayList<String[]> getTBRBooks() {
        return tbrBooks;
    }

    public ArrayList<String[]> getSearch() {
        return search;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String[] getBookDetails() {
        return bookDetails;
    }

    public Float getBookVolume() {
        return bookVolume;
    }


}
