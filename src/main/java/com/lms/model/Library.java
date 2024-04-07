package com.lms.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 07 April 2024
 * Library.java
 * This class creates a library of books that will contain all the books objects from the text file provided.
 */
public class Library {
    DatabaseConnection conn = new DatabaseConnection();
    Connection connectDB = conn.connect();

    private ArrayList<Book> collection = new ArrayList<>();

    public void updateCollection(ArrayList<Book> collection) {
        this.collection = collection;
    }

    /*
     * method: addBooksFromFile
     * parameters: String
     * return: boolean
     * purpose: add books to the collection array.
     */

    public boolean addBooksFromFile(String fileName) {
        boolean success = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    String genre = parts[2].trim();
                    collection.add(new Book(title, author, genre, false, null));
                }
            }
            System.out.println("Books added from file.\n" + collection);
            success = true;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        //upload data to the book
        try {
            for (Book book : collection) {
                String barcode = book.getBarcode();
                String title = book.getTitle();
                String author = book.getAuthor();
                String genre = book.getGenre();

                String sqlQuery = "INSERT INTO Book (Barcode, Title, Author, Genre, Status, Due_Date) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connectDB.prepareStatement(sqlQuery);
                ps.setString(1, barcode);
                ps.setString(2, title);
                ps.setString(3, author);
                ps.setString(4, genre);
                ps.setInt(5, 0);
                ps.executeUpdate();
            }

        } catch ( Exception e ) {
            System.err.println(e.getMessage());
        }

        return success;
    }

    /*
     * method: removeBookByBarcode
     * parameters: String
     * return: boolean
     * purpose: remove a book from the collection using a barcode.
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

    /*
     * method: removeBookByTitle
     * parameters: String
     * return: boolean
     * purpose: remove a book from the collection using a title.
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

    /*
     * method: checkOutBook
     * parameters: String
     * return: String
     * purpose: allows user to borrow (check out) books.
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

    /*
     * method: checkInBook
     * parameters: String
     * return: String
     * purpose: allows user to return (check in) books.
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
