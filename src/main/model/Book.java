package model;

import org.json.JSONObject;
import persistence.Writable;

// Class to represent a book
public class Book implements Writable {

    private String title;
    private boolean readStatus;
    private boolean starred;
    private int starReview;
    private String review;
    private String author;
    private String genre;
    private String series;


    // EFFECTS: Creates a book that is unread
    public Book(String title, String author, String genre, String series) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.series = series;
        this.review = "No review yet";
        this.starred = false;
        readStatus = false;
    }

    // EFFECTS: Creates a new book that is unread, but without other information as well
    public Book() {
        this.title = "Unknown";
        this.author = "Unknown";
        this.genre = "Unknown";
        this.series = "Unknown";
        this.review = "No review yet";
        this.starred = false;
        this.readStatus = false;
        this.starReview = 0;
    }

    // MODIFIES: this
    // EFFECTS: Sets the book title
    public void setTitle(String title) {
        this.title = title;
    }

    // MODIFIES: this
    // EFFECTS: Sets the book author
    public void setAuthor(String author) {
        this.author = author;
    }

    // MODIFIES: this
    // EFFECTS: Sets the book genre
    public void setGenre(String genre) {
        this.genre = genre;
    }

    // MODIFIES: this
    // EFFECTS: Sets the book series
    public void setSeries(String series) {
        this.series = series;
    }

    // REQUIRES: rating must be between 1 and 5
    // MODIFIES: this
    // EFFECTS: gives the book a rating between 1 and 5 stars
    public void giveStarRating(int rating) {
        starReview = rating;
    }

    // MODIFIES: this
    // EFFECTS: gives the book a review
    public void giveReview(String review) {
        this.review = review;
    }

    // MODIFIES: this
    // EFFECTS: gives book corresponding starred status
    public void star(boolean star) {
        this.starred = star;
    }

    // MODIFIES: this
    // EFFECTS: changes book status to read
    public void readStatus(boolean status) {
        readStatus = status;
    }

    // EFFECTS: gets book title
    public String getTitle() {
        return title;
    }

    // EFFECTS: gets book author
    public String getAuthor() {
        return author;
    }

    // EFFECTS: gets book genre
    public String getGenre() {
        return genre;
    }

    // EFFECTS: gets book series
    public String getSeries() {
        return series;
    }

    // EFFECTS: gets book star review
    public int getStar() {
        return starReview;
    }

    // EFFECTS: returns current starred status:
    public boolean starredStatus() {
        return starred;
    }

    // EFFECTS: gets book review
    public String getReview() {
        return review;
    }

    // EFFECTS: returns reading status
    public boolean getReadStatus() {
        return readStatus;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("read status", readStatus);
        json.put("starred", starred);
        json.put("star review", starReview);
        json.put("review", review);
        json.put("author", author);
        json.put("genre", genre);
        json.put("series", series);
        return json;
    }

    // EFFECTS: overrides what is displayed
    @Override
    public String toString() {
        return title + " by " + author;
    }

}