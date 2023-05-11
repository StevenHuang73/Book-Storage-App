package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    private BookStorages bookShelf;
    private BookStorages readingList;

    @BeforeEach
    void runBefore() {
        bookShelf = new BookShelf();
        readingList = new ReadingList();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Library lib = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLibrary.json");
        try {
            Library lib = reader.read();
            assertEquals("Bookshelf", lib.getBookShelf().getName());
            assertEquals("Reading List", lib.getReadingList().getName());
            assertEquals(0, lib.getBookShelf().getList().size());
            assertEquals(0, lib.getReadingList().getList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLibrary() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralLibrary.json");
        try {
            Library lib = reader.read();
            assertEquals("Bookshelf", lib.getBookShelf().getName());
            assertEquals("Reading List", lib.getReadingList().getName());

            List<Book> bookShelfBooks = lib.getBookShelf().getList();
            List<Book> readingListBooks = lib.getReadingList().getList();

            assertEquals(2, bookShelfBooks.size());
            assertEquals(2, readingListBooks.size());
            checkBook("A", false, false, 0.0, "P", "A", "A", "A",
                    bookShelfBooks.get(0));
            checkBook("B", false, true, 0.0, "No review yet", "B", "B",
                    "B", bookShelfBooks.get(1));
            checkBook("C", false, false, 1.0, "No review yet", "C", "C",
                    "C", readingListBooks.get(0));
            checkBook("D", true, false, 0.0, "No review yet", "D", "D",
                    "D", readingListBooks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}