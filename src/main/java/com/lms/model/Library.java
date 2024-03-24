package com.lms.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 24 March 2024
 * Library.java
 * This class creates a library of books that will contain all the books objects from the text file provided.
 */
public class Library {
    private ArrayList<Book> collection = new ArrayList<>();

    public ArrayList<Book> getCollection() {
        return this.collection;
    }

    public void updateCollection(ArrayList<Book> collection) {
        this.collection = collection;
    }

    /*
     * method: addBooksFromFile
     * parameters: String
     * return: none
     * purpose: add books to the collection array.
     */
    public boolean addBooksFromFile(String fileName) {
        boolean success = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    collection.add(new Book(title, author));
                }
            }
            System.out.println("Books added from file.");
            success = true;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
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

        if (collection.removeIf(book -> book.getBarcode().equalsIgnoreCase(barcode))) {
            System.out.println("Book removed.");
            success = true;
        } else {
            System.out.println("This barcode is not part of the collection.");
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

        if (collection.removeIf(book -> book.getTitle().equalsIgnoreCase(title))) {
            System.out.println("Book removed.");
            success = true;
        } else {
            System.out.println("This title is not part of the collection.");
        }

        return success;
    }

    /*
     * method: checkOutBook
     * parameters: String
     * return: String
     * purpose: allows user to borrow (check out) books.
     */
    public String checkOutBook(String title) {
        boolean foundMatch = false;
        String dateString = null;
        LocalDate dueDate;

        for (Book book : collection) {
            if (book.getTitle().equalsIgnoreCase(title) && !book.isCheckedOut()) {
                book.checkOut();
                dueDate = LocalDate.now().plusWeeks(4);
                book.setDueDate(dueDate);
                dateString = "Book checked out! Due date: " + dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                foundMatch = true;
                break;
            } else if (book.getTitle().equalsIgnoreCase(title) && book.isCheckedOut()) {
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
    public String checkInBook(String title) {
        boolean foundMatch = false;
        String dateString = null;

        for (Book book : collection) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isCheckedOut()) {
                book.checkIn();
                dateString = "Book checked in!";
                book.setDueDate(null);
                foundMatch = true;
                break;
            } else if (book.getTitle().equalsIgnoreCase(title) && !book.isCheckedOut()) {
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
