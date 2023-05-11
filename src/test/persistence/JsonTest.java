package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkBook(String title, boolean readStatus, boolean starred, double starReview,
                             String review, String author, String genre, String series, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(readStatus, book.getReadStatus());
        assertEquals(starred, book.starredStatus());
        assertEquals(starReview, book.getStar());
        assertEquals(review, book.getReview());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());
        assertEquals(series, book.getSeries());
    }

}
