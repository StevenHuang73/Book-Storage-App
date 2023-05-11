package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Window that pops up to allow for editing of the book
public class BookEditorGUI extends JDialog implements ActionListener {

    private Book book;
    private JPanel bookEditor;
    private JButton star;
    private ImageIcon emptystar30 = new ImageIcon("./data/Images/empty-star-30.png");
    private ImageIcon filledstar30 = new ImageIcon("./data/Images/filled-star-30.png");
    private JPanel starPanel;
    private JButton[] starButtons = new JButton[5];
    private JButton save;
    private JButton remove;
    private JRadioButton done;
    private JRadioButton notDone;

    private int rating;
    private boolean starred;
    private JTextField titleText;
    private JTextField authorText;
    private JTextField genreText;
    private JTextField seriesText;
    private JTextArea reviewText;
    private ButtonGroup group;

    private boolean removeStatus = false;

    // EFFECTS: creates a new Book editor to edit a book
    public BookEditorGUI(Book book) {
        super(BooksAppGUI.frame, "Book Editor", true);
        this.book = book;
        starred = book.starredStatus();
        makeBookEditor();
        this.setSize(800, 700);
        this.setLocationRelativeTo(BooksAppGUI.frame);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                removeStatus = false;
                dispose();
            }
        });

    }

    // MODIFIES: this
    // EFFECTS: helper function for making the book editor
    private void makeBookEditor() {
        bookEditor = new JPanel(null);
        bookEditor.setBackground(new Color(145, 129, 81));
        titleAndPicture();
        bookDetails();
        bookDetailsInput();
        reviewBox();
        star();
        starReview();
        setReadStatus();
        bottomButtons();

        this.add(bookEditor);
    }

    // MODIFIES: this
    // EFFECTS: shows the title and the picture displayed at the very top
    private void titleAndPicture() {
        JLabel bookEditorLabel = new JLabel("Edit Book");
        bookEditorLabel.setHorizontalTextPosition(JLabel.LEFT);
        bookEditorLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 30));
        bookEditorLabel.setForeground(Color.WHITE);
        bookEditorLabel.setBounds(50, 20, 400, 100);
        bookEditor.add(bookEditorLabel);

        JLabel picture = new JLabel();
        ImageIcon image = new ImageIcon("./data/Images/PopupImage.png");
        picture.setIcon(image);
        picture.setBounds(525, 20, 186, 150);
        bookEditor.add(picture);

    }

    // MODIFIES: this
    // EFFECTS: Creates the four labels for fields that users can use to input book information
    private void bookDetails() {
        JLabel title = new JLabel("Title");
        title.setBounds(50, 145, 100, 50);
        title.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
        bookEditor.add(title);

        JLabel author = new JLabel("Author");
        author.setBounds(50, 205, 100, 50);
        author.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
        bookEditor.add(author);

        JLabel genre = new JLabel("Genre");
        genre.setBounds(50, 265, 100, 50);
        genre.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
        bookEditor.add(genre);

        JLabel series = new JLabel("Series");
        series.setBounds(50, 325, 100, 50);
        series.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
        bookEditor.add(series);
    }

    // MODIFIES: this
    // EFFECTS: Creates the text boxes themselves
    private void bookDetailsInput() {
        titleText = new JTextField();
        titleText.setBounds(170, 152, 300, 35);
        titleText.setFont(new Font("Arial", Font.PLAIN, 18));
        titleText.setText(book.getTitle());
        bookEditor.add(titleText);

        authorText = new JTextField();
        authorText.setBounds(170, 212, 300, 35);
        authorText.setFont(new Font("Arial", Font.PLAIN, 18));
        authorText.setText(book.getAuthor());
        bookEditor.add(authorText);

        genreText = new JTextField();
        genreText.setBounds(170, 272, 300, 35);
        genreText.setFont(new Font("Arial", Font.PLAIN, 18));
        genreText.setText(book.getGenre());
        bookEditor.add(genreText);

        seriesText = new JTextField();
        seriesText.setBounds(170, 332, 300, 35);
        seriesText.setFont(new Font("Arial", Font.PLAIN, 18));
        seriesText.setText(book.getSeries());
        bookEditor.add(seriesText);
    }

    // MODIFIES: this
    // EFFECTS: creates the text box and label for a review
    private void reviewBox() {
        JLabel review = new JLabel("Review");
        review.setBounds(50, 390, 100, 50);
        review.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
        bookEditor.add(review);

        reviewText = new JTextArea();
        reviewText.setFont(new Font("Arial", Font.PLAIN, 18));
        reviewText.setBounds(50, 440, 420, 200);
        reviewText.setLineWrap(true);
        reviewText.setWrapStyleWord(true);
        reviewText.setText(book.getReview());
        bookEditor.add(reviewText);
    }

    // MODIFIES: this
    // EFFECTS: Creates the star that can be clicked to star book
    private void star() {
        star = new JButton();
        star.setBounds(700, 447, 30, 30);
        if (starred) {
            star.setIcon(filledstar30);
        } else {
            star.setIcon(emptystar30);
        }
        star.setBackground(new Color(145, 129, 81));
        star.addActionListener(this);
        bookEditor.add(star);

        JLabel favourite = new JLabel("Favourite");
        favourite.setBounds(560, 440, 150, 50);
        favourite.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
        bookEditor.add(favourite);

    }

    // MODIFIES: this
    // EFFECTS: Does something when the star, save, or remove is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == star) {
            starred = !starred;
            if (starred) {
                star.setIcon(filledstar30);
            } else {
                star.setIcon(emptystar30);
            }
        } else if (e.getSource() == remove) {
            removeStatus = true;
            this.dispose();
        } else if (e.getSource() == save) {
            save();
            this.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates the 5 star rating system
    private void starReview() {
        rating = book.getStar();
        setStarTag();
        starPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        starPanel.setBounds(550, 245, 150, 40);

        for (int i = 0; i < starButtons.length; i++) {
            starButtons[i] = new JButton(emptystar30);
            starButtons[i].setBorderPainted(false);
            starButtons[i].setContentAreaFilled(false);
            int index = i;
            starButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setRating(index + 1);
                }
            });
            starPanel.add(starButtons[i]);
        }
        updateRatingStars();
        starPanel.setBackground(new Color(145, 129, 81));
        bookEditor.add(starPanel);
    }

    // MODIFIES: this
    // EFFECTS: Helper function to set the new rating, as well as updating the images
    private void setRating(int newRating) {
        rating = newRating;
        updateRatingStars();
    }

    // MODIFIES: this
    // EFFECTS: Updates the pictures displayed on the rating system
    private void updateRatingStars() {
        for (int i = 0; i < 5; i++) {
            if (i >= rating) {
                starButtons[i].setIcon(emptystar30);
            } else {
                starButtons[i].setIcon(filledstar30);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: label for the rating system
    private void setStarTag() {
        JLabel starTag = new JLabel("Stars");
        starTag.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
        starTag.setBounds(550, 200, 75, 50);
        bookEditor.add(starTag);
    }

    // MODIFIES: this
    // EFFECTS: Part of the GUI that handles whether or not the book is read
    private void setReadStatus() {
        JPanel readStatus = new JPanel();
        readStatus.setLayout(new FlowLayout());

        done = new JRadioButton("Done");
        notDone = new JRadioButton("Not Done");
        done.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
        notDone.setFont(new Font("Lucida Handwriting", Font.PLAIN, 23));
        notDone.setSelected(true);

        group = new ButtonGroup();
        group.add(done);
        group.add(notDone);
        readStatus.add(done);
        readStatus.add(notDone);

        readStatus.setBounds(550, 320, 150, 100);
        readStatus.setBackground(new Color(145, 129, 81));
        done.setBackground(new Color(145, 129, 81));
        notDone.setBackground(new Color(145, 129, 81));
        bookEditor.add(readStatus);
    }

    // MODIFIES: this
    // EFFECTS: Creates the save and remove buttons
    private void bottomButtons() {
        save = new JButton("Save");
        remove = new JButton("Remove");

        save.setBounds(545, 560, 75, 35);
        remove.setBounds(650, 560, 85, 35);

        bookEditor.add(save);
        bookEditor.add(remove);

        save.addActionListener(this);
        remove.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: save function to save all the changes made in the window
    private void save() {
        book.setTitle(titleText.getText());
        book.setAuthor(authorText.getText());
        book.setGenre(genreText.getText());
        book.setSeries(seriesText.getText());

        book.giveReview(reviewText.getText());
        book.giveStarRating(rating);
        book.star(starred);

        ButtonModel selectedButton = group.getSelection();
        if (selectedButton != null) {
            if (selectedButton == done.getModel()) {
                book.readStatus(true);
            }
        } else {
            book.readStatus(false);
        }
    }

    // EFFECTS: returns whether or not the book is read
    public boolean readStatus() {
        return book.getReadStatus();
    }

    // EFFECTS: gets whether or not the user wanted to remove the book
    public boolean getRemoveStatus() {
        return removeStatus;
    }
}
