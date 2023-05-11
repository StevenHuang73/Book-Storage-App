package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    private BookStorages bookShelf;
    private BookStorages readingList;

    @BeforeEach
    void runBefore() {
        bookShelf = new BookShelf();
        readingList = new ReadingList();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Library lib = new Library(bookShelf, readingList);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLibrary.json");
            writer.open();
            writer.write(lib);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLibrary.json");
            lib = reader.read();
            assertEquals("Bookshelf", lib.getBookShelf().getName());
            assertEquals("Reading List", lib.getReadingList().getName());
            assertEquals(0, lib.getBookShelf().getList().size());
            assertEquals(0, lib.getReadingList().getList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Library lib = new Library(bookShelf, readingList);
            Book book1 = new Book("A", "A", "A", "A");
            book1.giveReview("P");
            Book book2 = new Book("B", "B", "B", "B");
            book2.star(true);
            Book book3 = new Book("C", "C", "C", "C");
            book3.giveStarRating(1);
            Book book4 = new Book("D", "D", "D", "D");
            book4.readStatus(true);
            bookShelf.addBook(book1);
            bookShelf.addBook(book2);
            readingList.addBook(book3);
            readingList.addBook(book4);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLibrary.json");
            writer.open();
            writer.write(lib);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLibrary.json");
            lib = reader.read();
            assertEquals("Bookshelf", lib.getBookShelf().getName());
            assertEquals("Reading List", lib.getReadingList().getName());

            List<Book> bookShelfBooks = lib.getBookShelf().getList();
            List<Book> readingListBooks = lib.getReadingList().getList();

            assertEquals(2, bookShelfBooks.size());
            assertEquals(2, readingListBooks.size());
            checkBook("A", false, false, 0, "P", "A", "A", "A",
                    bookShelfBooks.get(0));
            checkBook("B", false, true, 0, "No review yet", "B", "B",
                    "B", bookShelfBooks.get(1));
            checkBook("C", false, false, 1, "No review yet", "C", "C",
                    "C", readingListBooks.get(0));
            checkBook("D", true, false, 0, "No review yet", "D", "D",
                    "D", readingListBooks.get(1));


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}