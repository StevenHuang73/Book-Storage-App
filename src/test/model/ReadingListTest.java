package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReadingListTest {
    BookStorages myReadingList;
    Book myBook;
    Book myBook2;

    @BeforeEach
    void runBefore() {
        myBook = new Book("Steven's Book", "Steven Huang", "Action", "Steven's Wacky Adventures");
        myBook2 = new Book("Max's Book", "Max Wu", "Crime", "Max's Wacky Adventures");
        myReadingList = new ReadingList();
    }
    @Test
    void addBookTest() {
        myReadingList.addBook(myBook);
        assertEquals(myBook, myReadingList.getList().get(0));

        myReadingList.addBook(myBook2);
        assertEquals(myBook2, myReadingList.getList().get(1));
    }

    @Test
    void removeBookTest() {
        myReadingList.addBook(myBook);
        myReadingList.addBook(myBook2);

        myReadingList.removeBook(myBook);
        assertEquals(myBook2, myReadingList.getList().get(0));

        myReadingList.removeBook(myBook2);
        assertEquals(0, myReadingList.getList().size());
    }

    @Test
    void containsTest() {
        myReadingList.addBook(myBook);

        assertTrue(myReadingList.contains(myBook));
        assertFalse(myReadingList.contains(myBook2));
    }

    @Test
    void getTests() {
        myReadingList.addBook(myBook);
        myReadingList.addBook(myBook2);

        ArrayList<Book> test = new ArrayList<>();
        test.add(myBook);
        test.add(myBook2);

        assertEquals(test, myReadingList.getList());
        assertEquals("Reading List", myReadingList.getName());
    }

    @Test
    void sortByTest() {
        myReadingList.addBook(myBook);
        myReadingList.addBook(myBook2);
        myReadingList.sortBy("Author");

        ArrayList<Book> test = new ArrayList<>();
        test.add(myBook2);
        test.add(myBook);

        assertEquals(test, myReadingList.getList());
        myReadingList.sortBy("Title");
        assertEquals(test, myReadingList.getList());
        myReadingList.sortBy("Series");
        assertEquals(test, myReadingList.getList());

        ArrayList<Book> test2 = new ArrayList<>();
        test2.add(myBook);
        test2.add(myBook2);
        myReadingList.sortBy("Genre");

        assertEquals(test2, myReadingList.getList());
    }

    @Test
    void starredTest() {
        myBook2.star(true);
        myReadingList.addBook(myBook);
        myReadingList.addBook(myBook2);
        ArrayList<Book> test = new ArrayList<>();
        test.add(myBook2);
        test.add(myBook);

        myReadingList.sortBy("starred");
        assertEquals(test, myReadingList.getList());
    }
}
