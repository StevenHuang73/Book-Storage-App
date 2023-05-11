package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

// Menu for the bookshelf
public class BookShelfGUI extends JPanel implements ActionListener {

    private final JButton toMainMenu = new JButton("Main Menu");
    private JPanel sideBar;
    private final JButton toReadingList = new JButton("Reading List");
    private final JButton plus = new JButton();
    protected static JList<Book> list;
    private JComboBox sortMenu;

    // EFFECTS: creates the GUI
    public BookShelfGUI() {
        setSideBarMainMenu();
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1000, 760));
        this.setBackground(Color.decode("#014431"));
        this.add(sideBar);
        showList();
        titleAndPicture();
        sortMenu();
    }

    // MODIFIES: this
    // EFFECTS: sets the side bar on the left side of application
    private void setSideBarMainMenu() {
        sideBar = new JPanel(null);
        sideBar.setSize(250, 760);
        sideBar.setBackground(Color.BLACK);

        toReadingList.setBounds(65, 280, 120, 30);
        sideBar.add(toReadingList);

        toMainMenu.setBounds(75, 400, 100, 30);
        sideBar.add(toMainMenu);

        toReadingList.addActionListener(this);
        toMainMenu.addActionListener(this);
        plus.addActionListener(this);

        JLabel sideBarIcon = new JLabel();
        ImageIcon icon = new ImageIcon("./data/Images/Icon.png");
        sideBarIcon.setIcon(icon);
        sideBarIcon.setBounds(77, 610, 96, 96);
        sideBar.add(sideBarIcon);

        ImageIcon plusIcon = new ImageIcon("./data/Images/Plus.jpg");
        plus.setBounds(78, 70, 93, 93);
        plus.setIcon(plusIcon);
        sideBar.add(plus);
    }

    // MODIFIES: this, BooksAppGUI, Bookshelf
    // EFFECTS: Does something when the buttons on sidebar and sort menu are pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) this.getRootPane().getParent();

        if (e.getSource() == toReadingList) {
            BooksAppGUI.cardLayout.show(frame.getContentPane(), "reading list");
        } else if (e.getSource() == toMainMenu) {
            BooksAppGUI.cardLayout.show(frame.getContentPane(), "main menu");
        } else if (e.getSource() == plus) {
            createNewBook();
        } else if (e.getSource() == sortMenu) {
            BooksAppGUI.bookShelf.sortBy(sortMenu.getSelectedItem().toString());
            list.setListData(BooksAppGUI.bookShelf.getList().toArray(new Book[0]));
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the list of books and handles
    private void showList() {
        list = new JList<>(BooksAppGUI.bookShelf.getList().toArray(new Book[0]));
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(310, 160, 630, 550);
        scrollPane.setBorder(null);
        list.setBackground(Color.decode("#014431"));
        list.setForeground(Color.WHITE);
        list.setFixedCellHeight(75);
        list.setFont(new Font("Lucida Handwriting", Font.PLAIN, 30));

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    editBook();
                }
            }
        });

        this.add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: Edits selected book
    private void editBook() {
        Book selectedBook = list.getSelectedValue();
        if (selectedBook != null) {
            BookEditorGUI editor = new BookEditorGUI(selectedBook);
            editor.setVisible(true);
            checkStatus(editor, selectedBook);
            if (editor.getRemoveStatus()) {
                BooksAppGUI.bookShelf.removeBook(selectedBook);
            }
            list.setListData(BooksAppGUI.bookShelf.getList().toArray(new Book[0]));
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates and places the title and picture
    private void titleAndPicture() {
        JLabel bookshelfLabel = new JLabel("BOOKSHELF");
        ImageIcon image = new ImageIcon("./data/Images/BookshelfImage.png");
        bookshelfLabel.setHorizontalTextPosition(JLabel.LEFT);
        bookshelfLabel.setIcon(image);
        bookshelfLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 40));
        bookshelfLabel.setForeground(Color.WHITE);
        bookshelfLabel.setBounds(310, 20, 630, 150);
        bookshelfLabel.setIconTextGap(200);
        this.add(bookshelfLabel);
    }

    // MODIFIES: this
    // EFFECTS: Creates the sort menu
    private void sortMenu() {
        String[] sortItems = {"Sort by", "Title", "Author", "Genre", "Series", "Starred"};
        sortMenu = new JComboBox(sortItems);
        sortMenu.setBounds(310, 135, 80, 25);
        sortMenu.addActionListener(this);
        this.add(sortMenu);
    }

    // MODIFIES: Book
    // EFFECTS: Creates a new book
    private void createNewBook() {
        Book newBook = new Book();
        BookEditorGUI bookEditor = new BookEditorGUI(newBook);
        bookEditor.setVisible(true);

        checkStatus(bookEditor, newBook);
    }

    // MODIFIES: BooksAppGUI, this, both Bookstorages
    // EFFECTS: Moves the books back and forth between reading list and bookshelf depending on read status
    private void checkStatus(BookEditorGUI editor, Book book) {
        if (!editor.readStatus()) {
            if (!BooksAppGUI.readingList.contains(book)) {
                BooksAppGUI.readingList.addBook(book);
            }
            if (BooksAppGUI.bookShelf.getList().contains(book)) {
                BooksAppGUI.bookShelf.removeBook(book);
            }
            ReadingListGUI.list.setListData(BooksAppGUI.readingList.getList().toArray(new Book[0]));
        } else {
            if (!BooksAppGUI.bookShelf.contains(book)) {
                BooksAppGUI.bookShelf.addBook(book);
            }
            if (BooksAppGUI.readingList.getList().contains(book)) {
                BooksAppGUI.readingList.removeBook(book);
            }
            list.setListData(BooksAppGUI.bookShelf.getList().toArray(new Book[0]));
        }
    }


}
