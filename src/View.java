package src;

import src.repository.Book;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class View extends JFrame {
    private Control actionListener;
    public View() {
        // ICONS

        setTitle("Integrated Library System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        add(new LoginPanel(this), BorderLayout.CENTER);
    }

    public void setActionListener(Control actionListener) {
        this.actionListener = actionListener;
    }

    public Control getActionListener() {
        return actionListener;
    }
}
class LoginPanel extends JPanel {
    private Image backgroundImage;
    private JFrame parentFrame;
    public JTextField usernameField;
    public JTextField passwordField;


    public LoginPanel(View parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout());

        ImageIcon librarianNormal = new ImageIcon("src/Icons/librarian(1).png");
        ImageIcon librarianHover = new ImageIcon("src/Icons/librarian.png");
        ImageIcon readerNormal = new ImageIcon("src/Icons/reading(1).png");
        ImageIcon readerHover = new ImageIcon("src/Icons/reading.png");
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

        userPanel.setBackground(new Color(255, 255, 255, 150));
        userPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // USER TYPE
        JLabel libLabel = new JLabel(librarianNormal);
        JLabel readerLabel = new JLabel(readerNormal);
        JLabel libLabel2 = new JLabel("Librarian");
        libLabel2.setBorder(new EmptyBorder(10, 30, 10, 20));
        JLabel readerLabel2 = new JLabel("User");
        readerLabel2.setBorder(new EmptyBorder(10, 50, 10, 20));

        JPanel userTypePanel = new JPanel();
        userTypePanel.setOpaque(false);

        JPanel libPanel = new JPanel();
        libPanel.setLayout(new BoxLayout(libPanel, BoxLayout.Y_AXIS));
        libPanel.setOpaque(false);
        libPanel.add(libLabel);
        libPanel.add(libLabel2);

        JPanel readerPanel = new JPanel();
        readerPanel.setLayout(new BoxLayout(readerPanel, BoxLayout.Y_AXIS));
        readerPanel.setOpaque(false);
        readerPanel.add(readerLabel);
        readerPanel.add(readerLabel2);

        userTypePanel.add(libPanel);
        userTypePanel.add(readerPanel);
        userPanel.add(userTypePanel);

        // USER DATA
        JPanel userDataPanel = new JPanel();
        userDataPanel.setLayout(new GridLayout(4, 1));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        userDataPanel.add(usernameLabel);
        userDataPanel.add(usernameField);
        userDataPanel.add(passwordLabel);
        userDataPanel.add(passwordField);
        userDataPanel.setOpaque(false);
        userDataPanel.setBorder(new EmptyBorder(10, 10, 20, 10));
        userPanel.add(userDataPanel);

        // ENTER BUTTON
        JButton enterButton = new JButton("Login");
        enterButton.setPreferredSize(new Dimension(180, 40));
        enterButton.setBackground(new Color(255, 255, 255));
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        userPanel.add(enterButton);

        // ADD USER PANEL TO FRAME USING GRIDBAGLAYOUT
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10); // Margins around the panel
        gbc.anchor = GridBagConstraints.CENTER;
        add(userPanel, gbc);

        enterButton.setActionCommand("Enter-The-System");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = usernameField.getText()+","+passwordField.getText();
                parentFrame.getActionListener().action(e.getActionCommand(),data);
            }
        });

        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().createImage("src/Icons/bg.jpg");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}


