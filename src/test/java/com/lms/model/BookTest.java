package com.lms.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testCheckInAndCheckOut() {
        Book book = new Book("Dune", "Herbert", "Sci-Fi", false, null);

        assertFalse(book.isCheckout());
        book.checkOut();
        assertTrue(book.isCheckout());
        book.checkIn();
        assertFalse(book.isCheckout());
    }

    @Test
    void testSetDueDate() {
        Book book = new Book("Dune", "Herbert", "Sci-Fi", false, null);
        LocalDate newDate = LocalDate.of(2025, 12, 31);

        book.setDueDate(newDate);
        assertEquals(newDate, book.getDueDate());
    }

    @Test
    void testToString_WithDueDate() {
        LocalDate due = LocalDate.of(2025, 6, 15);
        Book book = new Book("B-123-A", "Animal Farm", "Orwell", "Satire", true, due);
        String output = book.toString();

        assertTrue(output.contains("Animal Farm"));
        assertTrue(output.contains("Orwell"));
        assertTrue(output.contains("Checked Out: true"));
        assertTrue(output.contains("Due Date: 2025-06-15"));
    }

    @Test
    void testToString_WithoutDueDate() {
        Book book = new Book("B-123-A", "Macbeth", "Shakespeare", "Tragedy", false, null);
        String output = book.toString();

        assertTrue(output.contains("Macbeth"));
        assertFalse(output.contains("Due Date:"));
    }
}