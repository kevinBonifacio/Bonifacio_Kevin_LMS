package com.lms.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * CEN 3024 - Software Development 1
 * 14 April 2024
 * Library.java
 * This class creates a library of books that will contain all the books objects from the text file provided.
 * @author Kevin Bonifacio
 */
public class Library {
    DatabaseConnection conn = new DatabaseConnection();
    Connection connectDB = conn.connect();

    private final ArrayList<Book> collection = new ArrayList<>();

    /**
     * method: addBooksFromFile
     * purpose: add books from a sample database (Library) to the application database.
     * @param dataBaseName String
     * @return boolean
     */

    public boolean addBooks(String dataBaseName) {
        boolean success = false;

        try {
            //fetch data from the test dataBase (Library)
            Connection connectNewDB = conn.connect(dataBaseName);
            String selectQuery = "SELECT Barcode, Title, Author, Genre, Status, Due_Date FROM Book";

            Statement statement = connectNewDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(selectQuery);

            while (queryOutput.next()) {
                String barcode = queryOutput.getString("Barcode");
                String title = queryOutput.getString("Title");
                String author = queryOutput.getString("Author");
                String genre = queryOutput.getString("Genre");
                boolean status = queryOutput.getBoolean("Status");

                if (queryOutput.getDate("Due_Date") != null) {
                    LocalDate dueDate = queryOutput.getDate("Due_Date").toLocalDate();
                    this.collection.add(new Book(barcode, title, author, genre, status, dueDate));
                } else {
                    this.collection.add(new Book(barcode, title, author, genre, status, null));
                }
            }

            //upload data to the database
            for (Book book : collection) {
                String barcode = book.getBarcode();
                String title = book.getTitle();
                String author = book.getAuthor();
                String genre = book.getGenre();
                boolean status = book.isCheckout();
                LocalDate dueDate = book.getDueDate();


                String insertQuery = "INSERT INTO Book (Barcode, Title, Author, Genre, Status, Due_Date) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connectDB.prepareStatement(insertQuery);

                ps.setString(1, barcode);
                ps.setString(2, title);
                ps.setString(3, author);
                ps.setString(4, genre);
                ps.setBoolean(5, status);
                if (dueDate != null) {
                    ps.setDate(6, java.sql.Date.valueOf(dueDate));
                }
                ps.executeUpdate();
            }

            success = true;

        } catch ( Exception e ) {
            System.err.println(e.getMessage());
        }

        return success;
    }

    /**
     * method: removeBookByBarcode
     * purpose: remove a book from the collection using a barcode.
     * @param barcode String
     * @return boolean
     */
    public boolean removeBookByBarcode(String barcode) {
        boolean success = false;

        try {
            String sqlQuery = "DELETE FROM Book WHERE Barcode = ?";
            PreparedStatement ps = connectDB.prepareStatement(sqlQuery);
            ps.setString(1, barcode);
            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Book removed.");
                success = true;
            } else {
                System.out.println("This barcode is not part of the collection.");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return success;
    }

    /**
     * method: removeBookByTitle
     * purpose: remove a book from the collection using a title.
     * @param title String
     * @return boolean
     */
    public boolean removeBookByTitle(String title) {
        boolean success = false;

        try {
            String sqlQuery = "DELETE FROM Book WHERE Title = ?";
            PreparedStatement ps = connectDB.prepareStatement(sqlQuery);
            ps.setString(1, title);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Book removed.");
                success = true;
            } else {
                System.out.println("This title is not part of the collection.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return success;
    }

    /**
     * method: checkOutBook
     * purpose: allows user to borrow (check out) books.
     * @param title String
     * @param books ArrayList<Book>
     * @return String
     */
    public String checkOutBook(String title, ArrayList<Book> books) {
        boolean foundMatch = false;
        String dateString = null;
        LocalDate dueDate;

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isCheckout()) {
                book.checkOut();
                dueDate = LocalDate.now().plusWeeks(4);
                book.setDueDate(dueDate);

                try {
                    String sqlQuery = "UPDATE Book SET Status = ?, Due_Date = ? WHERE Title = ?";
                    PreparedStatement ps = connectDB.prepareStatement(sqlQuery);
                    ps.setInt(1, 1);
                    ps.setDate(2, java.sql.Date.valueOf(dueDate));
                    ps.setString(3, title);
                    ps.executeUpdate();

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

                dateString = "Book checked out! Due date: " + dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                foundMatch = true;
                break;
            } else if (book.getTitle().equalsIgnoreCase(title) && book.isCheckout()) {
                dateString = "This book has already been borrowed";
                foundMatch = true;
                break;
            }
        }

        if (!foundMatch) {
            dateString = "No match found!";
        }

        return dateString;
    }

    /**
     * method: checkInBook
     * purpose: allows user to return (check in) books.
     * @param title String
     * @param books ArrayList<Book>
     * @return String
     */
    public String checkInBook(String title, ArrayList<Book> books) {
        boolean foundMatch = false;
        String dateString = null;

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isCheckout()) {
                book.checkIn();
                book.setDueDate(null);

                try {
                    String sqlQuery = "UPDATE Book SET Status = ?, Due_Date = ? WHERE Title = ?";
                    PreparedStatement ps = connectDB.prepareStatement(sqlQuery);
                    ps.setInt(1, 0);
                    ps.setDate(2, null);
                    ps.setString(3, title);
                    ps.executeUpdate();

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

                dateString = "Book checked in!";
                foundMatch = true;
                break;
            } else if (book.getTitle().equalsIgnoreCase(title) && !book.isCheckout()) {
                dateString = "This book has already been returned";
                foundMatch = true;
                break;
            }
        }

        if (!foundMatch) {
            dateString = "No match found!";
        }

        return dateString;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Printing Library Database...\n\n");
        for (Book book : collection) {
            sb.append(book).append("\n");
        }
        return sb.toString();
    }
}
