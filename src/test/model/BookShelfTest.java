package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BookShelfTest {
    BookStorages myBookshelf;
    Book myBook;
    Book myBook2;

    @BeforeEach
    void runBefore() {
        myBook = new Book("Steven's Book", "Steven Huang", "Action", "Steven's Wacky Adventures");
        myBook2 = new Book("Max's Book", "Max Wu", "Crime", "Max's Wacky Adventures");
        myBookshelf = new BookShelf();
    }

    @Test
    void addBookTest() {
        myBookshelf.addBook(myBook);
        assertEquals(myBook, myBookshelf.getList().get(0));

        myBookshelf.addBook(myBook2);
        assertEquals(myBook2, myBookshelf.getList().get(1));
    }

    @Test
    void removeBookTest() {
        myBookshelf.addBook(myBook);
        myBookshelf.addBook(myBook2);

        myBookshelf.removeBook(myBook);
        assertEquals(myBook2, myBookshelf.getList().get(0));

        myBookshelf.removeBook(myBook2);
        assertEquals(0, myBookshelf.getList().size());
    }

    @Test
    void containsTest() {
        myBookshelf.addBook(myBook);

        assertTrue(myBookshelf.contains(myBook));
        assertFalse(myBookshelf.contains(myBook2));
    }

    @Test
    void getTests() {
        myBookshelf.addBook(myBook);
        myBookshelf.addBook(myBook2);

        ArrayList<Book> test = new ArrayList<>();
        test.add(myBook);
        test.add(myBook2);

        assertEquals(test, myBookshelf.getList());
        assertEquals("Bookshelf", myBookshelf.getName());
    }

    @Test
    void sortByTest() {
        myBookshelf.addBook(myBook);
        myBookshelf.addBook(myBook2);
        myBookshelf.sortBy("Author");

        ArrayList<Book> test = new ArrayList<>();
        test.add(myBook2);
        test.add(myBook);

        assertEquals(test, myBookshelf.getList());
        myBookshelf.sortBy("Title");
        assertEquals(test, myBookshelf.getList());
        myBookshelf.sortBy("Series");
        assertEquals(test, myBookshelf.getList());

        ArrayList<Book> test2 = new ArrayList<>();
        test2.add(myBook);
        test2.add(myBook2);
        myBookshelf.sortBy("Genre");

        assertEquals(test2, myBookshelf.getList());
    }

    @Test
    void starredTest() {
        myBook2.star(true);
        myBookshelf.addBook(myBook);
        myBookshelf.addBook(myBook2);
        ArrayList<Book> test = new ArrayList<>();
        test.add(myBook2);
        test.add(myBook);

        myBookshelf.sortBy("starred");
        assertEquals(test, myBookshelf.getList());
    }
}
