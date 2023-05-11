package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

// Class for the main portion of the GUI
public class BooksAppGUI {

    protected static JFrame frame;
    private BookShelfGUI bookShelfPanel;
    private ReadingListGUI readingListPanel;
    private MainMenuGUI mainMenuGUI = new MainMenuGUI();
    protected static CardLayout cardLayout = new CardLayout();
    protected static BookStorages bookShelf;
    protected static BookStorages readingList;
    private Library library;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/library.json";


    // EFFECTS: Creates a new GUI
    public BooksAppGUI() {
        bookShelf = new BookShelf();
        readingList = new ReadingList();
        library = new Library(bookShelf, readingList);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        bookShelfPanel = new BookShelfGUI();
        readingListPanel = new ReadingListGUI();
        setApp();
    }

    // MODIFIES: this
    // EFFECTS: Sets the main frame for the GUI to be set on
    private void setFrame() {
        frame = new JFrame();

        ImageIcon icon = new ImageIcon("./data/Images/Icon.png");
        frame.setIconImage(icon.getImage());

        frame.setLayout(cardLayout);
        frame.add(mainMenuGUI, "main menu");
        frame.add(bookShelfPanel, "bookshelf");
        frame.add(readingListPanel, "reading list");
        cardLayout.show(frame.getContentPane(), "main menu");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(frame, "Would you like to save data?", "Load Data",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    handleSave();
                }
                printLog();
                frame.dispose();
            }
        });
        frame.setTitle("Personal Library");
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // EFFECTS: Prints out the EventLog
    private void printLog() {
        Iterator<Event> it = EventLog.getInstance().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up the app as well as the first popup
    private void setApp() {
        setFrame();
        loadPop();
    }

    // MODIFIES: this
    // EFFECTS: Popup asking user if they want to load data
    private void loadPop() {
        int option = JOptionPane.showConfirmDialog(frame,
                "Would you like to load data?", "Load Data", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            handleLoad();
        }

    }

    // MODIFIES: library.json
    // EFFECTS: loads information for json
    private void handleLoad() {
        try {
            library = jsonReader.read();
            bookShelf = library.getBookShelf();
            readingList = library.getReadingList();
            System.out.println("Loaded library from " + JSON_STORE);
            BookShelfGUI.list.setListData(bookShelf.getList().toArray(new Book[0]));
            readingListPanel.list.setListData(readingList.getList().toArray(new Book[0]));
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: library.json
    // EFFECTS: saves current state to json
    private void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            System.out.println("Saved library to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

}
