package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Console based aspect of App, unused
public class BooksApp {

    private static final String JSON_STORE = "./data/library.json";
    private BookStorages myBookShelf;
    private BookStorages myReadingList;
    private Library myLibrary;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private final String[] validBooksAppCommands = {"QUIT", "ENTER BOOKSHELF", "ENTER READING LIST", "LOAD"};
    private final String[] validBookshelfCommands = {"ADD BOOK", "BROWSE", "MAIN MENU", "ENTER READING LIST", "SORT"};
    private final String[] validReadingListCommands = {"ADD BOOK", "BROWSE", "MAIN MENU", "ENTER BOOKSHELF", "SORT"};
    private final String[] validHandleBookCommands = {"ADD REVIEW", "ADD STAR REVIEW", "DONE READING", "STAR", "VIEW"};
    private final String[] validSortCommands = {"AUTHOR", "GENRE", "SERIES", "TITLE", "STARRED"};

    // EFFECTS: starts the BooksApp application
    public BooksApp() {
        myBookShelf = new BookShelf();
        myReadingList = new ReadingList();
        myLibrary = new Library(myBookShelf, myReadingList);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        beginApp();
    }

    // EFFECTS: begins the app
    private void beginApp() {
        handleLoad();
        mainMenu();
    }

    // EFFECTS: Displays main menu and asks for input
    private void mainMenu() {
        System.out.println("Welcome to your personal library!");
        List<String> validInputs = new ArrayList<>(Arrays.asList(validBooksAppCommands));
        handleMenuInput(validInputs);
    }

