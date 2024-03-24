package com.lms.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 24 March 2024
 * Book.java
 * This class creates an object that will contain all the pertinent information about the books,
 * ensuring an easy and organized access to the variables.
 */
public class Book {
    private final String title;
    private final String author;
    private String barcode;
    private boolean checkedOut;
    private LocalDate dueDate;

    public Book(String a_title, String a_name) {
        this.title = a_title;
        this.author = a_name;
        this.checkedOut = false;
        generateCode(a_title, a_name);
    }

    public String getTitle() {
        return this.title;
    }
    public String getAuthor() {
        return this.author;
    }
    public String getBarcode() {
        return this.barcode;
    }
    public LocalDate getDueDate() {
        return this.dueDate;
    }
    public boolean isCheckedOut() {
        return this.checkedOut;
    }

    public void checkOut() {
        this.checkedOut = true;
    }
    public void checkIn() {
        this.checkedOut = false;
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
    public void generateCode(String a_title, String a_name) {
        Random random = new Random();
        this.barcode = a_title.charAt(0)+ "-" + (random.nextInt(900) + 100) + "-" + a_name.charAt(0);
    }

    @Override
    public String toString() {
        String bookStr =
                "Barcode: " + barcode +
                ", Title: " + title +
                ", Author: " + author +
                ", Checked Out: " + checkedOut;
        if (this.dueDate == null) {
            return bookStr;
        } else {
            return bookStr + ", Due Date: " + dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
    }
}
