package model;

import org.json.JSONObject;
import persistence.Writable;

// Library class to contain the bookshelf and reading list
public class Library implements Writable {
    private BookStorages bookShelf;
    private BookStorages readingList;

    // REQUIRES: the first bookstorage is a bookshelf, second bookstorage is a reading list
    // EFFECTS: creates a Library with a bookshelf and reading list
    public Library(BookStorages bookShelf, BookStorages readingList) {
        this.bookShelf = bookShelf;
        this.readingList = readingList;
    }

    // EFFECTS: returns bookshelf
    public BookStorages getBookShelf() {
        return bookShelf;
    }

    // EFFECTS: returns reading list
    public BookStorages getReadingList() {
        return readingList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("bookshelf", bookShelf.toJson());
        json.put("reading list", readingList.toJson());
        return json;
    }
}
