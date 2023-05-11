package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.*;

// The GUI for the reading list
public class ReadingListGUI extends JPanel implements ActionListener {

    private final JButton toMainMenu = new JButton("Main Menu");
    private JPanel sideBar;
    private final JButton toBookShelf = new JButton("Bookshelf");
    private final JButton plus = new JButton();
    protected static JList<Book> list;
    private JComboBox sortMenu;

    // EFFECTS: Creates the GUI for reading list
    public ReadingListGUI() {
        setSideBarMainMenu();
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1000, 760));
        this.setBackground(Color.decode("#800000"));
        this.add(sideBar);

        showList();
        titleAndPicture();
        sortMenu();
    }

    // MODIFIES: this
    // EFFECTS: Sets the side menu
    private void setSideBarMainMenu() {
        sideBar = new JPanel(null);
        sideBar.setSize(250, 760);
        sideBar.setBackground(Color.BLACK);

        toBookShelf.setBounds(75, 280, 100, 30);
        sideBar.add(toBookShelf);

        toMainMenu.setBounds(75, 400, 100, 30);
        sideBar.add(toMainMenu);

        toBookShelf.addActionListener(this);
        toMainMenu.addActionListener(this);

        JLabel sideBarIcon = new JLabel();
        ImageIcon icon = new ImageIcon("./data/Images/Icon.png");
        sideBarIcon.setIcon(icon);
        sideBarIcon.setBounds(77, 610, 96, 96);
        sideBar.add(sideBarIcon);

        ImageIcon plusIcon = new ImageIcon("./data/Images/Plus.jpg");
        plus.setBounds(78, 70, 93, 93);
        plus.setIcon(plusIcon);
        sideBar.add(plus);
        plus.addActionListener(this);
    }

    // MODIFIES: this, BooksAppGUI, ReadingList
    // EFFECTS: Does something when the buttons on sidebar or sort menu are pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) this.getRootPane().getParent();

        if (e.getSource() == toBookShelf) {
            BooksAppGUI.cardLayout.show(frame.getContentPane(),"bookshelf");
        } else if (e.getSource() == toMainMenu) {
            BooksAppGUI.cardLayout.show(frame.getContentPane(),"main menu");
        } else if (e.getSource() == plus) {
            createNewBook();
        } else if (e.getSource() == sortMenu) {
            BooksAppGUI.readingList.sortBy(sortMenu.getSelectedItem().toString());
            list.setListData(BooksAppGUI.readingList.getList().toArray(new Book[0]));
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the list of books
    private void showList() {
        list = new JList<>(BooksAppGUI.readingList.getList().toArray(new Book[0]));
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(310, 160, 630, 550);
        scrollPane.setBorder(null);
        list.setBackground(Color.decode("#800000"));
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
    // EFFECTS: edits the selected book
    private void editBook() {
        Book selectedBook = list.getSelectedValue();
        if (selectedBook != null) {
            BookEditorGUI editor = new BookEditorGUI(selectedBook);
            editor.setVisible(true);
            checkStatus(editor, selectedBook);
            if (editor.getRemoveStatus()) {
                BooksAppGUI.readingList.removeBook(selectedBook);
            }
            list.setListData(BooksAppGUI.readingList.getList().toArray(new Book[0]));
        }
    }

    // MODIFIES: this
    // EFFECTS: Shows the title and picture displayed at the top of GUI
    private void titleAndPicture() {
        JLabel readingListLabel = new JLabel("READING LIST");
        ImageIcon image = new ImageIcon("./data/Images/ReadingListImage.png");
        readingListLabel.setHorizontalTextPosition(JLabel.LEFT);
        readingListLabel.setIcon(image);
        readingListLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 40));
        readingListLabel.setForeground(Color.WHITE);
        readingListLabel.setBounds(310, 20, 630, 150);
        readingListLabel.setIconTextGap(120);
        this.add(readingListLabel);

    }

    // MODIFIES: this, Book
    // EFFECTS: Creates a new book
    private void createNewBook() {
        Book newBook = new Book();
        BookEditorGUI bookEditor = new BookEditorGUI(newBook);
        bookEditor.setVisible(true);

        checkStatus(bookEditor, newBook);

    }

    // MODIFIES: this
    // EFFECTS: creates the sort menu
    private void sortMenu() {
        String[] sortItems = {"Sort by", "Title", "Author", "Genre", "Series", "Starred"};
        sortMenu = new JComboBox(sortItems);
        sortMenu.setBounds(310, 135, 80, 25);
        sortMenu.addActionListener(this);
        this.add(sortMenu);
    }

    // MODIFIES: this, BooksAppGUI, both the BookStorages
    // EFFECTS: Moves the books back and forth between reading list and bookshelf depending on read status
    private void checkStatus(BookEditorGUI editor, Book book) {
        if (!editor.readStatus()) {
            if (!BooksAppGUI.readingList.contains(book)) {
                BooksAppGUI.readingList.addBook(book);
            }
            if (BooksAppGUI.bookShelf.getList().contains(book)) {
                BooksAppGUI.bookShelf.removeBook(book);
            }
            list.setListData(BooksAppGUI.readingList.getList().toArray(new Book[0]));
        } else {
            if (!BooksAppGUI.bookShelf.contains(book)) {
                BooksAppGUI.bookShelf.addBook(book);
            }
            if (BooksAppGUI.readingList.getList().contains(book)) {
                BooksAppGUI.readingList.removeBook(book);
            }
            BookShelfGUI.list.setListData(BooksAppGUI.bookShelf.getList().toArray(new Book[0]));
        }
    }
}
