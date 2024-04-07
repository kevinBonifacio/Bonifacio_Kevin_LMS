package com.lms.model;

import java.time.LocalDate;
import java.util.Random;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 07 April 2024
 * Book.java
 * This class creates an object that will contain all the pertinent information about the books,
 * ensuring an easy and organized access to the variables.
 */
public class Book {
    private String barcode;
    private final String title;
    private final String author;
    private final String genre;
    private boolean status;
    private LocalDate dueDate;

    public Book(String a_title, String a_name, String a_genre, boolean status, LocalDate a_dueDate) {
        generateCode(a_title, a_name);
        this.title = a_title;
        this.author = a_name;
        this.genre = a_genre;
        this.status = status;
        this.dueDate = a_dueDate;
    }

    public Book(String barcode, String a_title, String a_name, String a_genre, boolean status, LocalDate a_dueDate) {
        this.barcode = barcode;
        this.title = a_title;
        this.author = a_name;
        this.genre = a_genre;
        this.status = status;
        this.dueDate = a_dueDate;
    }

    public String getBarcode() {
        return this.barcode;
    }
    public String getTitle() {
        return this.title;
    }
    public String getAuthor() {
        return this.author;
    }
    public String getGenre() {
        return this.genre;
    }
    public LocalDate getDueDate() {
        return this.dueDate;
    }
    public boolean isCheckout() {
        return this.status;
    }
    public void checkOut() {
        this.status = true;
    }
    public void checkIn() {
        this.status = false;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /*
     * method: generateCode
     * parameters: (2)String
     * return: none
     * purpose: Generates a barcode using the title first letter, the author first initial and a random number.
     */
    private void generateCode(String a_title, String a_name) {
        Random random = new Random();
        this.barcode = a_title.charAt(0)+ "-" + (random.nextInt(900) + 100) + "-" + a_name.charAt(0);
    }

    @Override
    public String toString() {
        String bookStr =
                "Barcode: " + barcode +
                ", Title: " + title +
                ", Author: " + author +
                ", Genre: " + genre +
                ", Checked Out: " + status;
        if (this.dueDate == null) {
            return bookStr;
        } else {
            return bookStr + ", Due Date: " + dueDate;
        }
    }
}
