package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The main menu GUI
public class MainMenuGUI extends JPanel implements ActionListener {

    private final JButton toBookShelf = new JButton("Bookshelf");
    private JPanel sideBar;
    private final JButton toReadingList = new JButton("Reading List");

    // EFFECTS: Sets the main menu
    public MainMenuGUI() {
        setSideBarMainMenu();
        this.setLayout(null);
        this.setPreferredSize(new Dimension(1000, 760));
        this.setBackground(Color.decode("#2a4b5f"));
        this.add(sideBar);

        mainMenuDisplay();
    }

    // MODIFIES: this
    // EFFECTS: Sets the sidebar menu
    private void setSideBarMainMenu() {
        sideBar = new JPanel(null);
        sideBar.setSize(250, 760);
        sideBar.setBackground(Color.BLACK);

        toBookShelf.setBounds(75, 280, 100, 30);
        sideBar.add(toBookShelf);

        toReadingList.setBounds(65, 400, 120, 30);
        sideBar.add(toReadingList);

        toBookShelf.addActionListener(this);
        toReadingList.addActionListener(this);

        JLabel sideBarIcon = new JLabel();
        ImageIcon icon = new ImageIcon("./data/Images/Icon.png");
        sideBarIcon.setIcon(icon);
        sideBarIcon.setBounds(77, 610, 96, 96);
        sideBar.add(sideBarIcon);
    }

    // EFFECTS: swaps between menus
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) this.getRootPane().getParent();

        if (e.getSource() == toBookShelf) {
            BooksAppGUI.cardLayout.show(frame.getContentPane(),"bookshelf");
        } else if (e.getSource() == toReadingList) {
            BooksAppGUI.cardLayout.show(frame.getContentPane(),"reading list");

        }
    }

    // MODIFIES: this
    // EFFECTS: Displays the picture and title
    private void mainMenuDisplay() {
        ImageIcon image = new ImageIcon("./data/Images/ReadingCouch.png");
        JLabel mainMenuDisplay = new JLabel();
        mainMenuDisplay.setText("<html>Welcome to your<br>Personal Library<html>");
        mainMenuDisplay.setHorizontalTextPosition(JLabel.CENTER);
        mainMenuDisplay.setVerticalTextPosition(JLabel.TOP);
        mainMenuDisplay.setFont(new Font("Lucida Handwriting", Font.PLAIN, 50));
        mainMenuDisplay.setForeground(Color.WHITE);
        mainMenuDisplay.setIcon(image);
        mainMenuDisplay.setIconTextGap(50);
        mainMenuDisplay.setBounds(345, 10, 563, 717);

        this.add(mainMenuDisplay);
    }


}