class StaffFrame extends JFrame {
    public StaffFrame(Control control) throws SQLException {
        setTitle("Integrated Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Book", new AddBookPanel(control));
        tabbedPane.addTab("View Books", new ViewBooksPanel(control));
        tabbedPane.addTab("Manage Users", new ManageUsersPanel());
        tabbedPane.addTab("Book List", new BookListPanel(control));

        add(tabbedPane);

    }
}
class NormalFrame extends JFrame {
    private Control control;
    public NormalFrame(Control control) throws SQLException {
        setTitle("Integrated Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        this.control = control;

        JTabbedPane tabbedPane = new JTabbedPane();
        try {
            tabbedPane.addTab("View Books", new ViewBooksPanel(control));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tabbedPane.addTab("Booklist", new BookListPanel(control));
        tabbedPane.addTab("User Services", new UserPanel());
        add(tabbedPane);

    }
}
class UserPanel extends JPanel {
    private JButton payPenaltyButton, checkDueBooksButton, payMembershipFeeButton;
    private JLabel penaltyLabel;
    private JTextField penaltyAmountField;
    private JTable borrowedBooksTable;
    private JButton returnBookButton;
    private Image backgroundImage;

    // Simulated list of books with due dates
    private ArrayList<Book> borrowedBooks;

    public UserPanel() {
        loadBorrowedBooks(); // Load borrowed books for simulation
        initializeUI();

    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        backgroundImage = Toolkit.getDefaultToolkit().createImage("src/Icons/bg.jpg");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(5, 10, 5, 10);


        // Penalty Amount Field and Label
        penaltyLabel = new CustomLabel("Penalty Amount:");
        add(penaltyLabel, gbc);

        penaltyAmountField = new JTextField(10);
        add(penaltyAmountField, gbc);

        // Pay Penalty Button
        payPenaltyButton = new JButton("Pay Penalty");
        payPenaltyButton.addActionListener(this::payPenalty);
        add(payPenaltyButton, gbc);

        // Check Due Books Button
        checkDueBooksButton = new JButton("Check Due Books");
        checkDueBooksButton.addActionListener(this::checkBookReturnDates);
        add(checkDueBooksButton, gbc);
        // Initialize borrowed books table
        borrowedBooksTable = new JTable();
        JScrollPane borrowedBooksScrollPane = new JScrollPane(borrowedBooksTable);
        borrowedBooksScrollPane.setPreferredSize(new Dimension(200, 300));
        borrowedBooksScrollPane.setOpaque(false);
        borrowedBooksScrollPane.getViewport().setOpaque(false);
        borrowedBooksTable.setBackground(new Color(255, 255, 255, 200));
        returnBookButton = new JButton("Return Book");
        add(borrowedBooksScrollPane, gbc);
        add(returnBookButton, gbc);

        borrowedBooksTable.setModel(populateBorrowedBooksTable());
        // Pay Membership Fee Button
        payMembershipFeeButton = new JButton("Pay Membership Fee");
        payMembershipFeeButton.addActionListener(this::payMembershipFee);
        add(payMembershipFeeButton, gbc);
    }


    private void payPenalty(ActionEvent e) {
        String penalty = penaltyAmountField.getText();
        if (!penalty.isEmpty()) {
            try {
                double amount = Double.parseDouble(penalty);
                JOptionPane.showMessageDialog(this, "Penalty of $" + penalty + " paid.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid penalty amount.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a penalty amount.");
        }
    }

    private void checkBookReturnDates(ActionEvent e) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        StringBuilder dueSoonBooks = new StringBuilder();
        StringBuilder overdueBooks = new StringBuilder();

        for (Book book : borrowedBooks) {
            LocalDate dueDate = LocalDate.parse(book.dueDate, dtf);
            long daysUntilDue = ChronoUnit.DAYS.between(today, dueDate);

            if (daysUntilDue < 0) {
                overdueBooks.append(book.title).append(" is overdue by ").append(-daysUntilDue).append(" days.\n");
            } else if (daysUntilDue <= 2) {
                dueSoonBooks.append(book.title).append(" is due in ").append(daysUntilDue).append(" days.\n");
            }
        }

        if (overdueBooks.length() > 0) {
            JOptionPane.showMessageDialog(this, overdueBooks.toString(), "Overdue Books", JOptionPane.WARNING_MESSAGE);
        }

        if (dueSoonBooks.length() > 0) {
            JOptionPane.showMessageDialog(this, dueSoonBooks.toString(), "Books Due Soon", JOptionPane.INFORMATION_MESSAGE);
        } else if (overdueBooks.length() == 0) {
            JOptionPane.showMessageDialog(this, "No books are currently overdue or due soon.", "All Clear", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void payMembershipFee(ActionEvent e) {
        // Placeholder for membership fee payment logic
        JOptionPane.showMessageDialog(this, "Membership fee payment processed.", "Payment Successful",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadBorrowedBooks() {
        borrowedBooks = new ArrayList<>();
        borrowedBooks.add(new Book("Book 1", "2023-04-15"));
        borrowedBooks.add(new Book("Book 2", "2023-05-01"));
    }
    private DefaultTableModel populateBorrowedBooksTable() {
        //populate from the database
        // Assuming you have a method getBorrowedBooksData() that returns the data for the table
        Vector<Vector<Object>> data = Control.getBorrowedBooksData();
        Vector<String> columnNames = new Vector<>(Arrays.asList("Book ID", "Book Name", "Due Date"));
        return new DefaultTableModel(data, columnNames);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Simple book class for simulation
    class Book {
        String title;
        String dueDate;

        Book(String title, String dueDate) {
            this.title = title;
            this.dueDate = dueDate;
        }
    }
}


class CustomLabel extends JLabel {
    private Color backgroundColor;

    public CustomLabel(String text) {
        super(text);
        setOpaque(false);
        backgroundColor = new Color(255, 255, 255, 100); // Semi-transparent white
        setFont(getFont().deriveFont(Font.BOLD, 16)); // Set font to bold and size 16
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Draw background color
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}

class AddBookPanel extends JPanel {
    private JTextField genreField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private JTextField descriptionField;
    private JButton createButton;
    private JLabel imageLabel;
    private Image backgroundImage;

    public AddBookPanel(Control control) {
        setLayout(new BorderLayout());

        // Panel to hold input fields and button
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setOpaque(false); // Make the control panel transparent

        // Input fields for book details
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 100, 10, 100);
        gbc.anchor = GridBagConstraints.WEST;


        controlPanel.add(new CustomLabel("Book Title:"), gbc);
        gbc.gridy++;
        controlPanel.add(new CustomLabel("Author Name:"), gbc);
        gbc.gridy++;
        controlPanel.add(new CustomLabel("Genre:"), gbc);
        gbc.gridy++;
        controlPanel.add(new CustomLabel("Year:"), gbc);
        gbc.gridy++;
        controlPanel.add(new CustomLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;


        titleField = new JTextField(15);
        titleField.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(titleField, gbc);
        gbc.gridy++;
        authorField = new JTextField(15);
        authorField.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(authorField, gbc);
        gbc.gridy++;
        genreField = new JTextField(15);
        genreField.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(genreField, gbc);
        gbc.gridy++;
        yearField = new JTextField(15);
        yearField.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(yearField, gbc);
        gbc.gridy++;
        descriptionField = new JTextField(15);
        descriptionField.setPreferredSize(new Dimension(200, 50));
        controlPanel.add(descriptionField, gbc);

        // Add image label
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        imageLabel = new CustomLabel("Drag & Drop or Click to Select Image");
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setTransferHandler(new ImageTransferHandler(imageLabel));
        controlPanel.add(imageLabel, gbc);

        // Button to create the book
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        createButton = new JButton("Create Book");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to create the book
             String genre = genreField.getText();
                String title = titleField.getText();
                String author = authorField.getText();
                String year = yearField.getText();

                String description = descriptionField.getText();


                //print all the data to the console
                System.out.println("Book created with the following details:");
                System.out.println("Genre: " + genre);
                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("Year: " + year);
                System.out.println("Description: " + description);
                control.addBook(new Book(title, author, genre, Integer.parseInt(year), description));


            }
        });

        controlPanel.add(createButton, gbc);

        // Add control panel to the center
        add(controlPanel, BorderLayout.CENTER);

        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().createImage("src/Icons/bg.jpg");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
class ViewBooksPanel extends JPanel {
    private Control control;
    private Image backgroundImage;
    private JTextField searchField;
    private JButton searchButton;
    private JTable table;
    private JTable cartTable;
    private JButton borrowButton;
    private JButton addToCartButton;
    private JButton removeFromCartButton;
    private JButton rateBookButton;
    private JButton addToBookListButton;
    private JButton notifyAvailabilityButton;
    private JComboBox<String> bookListComboBox;

    public ViewBooksPanel(Control control) throws SQLException {
        this.control = control;
        initializeUI();
        loadBackgroundImage();
        createMainTable(control);
        createCartTable();
        addComponents(control);
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private void loadBackgroundImage() {
        backgroundImage = Toolkit.getDefaultToolkit().createImage("src/Icons/bg.jpg");
    }

  private void searchBooks(Control control) throws SQLException{
      ArrayList<Book> books;
      String search = searchField.getText();
      if(search.equals("")){
        books = control.getBooks();
        }
        else {
        books = control.getBooksByName(search);
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Book value : books) {
            Vector<Object> row = new Vector<>();
            row.add(false); // Checkbox
            row.add(value.getBookID());
            row.add(value.getBookName());
            row.add(value.getAuthor());
            row.add(value.getisAvailable());
            row.add(value.getGenre());
            row.add(value.getYear());
            row.add(value.getRating());
            model.addRow(row);
    }


  }
  private void createMainTable(Control control) throws SQLException {
    Vector<String> viewColumnNames = new Vector<>();
    // Add column names
    viewColumnNames.add(""); // Checkbox column
    viewColumnNames.add("ID");
    viewColumnNames.add("Title");
    viewColumnNames.add("Author");
    viewColumnNames.add("Availability");
    viewColumnNames.add("Genre");
    viewColumnNames.add("Year");
    viewColumnNames.add("Rating");

    Vector<Vector<Object>> data = new Vector<>();
    // Add sample data (you can replace this with actual data)
    //SQL QUERY gelecek
    ArrayList<Book> books = control.getBooks();
      for (Book value : books) {
          Vector<Object> row = new Vector<>();
          row.add(false); // Checkbox
          row.add(value.getBookID());
          row.add(value.getBookName());
          row.add(value.getAuthor());
          row.add(value.getisAvailable());
          row.add(value.getGenre());
          row.add(value.getYear());
          row.add(value.getRating());
          data.add(row);
      }

    // Create the main table
    DefaultTableModel tableModel = new DefaultTableModel(data, viewColumnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) {
                return Boolean.class; // Checkbox column
            }
            return super.getColumnClass(columnIndex);
        }
    };


    table = new JTable(tableModel);
    table.getColumnModel().getColumn(0).setPreferredWidth(20); // Set checkbox column width
    table.setPreferredScrollableViewportSize(new Dimension(600, 400)); // Set preferred size
    addTableMouseListener(table);

    // Setup the TableRowSorter
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
    table.setRowSorter(sorter);
}

private void createCartTable() {
    Vector<String> cartColumnNames = new Vector<>();
    // Add column names for the cart table
    cartColumnNames.add("");
    cartColumnNames.add("ID");
    cartColumnNames.add("Title");
    cartColumnNames.add("Author");
    cartColumnNames.add("Availability");
    cartColumnNames.add("Genre");
    cartColumnNames.add("Year");
    cartColumnNames.add("Rating");

    // Create the cart table
    DefaultTableModel cartTableModel = new DefaultTableModel(new Vector<>(), cartColumnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) {
                return Boolean.class; // Checkbox column
            }
            return super.getColumnClass(columnIndex);
        }
    };

    cartTable = new JTable(cartTableModel);
}

    private void addComponents(Control control) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            try {
                searchBooks(control);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.setOpaque(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(searchPanel, gbc);


        // Add the main table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        JLabel infoLabel = new CustomLabel("Double click on a book to see more information.");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy++;
        add(infoLabel, gbc);
        JPanel buttonPanel = new JPanel();
        addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(this::addToCart);
        removeFromCartButton = new JButton("Remove from Cart");
        removeFromCartButton.addActionListener(this::removeFromCart);
        buttonPanel.add(addToCartButton);
        buttonPanel.add(removeFromCartButton);
        buttonPanel.setOpaque(false);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        add(buttonPanel, gbc);


        rateBookButton = new JButton("Rate Book");
        rateBookButton.addActionListener(this::rateBook);
        gbc.gridy += 2; // Skipped a row
        gbc.fill = GridBagConstraints.NONE;
        add(rateBookButton, gbc);

        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        add(cartScrollPane, gbc);

        borrowButton = new JButton("Borrow from Cart");
        borrowButton.addActionListener(this::borrowFromCart);
        gbc.gridy++;
        add(borrowButton, gbc);

        addToBookListButton = new JButton("Add to Book List");
        addToBookListButton.addActionListener(e -> {
            try {
                addToBookList(e);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        bookListComboBox = new JComboBox<>(new String[]{"Book List 1", "Book List 2", "Book List 3"});
        JPanel bookListPanel = new JPanel();
        bookListPanel.setLayout(new BorderLayout());
        bookListPanel.add(addToBookListButton, BorderLayout.NORTH);
        bookListPanel.add(bookListComboBox, BorderLayout.SOUTH);
        JPanel borrowPanel = new JPanel();
        borrowPanel.add(borrowButton);
        borrowPanel.add(bookListPanel);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        add(borrowPanel, gbc);
        bookListPanel.setOpaque(false);
        borrowPanel.setOpaque(false);

        // Add the notifyAvailabilityButton
        notifyAvailabilityButton = new JButton("Notify When Available");
        notifyAvailabilityButton.addActionListener(this::onNotifyAvailabilityClicked);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(notifyAvailabilityButton, gbc);
    }
    private void addTableMouseListener(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Retrieve the explanation and image path
                        String explanation ="Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                                " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                                " ut aliquip ex ea commodo consequat.";
                        String imagePath ="src/Icons/book.jpg" ;
                        JTextArea explanationLabel = new JTextArea(explanation);
                        explanationLabel.setLineWrap(true);
                        explanationLabel.setWrapStyleWord(true);
                        explanationLabel.setEditable(false);
                        // Display the explanation and image in a message dialog
                        ImageIcon bookImage = new ImageIcon(imagePath);

                        JOptionPane optionPane = new JOptionPane(
                                explanationLabel,
                                JOptionPane.INFORMATION_MESSAGE,
                                JOptionPane.DEFAULT_OPTION,
                                bookImage
                        );

                        // Create a JDialog
                        JDialog dialog = optionPane.createDialog(null, table.getValueAt(selectedRow, 2).toString());

                        // Set custom size for the dialog
                        dialog.setSize(500, 300); // Set the desired size here

                        // Display the dialog
                        dialog.setVisible(true);
                    }
                }
            }
        });
    }

    private void onNotifyAvailabilityClicked(ActionEvent e) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        boolean foundAvailable = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean isChecked = (boolean) model.getValueAt(i, 0); // Checkbox column
            if (isChecked) {
                String status = (String) model.getValueAt(i, 5); // Assuming 'Availability' is in column 5
                if ("Available".equals(status)) {
                    JOptionPane.showMessageDialog(this, model.getValueAt(i, 2) + " is now available!", // Assuming 'Title'
                            // is in column 2
                            "Book Available", JOptionPane.INFORMATION_MESSAGE);
                    foundAvailable = true;
                } else {
                    JOptionPane.showMessageDialog(this, model.getValueAt(i, 2) + " is not available.", // Assuming 'Title'
                            // is in column 2
                            "Book Not Available", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        if (!foundAvailable) {
            JOptionPane.showMessageDialog(this, "No checked books are currently available.", "No Books Available",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


    // Action listeners for buttons

   private void addToCart(ActionEvent e) {
    // Add selected books to the cart
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();

    for (int i = 0; i < model.getRowCount(); i++) {
        if ((boolean) model.getValueAt(i, 0)) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(false); // Add checkbox at the start of each row
            for (int j = 1; j < model.getColumnCount(); j++) {
                rowData.add(model.getValueAt(i, j));
            }
            cartModel.addRow(rowData);
        }
    }
}

    private void removeFromCart(ActionEvent e) {
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        int[] selectedRows = cartTable.getSelectedRows();

        // Remove rows from the end to the start to avoid index shifting issues
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            cartModel.removeRow(selectedRows[i]);
        }
    }

    private void borrowFromCart(ActionEvent e) {
        // Borrow books from the cart
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        //loop through the cart and borrow the books in the cart one by one if the book is available
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String title = cartModel.getValueAt(i, 2).toString(); // Assuming the title is in column 2
            String status = cartModel.getValueAt(i, 4).toString(); // Assuming the status is in column 5
            if ("true".equals(status)) {
                //borrow the book via 2nd column which is the ID
               Control.borrowBook(Integer.parseInt(cartModel.getValueAt(i, 1).toString()));



                cartModel.removeRow(i);
            } else {
                JOptionPane.showMessageDialog(this, "The book '" + title + "' is not available.", "Book Not Available",
                        JOptionPane.ERROR_MESSAGE);
            }
        }


    }


    private void rateBook(ActionEvent e) {
        // Rate a book
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String title = table.getValueAt(selectedRow, 2).toString(); // Assuming the title is in column 2
            String[] options = {"1", "2", "3", "4", "5"};
            String rating = (String) JOptionPane.showInputDialog(this, "Rate the book: " + title, "Book Rating",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[4]);
            if (rating != null) {
                JOptionPane.showMessageDialog(this, "You rated '" + title + "' with " + rating + " stars.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to rate.", "Rating Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addToBookList(ActionEvent e) throws SQLException {
        // Add selected books to the selected book list
        String selectedBookList = (String) bookListComboBox.getSelectedItem();
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();

        // Do something with the selected book list and the books in the cart
        StringBuilder message = new StringBuilder("Added to ").append(selectedBookList).append(":\n");
        ArrayList<Integer> bookIDList = new ArrayList<>();
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            message.append(cartModel.getValueAt(i, 1)).append("\n"); // Get book title from column index 1
            bookIDList.add(Integer.parseInt((String)cartModel.getValueAt(i, 1)));
        }
        control.addBookList(selectedBookList,bookIDList);

        JOptionPane.showMessageDialog(this, message.toString());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
class ManageUsersPanel extends JPanel {

    private Image backgroundImage;
    private JTextField searchField;
    private JButton searchButton;
    private JTable userTable;
    private JTable historyTable;
    private JCheckBox penaltyCheckBox;
    private JButton managePenaltiesButton;

    public ManageUsersPanel() {
        setLayout(new BorderLayout());

        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().createImage("src/Icons/bg.jpg");

        // Create search components
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform search action
                String searchQuery = searchField.getText();
                // Display users in userTable
                String[][] userData = {
                        {"Seden Mutaf", "1001"},
                        {"Seden Mutaf", "1002"},
                        {"John Doe", "1003"}
                };
                String[] userColumnNames = {"User Name", "User ID"};
                DefaultTableModel userModel = new DefaultTableModel(userData, userColumnNames);
                userTable.setModel(userModel);
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Create user table
        userTable = new JTable();
        JScrollPane userTableScrollPane = new JScrollPane(userTable);

        // Create borrowing history table
        historyTable = new JTable();
        JScrollPane historyTableScrollPane = new JScrollPane(historyTable);

        // Create penalty management components
        JPanel penaltyPanel = new JPanel(new FlowLayout());
        penaltyCheckBox = new JCheckBox("Select All for Penalty");
        managePenaltiesButton = new JButton("Manage Penalties");
        managePenaltiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform penalty management action
                // Implement logic to manage penalties for selected users
                JOptionPane.showMessageDialog(null, "Manage Penalties action");
            }
        });
        penaltyPanel.add(penaltyCheckBox);
        penaltyPanel.add(managePenaltiesButton);

        // Add components to the panel
        add(searchPanel, BorderLayout.NORTH);
        add(userTableScrollPane, BorderLayout.WEST);
        add(historyTableScrollPane, BorderLayout.CENTER);
        add(penaltyPanel, BorderLayout.SOUTH);

        // Add mouse listener to userTable to handle row selection
        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] columnNames = {"Book ID", "Book Name", "Returned", "Days Left/Overdue"};
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedUserName = (String) userTable.getValueAt(selectedRow, 0);
                    // Display borrowing history for the selected user
                    if (selectedUserName.equalsIgnoreCase("Seden Mutaf")) {
                        String[][] data = {
                                {"123", "Book A", "Yes", "-"},
                                {"456", "Book B", "No", "5 days overdue"},
                                {"789", "Book C", "No", "2 days left"}
                        };
                        DefaultTableModel model = new DefaultTableModel(data, columnNames);
                        historyTable.setModel(model);
                        repaint();
                    } else {
                        // User not found or no history available
                        DefaultTableModel emptyModel = new DefaultTableModel(new String[0][0],columnNames);
                        historyTable.setModel(emptyModel);
                        repaint();
                    }
                }
            }
        });
        historyTable.setPreferredSize(new Dimension(300, 300));
        userTable.setPreferredSize(new Dimension(300, 300));
        historyTableScrollPane.setPreferredSize(new Dimension(200, 300));
        userTableScrollPane.setPreferredSize(new Dimension(200, 300));
        userTable.setBackground(new Color(255, 255, 255, 200));
        historyTable.setBackground(new Color(255, 255, 255, 200));
        historyTableScrollPane.setOpaque(false);
        userTableScrollPane.setOpaque(false);
        historyTableScrollPane.getViewport().setOpaque(false);
        userTableScrollPane.getViewport().setOpaque(false);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
class BookListPanel extends JPanel {
    private Control control;
    private Image backgroundImage;
    private JTextField textField;
    private JButton createBookListButton;
    private JTable bookListTable;
    private JTable bookTable;

    public BookListPanel(Control control) throws SQLException {
        this.control = control;
        setLayout(new BorderLayout());

        // Text field and button panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textField = new JTextField(20);
        topPanel.add(textField);
        createBookListButton = new JButton("Create Book List");
        createBookListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    control.createBookList(textField.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        topPanel.add(createBookListButton);
        add(topPanel, BorderLayout.NORTH);

        // Table for displaying book lists
        ArrayList<String> bookListNames = control.getBookListName();
        DefaultTableModel bookListModel = new DefaultTableModel(
                new Object[][]{{"Book List 1"}, {"Book List 2"}}, // Test case: two book lists
                new String[]{"Book List"}
        );
        bookListTable = new JTable(bookListModel);
        bookListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookListTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Get the selected book list
                    int selectedRow = bookListTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Retrieve and display the books of the selected book list
                        DefaultTableModel bookTableModel = (DefaultTableModel) bookTable.getModel();
                        bookTableModel.setRowCount(0); // Clear previous data
                        String selectedBookList = (String) bookListTable.getValueAt(selectedRow, 0);
                        ArrayList<Book> books;
                        try {
                            books = control.getBooksByBookList(selectedBookList);
                            for (Book book : books){
                                bookTableModel.addRow(new Object[]{book.getBookName(), book.getAuthor(), book.getBookID()});
                            }
                            repaint();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (selectedBookList.equals("Book List 1")) { // Test case: Add books to Book List 1
                            bookTableModel.addRow(new Object[]{"Book 1 Title", "Author 1", "1"});
                            bookTableModel.addRow(new Object[]{"Book 2 Title", "Author 2", "2"});
                            repaint();
                        } else if (selectedBookList.equals("Book List 2")) { // Test case: Add books to Book List 2
                            bookTableModel.addRow(new Object[]{"Book 3 Title", "Author 3", "3"});
                            bookTableModel.addRow(new Object[]{"Book 4 Title", "Author 4", "4"});
                            repaint();
                        }
                    }
                }
            }
        });
        JScrollPane bookListScrollPane = new JScrollPane(bookListTable);
        add(bookListScrollPane, BorderLayout.WEST);

        // Table for displaying books
        DefaultTableModel bookTableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Book Title", "Author", "Book ID"}
        );
        bookTable = new JTable(bookTableModel);
        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        add(bookScrollPane, BorderLayout.CENTER);
        bookTable.setPreferredSize(new Dimension(300, 300));
        bookListTable.setPreferredSize(new Dimension(300, 300));
        bookScrollPane.setPreferredSize(new Dimension(200, 300));
        bookListScrollPane.setPreferredSize(new Dimension(200, 300));
        bookTable.setBackground(new Color(255, 255, 255, 200));
        bookListTable.setBackground(new Color(255, 255, 255, 200));
        bookScrollPane.setOpaque(false);
        bookListScrollPane.setOpaque(false);
        bookScrollPane.getViewport().setOpaque(false);
        bookListScrollPane.getViewport().setOpaque(false);






        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().createImage("src/Icons/bg.jpg");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
class ImageTransferHandler extends TransferHandler {
    private JLabel label;

    public ImageTransferHandler(JLabel label) {
        this.label = label;
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(label);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = new ImageIcon(file.getPath());
                    label.setIcon(imageIcon);
                }
            }
        });
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return true;
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        Transferable transferable = support.getTransferable();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        for (DataFlavor flavor : flavors) {
            try {
                if (flavor.isFlavorJavaFileListType()) {
                    java.util.List<File> files = (java.util.List<File>) transferable.getTransferData(flavor);
                    if (!files.isEmpty()) {
                        File file = files.get(0);
                        ImageIcon imageIcon = new ImageIcon(file.getPath());
                        label.setIcon(imageIcon);
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
