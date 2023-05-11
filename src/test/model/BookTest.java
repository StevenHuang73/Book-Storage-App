package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    Book myBook;
    Book myBook2;

    @BeforeEach
    void runBefore() {
        myBook = new Book("Steven's Book", "Steven Huang", "Action", "Steven's Wacky Adventures");
        myBook2 = new Book("Max's Book", "Max Wu", "Action", "Max's Wacky Adventures");
    }

    @Test
    void giveStarRatingTest() {
        myBook.giveStarRating(2);
        assertEquals(2, myBook.getStar());

        myBook2.giveStarRating(5);
        assertEquals(5, myBook2.getStar());
    }

    @Test
    void giveReviewTest() {
        String review = "Excellent Book";

        myBook.giveReview(review);
        assertEquals(review, myBook.getReview());

        myBook2.giveReview("");
        assertEquals("", myBook2.getReview());
    }

    @Test
    void starTest() {
        myBook2.star(true);
        assertFalse(myBook.starredStatus());
        assertTrue(myBook2.starredStatus());
        myBook2.star(false);
        assertFalse(myBook2.starredStatus());
    }

    @Test
    void doneReadingTest() {
        myBook2.readStatus(true);
        assertFalse(myBook.getReadStatus());
        assertTrue(myBook2.getReadStatus());
    }

    @Test
    void getTesting() {
        assertEquals("Steven's Book", myBook.getTitle());
        assertEquals("Max Wu", myBook2.getAuthor());
        assertEquals("Max's Wacky Adventures", myBook2.getSeries());
        assertEquals("Action", myBook.getGenre());
    }

    @Test
    void newTests() {
        Book newBook = new Book();
        newBook.setTitle("Title");
        assertEquals("Title", newBook.getTitle());
        newBook.setAuthor("Author");
        assertEquals("Author", newBook.getAuthor());
        newBook.setSeries("Series");
        assertEquals("Series", newBook.getSeries());
        newBook.setGenre("Genre");
        assertEquals("Genre", newBook.getGenre());
        assertEquals("Title by Author", newBook.toString());

    }
}