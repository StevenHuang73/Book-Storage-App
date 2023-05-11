package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Bookstorages class superclass for reading list and bookshelf
public abstract class BookStorages implements Writable {
    private ArrayList<Book> bookList;
    private String name;

    public BookStorages(String name) {
        this.bookList = new ArrayList<>();
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds a book to the book storage
    public void addBook(Book book) {
        bookList.add(book);
        EventLog.getInstance().logEvent(new Event("Added " + book.getTitle() + " to " + this.name));
    }

    // MODIFIES: this
    // EFFECTS: removes a book from the book storage
    public void removeBook(Book book) {
        bookList.remove(book);
        EventLog.getInstance().logEvent(new Event("Removed " + book.getTitle() + " from " + this.name));
    }

    // EFFECTS: checks to see if book storage contains book
    public boolean contains(Book book) {
        return bookList.contains(book);
    }

    // EFFECTS: returns the entire book storage
    public ArrayList<Book> getList() {
        return bookList;
    }

    // EFFECTS: returns the name of the book storage
    public String getName() {
        return name;
    }

    // REQUIRES: string has to be a valid category
    // MODIFIES: this
    // EFFECTS: sorts the book storage by a one of 5 set categories
    public void sortBy(String fieldName) {
        switch (fieldName.toLowerCase()) {
            case "title":
                Collections.sort(bookList, Comparator.comparing(Book::getTitle));
                EventLog.getInstance().logEvent(new Event("Sorted " + this.name +  " by title"));
                break;
            case "author":
                Collections.sort(bookList, Comparator.comparing(Book::getAuthor));
                EventLog.getInstance().logEvent(new Event("Sorted " + this.name +  " by author"));
                break;
            case "genre":
                Collections.sort(bookList, Comparator.comparing(Book::getGenre));
                EventLog.getInstance().logEvent(new Event("Sorted " + this.name +  " by genre"));
                break;
            case "series":
                Collections.sort(bookList, Comparator.comparing(Book::getSeries));
                EventLog.getInstance().logEvent(new Event("Sorted " + this.name +  " by series"));
                break;
            default:
                sortStarred();
                EventLog.getInstance().logEvent(new Event("Sorted " + this.name +  " by starred"));
        }
    }

    // EFFECTS: sorts the books based on whether they've been starred
    private void sortStarred() {
        ArrayList<Book> starredList = new ArrayList<>();
        ArrayList<Book> notStarredList = new ArrayList<>();
        for (Book i : bookList) {
            if (i.starredStatus()) {
                starredList.add(i);
            } else {
                notStarredList.add(i);
            }
        }
        starredList.addAll(notStarredList);
        bookList = starredList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("books", booksToJson());
        return json;
    }

    // MODIFIES: library.json
    // EFFECTS: returns books in this book storage as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book i : bookList) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }


}
