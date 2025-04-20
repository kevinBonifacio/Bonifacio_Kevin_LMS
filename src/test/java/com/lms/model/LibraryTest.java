package com.lms.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;
    private ArrayList<Book> books;

    @BeforeEach
    void setup() {
        library = new Library();
        books = new ArrayList<>();

        books.add(new Book("001", "1984", "Orwell", "Dystopian", false, null));
        books.add(new Book("002", "Brave New World", "Huxley", "Sci-Fi", true, LocalDate.now().plusWeeks(4)));
    }

    @Test
    void testCheckOutAvailableBook() {
        String result = library.checkOutBook("1984", books);
        assertTrue(result.contains("Due date"), "Book should be checked out with a due date.");
    }

    @Test
    void testCheckOutAlreadyBorrowedBook() {
        String result = library.checkOutBook("Brave New World", books);
        assertEquals("This book has already been borrowed", result);
    }

    @Test
    void testCheckOutNonexistentBook() {
        String result = library.checkOutBook("Nonexistent Title", books);
        assertEquals("No match found!", result);
    }

    @Test
    void testCheckInBorrowedBook() {
        String result = library.checkInBook("Brave New World", books);
        assertEquals("Book checked in!", result);
    }

    @Test
    void testCheckInBookNotBorrowed() {
        String result = library.checkInBook("1984", books);
        assertEquals("This book has already been returned", result);
    }

    @Test
    void testCheckInNonexistentBook() {
        String result = library.checkInBook("Fake Book", books);
        assertEquals("No match found!", result);
    }
}