    // MODIFIES: this
    // EFFECTS: asks user if they want to load old data, if yes load
    private void handleLoad() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Would you like to load previous data? ");
        if (myScanner.nextLine().equals("YES")) {
            try {
                myLibrary = jsonReader.read();
                myBookShelf = myLibrary.getBookShelf();
                myReadingList = myLibrary.getReadingList();
                System.out.println("Loaded library from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // EFFECTS: handle main menu input
    private void handleMenuInput(List<String> validInputs) {
        Scanner myScanner = new Scanner(System.in);
        String input = "empty string";
        while (!validInputs.contains(input)) {
            System.out.println("What would you like to do? type \"HELP\" for a list of commands.");
            input = myScanner.nextLine();
            switch (input) {
                case "HELP":
                    printValidCommands(validBooksAppCommands);
                    break;
                case "ENTER BOOKSHELF":
                    inBookshelf();
                    break;
                case "ENTER READING LIST":
                    inReadingList();
                    break;
                case "QUIT":
                    saveHandle();
                    break;
                default:
                    System.out.println("That's not a valid input.");
                    break;
            }
        }
    }

    // EFFECT: asks user is they want to save, saves to file if yes
    private void saveHandle() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Would you like to save? ");
        if (myScanner.nextLine().equals("YES")) {
            try {
                jsonWriter.open();
                jsonWriter.write(myLibrary);
                jsonWriter.close();
                System.out.println("Saved library to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
        System.out.println("See you next time!");
    }

    // MODIFIES: BookShelf
    // EFFECTS: Enters the bookshelf portion of the app and allows for modifications
    @SuppressWarnings("methodlength")
    private void inBookshelf() {
        System.out.println("Welcome to your personal bookshelf!");
        List<String> validInputs = new ArrayList<>(Arrays.asList(validBookshelfCommands));
        Scanner myScanner = new Scanner(System.in);
        String input = "empty string";
        while (!validInputs.contains(input)) {
            System.out.println("What would you like to do? type \"HELP\" for a list of commands.");
            input = myScanner.nextLine();
            switch (input) {
                case "HELP":
                    printValidCommands(validBookshelfCommands);
                    break;
                case "ADD BOOK":
                    Book toBeAdded = newBook();
                    toBeAdded.readStatus(true);
                    myBookShelf.addBook(toBeAdded);
                    System.out.println("Book added!");
                    inBookshelf();
                    break;
                case "ENTER READING LIST":
                    inReadingList();
                    break;
                case "MAIN MENU":
                    mainMenu();
                    break;
                case "BROWSE":
                    if (!emptyCheck(myBookShelf)) {
                        browse(myBookShelf);
                    } else {
                        inBookshelf();
                    }
                    break;
                case "SORT":
                    sort(myBookShelf);
                    break;
                default:
                    System.out.println("That's not a valid input.");
                    break;
            }
        }
    }

    // MODIFIES: Reading List
    // EFFECTS: Enters the reading list portion of the app and allows for modifications
    @SuppressWarnings("methodlength")
    private void inReadingList() {
        System.out.println("Welcome to your personal Reading List!");
        List<String> validInputs = new ArrayList<>(Arrays.asList(validReadingListCommands));
        Scanner myScanner = new Scanner(System.in);
        String input = "empty string";
        while (!validInputs.contains(input)) {
            System.out.println("What would you like to do? type \"HELP\" for a list of commands.");
            input = myScanner.nextLine();
            switch (input) {
                case "HELP":
                    printValidCommands(validReadingListCommands);
                    break;
                case "ADD BOOK":
                    myReadingList.addBook(newBook());
                    System.out.println("Book added!");
                    inReadingList();
                    break;
                case "ENTER BOOKSHELF":
                    inBookshelf();
                    break;
                case "MAIN MENU":
                    mainMenu();
                    break;
                case "BROWSE":
                    if (!emptyCheck(myReadingList)) {
                        browse(myReadingList);
                    } else {
                        inReadingList();
                    }
                    break;
                case "SORT":
                    sort(myReadingList);
                    break;
                default:
                    System.out.println("That's not a valid input.");
                    break;
            }
        }
    }

    // EFFECTS: Generates a new book to be added
    private Book newBook() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("What is the book title?");
        String title = myScanner.nextLine();
        System.out.println("Who is the author?");
        String author = myScanner.nextLine();
        System.out.println("What genre is the book?");
        String genre = myScanner.nextLine();
        System.out.println("What series is the book part of?");
        String series = myScanner.nextLine();
        return new Book(title, author, genre, series);
    }

    // EFFECTS: Displays all the books in a given bookshelf/reading list and allows user to select one for handling
    private void browse(BookStorages theList) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Here are the books in your " + theList.getName());
        System.out.println("Use numbers to select a book: ");
        for (int x = 0; x < theList.getList().size(); x++) {
            System.out.println(x + 1 + ". " + theList.getList().get(x).getTitle());
        }
        int bookNum = myScanner.nextInt();
        if ((bookNum > 0) & (bookNum <= theList.getList().size())) {
            if (theList == myReadingList) {
                handleBook(theList.getList().get(bookNum - 1));
                inReadingList();
            } else {
                handleBook(theList.getList().get(bookNum - 1));
                inBookshelf();
            }
        } else {
            System.out.println("That's not a valid input.");
            browse(theList);
        }
    }

    // checks if the given storage is empty
    private boolean emptyCheck(BookStorages theList) {
        if (theList.getList().size() == 0) {
            System.out.println("Your " + theList.getName() + " is empty.");
            return true;
        }
        return false;
    }

    // MODIFIES: Book
    // EFFECTS: Allows for handling of the book, can add review, add star review, mark as done reading, star the book
    //          or just view.
    @SuppressWarnings("methodlength")
    private void handleBook(Book book) {
        Scanner myScanner = new Scanner(System.in);
        List<String> validInputs = new ArrayList<>(Arrays.asList(validHandleBookCommands));
        String input = "empty string";
        System.out.println("You've selected: " + book.getTitle());
        while (!validInputs.contains(input)) {
            System.out.println("What would you like to do next? type \"HELP\" for a list of commands.");
            input = myScanner.nextLine();
            switch (input) {
                case "HELP":
                    printValidCommands(validHandleBookCommands);
                    break;
                case "ADD REVIEW":
                    System.out.println("Please give a review: ");
                    book.giveReview(myScanner.nextLine());
                    System.out.println("Review added!");
                    break;
                case "ADD STAR REVIEW":
                    System.out.println("Please give star review: ");
                    book.giveStarRating(myScanner.nextInt());
                    System.out.println("Review added!");
                    break;
                case "DONE READING":
                    book.readStatus(true);
                    System.out.println("Book complete!");
                    if (myReadingList.contains(book)) {
                        myReadingList.removeBook(book);
                        if (!myBookShelf.contains(book)) {
                            myBookShelf.addBook(book);
                        }
                    }
                    break;
                case "STAR":
                    book.star(true);
                    System.out.println("Starred!");
                    break;
                case "VIEW":
                    System.out.println("Star rating: " + book.getStar() + " Review: " + book.getReview());
                    break;
                default:
                    System.out.println("That's not a valid input.");
                    break;
            }
        }
    }

    // MODIFIES: One of the BookStorages
    // EFFECTS: Sorts either the bookshelf or reading list by chosen criteria
    private void sort(BookStorages readingOrBook) {
        List<String> validInputs = new ArrayList<>(Arrays.asList(validSortCommands));
        Scanner myScanner = new Scanner(System.in);
        String input = "empty string";
        while (!validInputs.contains(input)) {
            System.out.println("What would you like to sort by? type \"HELP\" for a list of commands.");
            input = myScanner.nextLine();
            switch (input) {
                case "AUTHOR":
                case "TITLE":
                case "GENRE":
                case "SERIES":
                case "STARRED":
                    readingOrBook.sortBy(input);
                    break;
                case "HELP":
                    printValidCommands(validSortCommands);
                    break;
                default:
                    System.out.println("That's not a valid input.");
                    break;
            }
        }
        browse(readingOrBook);
    }

    // EFFECTS: prints out the list of valid commands for a menu
    private void printValidCommands(String[] commands) {
        System.out.println("The list of commands are: ");
        for (String command : commands) {
            System.out.println("\"" + command + "\"");
        }
    }
}