package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads library from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads library from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Library read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses library from JSON object and returns it
    private Library parseLibrary(JSONObject jsonObject) {
        JSONObject bookshelf = jsonObject.getJSONObject("bookshelf");
        JSONObject readingList = jsonObject.getJSONObject("reading list");

        Library lib = new Library(parseBookStorages(bookshelf), parseBookStorages(readingList));
        return lib;
    }

    // EFFECTS: parses book storages from JSON object and returns it
    private BookStorages parseBookStorages(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        BookStorages bs;
        if (name.equals("Bookshelf")) {
            bs = new BookShelf();
        } else {
            bs = new ReadingList();
        }
        addBooks(bs, jsonObject);
        return bs;
    }

    // MODIFIES: BookStorages
    // EFFECTS: parses books from JSON object and adds them to book storage
    private void addBooks(BookStorages bs, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(bs, nextBook);
        }
    }

    // MODIFIES: BookStorages
    // EFFECTS: parses book from JSON object and adds it to book storage
    private void addBook(BookStorages bs, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        Boolean readStatus = jsonObject.getBoolean("read status");
        Boolean starred = jsonObject.getBoolean("starred");
        int starReview = jsonObject.getInt("star review");
        String review = jsonObject.getString("review");
        String author = jsonObject.getString("author");
        String genre = jsonObject.getString("genre");
        String series = jsonObject.getString("series");
        Book createdBook = new Book(title, author, genre, series);
        if (readStatus) {
            createdBook.readStatus(true);
        }
        if (starred) {
            createdBook.star(true);
        }
        createdBook.giveReview(review);
        createdBook.giveStarRating(starReview);
        bs.addBook(createdBook);
    }
}
