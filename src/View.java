package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.io.File;

public class View extends JFrame {

    public View() {
        // ICONS

        setTitle("Integrated Library System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        add(new LoginPanel(this), BorderLayout.CENTER);

        setVisible(true);
    }
}
class LoginPanel extends JPanel {
    private Image backgroundImage;
    private JFrame parentFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;


    public LoginPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new GridBagLayout());

        ImageIcon librarianNormal = new ImageIcon("Icons/librarian(1).png");
        ImageIcon librarianHover = new ImageIcon("Icons/librarian.png");
        ImageIcon readerNormal = new ImageIcon("Icons/reading(1).png");
        ImageIcon readerHover = new ImageIcon("Icons/reading.png");
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
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                // Perform login logic here
                if (username.equals("staff")) {
                    parentFrame.dispose(); // Close the login frame
                    StaffFrame staffFrame = new StaffFrame();
                    staffFrame.setVisible(true); // Show the main application frame
                }
                else if (username.equals("normal")) {
                    parentFrame.dispose(); // Close the login frame
                    NormalFrame normalFrame = new NormalFrame();
                    normalFrame.setVisible(true); // Show the main application frame
                }

                else {
                    JOptionPane.showMessageDialog(null, "Login failed. Please try again.");
                }
            }
        });


        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().createImage("Icons/bg.jpg");
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
    public StaffFrame() {
        setTitle("Integrated Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Book", new AddBookPanel());
        tabbedPane.addTab("View Books", new ViewBooksPanel());
        tabbedPane.addTab("Manage Users", new ManageUsersPanel());
        tabbedPane.addTab("Book List", new BookListPanel());

        add(tabbedPane);

    }
}
class NormalFrame extends JFrame {
    public NormalFrame() {
        setTitle("Integrated Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("View Books", new ViewBooksPanel());
        tabbedPane.addTab("Booklist", new BookListPanel());
        add(tabbedPane);

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
    private JTextField idField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField pagesField;
    private JTextField borrowDaysField;
    private JTextField explanationField;
    private JButton createButton;
    private JLabel imageLabel;
    private Image backgroundImage;

    public AddBookPanel() {
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

        controlPanel.add(new CustomLabel("Book ID:"), gbc);
        gbc.gridy++;
        controlPanel.add(new CustomLabel("Book Title:"), gbc);
        gbc.gridy++;
        controlPanel.add(new CustomLabel("Author Name:"), gbc);
        gbc.gridy++;
        controlPanel.add(new CustomLabel("Number of Pages:"), gbc);
        gbc.gridy++;
        controlPanel.add(new CustomLabel("Borrow Days:"), gbc);
        gbc.gridy++;
        controlPanel.add(new CustomLabel("Explanation:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;

        idField = new JTextField(15);
        idField.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(idField, gbc);
        gbc.gridy++;
        titleField = new JTextField(15);
        titleField.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(titleField, gbc);
        gbc.gridy++;
        authorField = new JTextField(15);
        authorField.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(authorField, gbc);
        gbc.gridy++;
        pagesField = new JTextField(15);
        pagesField.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(pagesField, gbc);
        gbc.gridy++;
        borrowDaysField = new JTextField(15);
        borrowDaysField.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(borrowDaysField, gbc);
        gbc.gridy++;
        explanationField = new JTextField(15);
        explanationField.setPreferredSize(new Dimension(200, 50));
        controlPanel.add(explanationField, gbc);

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
                String id = idField.getText();
                String title = titleField.getText();
                String author = authorField.getText();
                int pages = Integer.parseInt(pagesField.getText());
                int borrowDays = Integer.parseInt(borrowDaysField.getText());
                // Do something with the book details (e.g., create a book object)
                System.out.println("New Book created: ID - " + id + ", Title - " + title + ", Author - " + author +
                        ", Pages - " + pages + ", Borrow Days - " + borrowDays);
            }
        });

        controlPanel.add(createButton, gbc);

        // Add control panel to the center
        add(controlPanel, BorderLayout.CENTER);

        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().createImage("Icons/bg.jpg");
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
    private Image backgroundImage;
    private JTable table;
    private JTable cartTable;
    private JButton borrowButton;
    private JButton addToCartButton;
    private JButton addToBookListButton;
    private JComboBox<String> bookListComboBox;

    public ViewBooksPanel() {
        setLayout(new GridBagLayout());

        // Create a table model with column names and initial data
        Vector<String> viewColumnNames = new Vector<>();
        viewColumnNames.add(""); // Add a checkbox column
        viewColumnNames.add("ID");
        viewColumnNames.add("Title");
        viewColumnNames.add("Author");
        viewColumnNames.add("Pages");
        viewColumnNames.add("Availability");

        Vector<String> cartColumnNames = new Vector<>();
        cartColumnNames.add("ID");
        cartColumnNames.add("Title");
        cartColumnNames.add("Author");
        cartColumnNames.add("Pages");
        cartColumnNames.add("Availability");

        Vector<Vector<Object>> data = new Vector<>();
        // Add sample data (you can replace this with actual data)
        for (int i = 1; i <= 8; i++) {
            Vector<Object> row = new Vector<>();
            row.add(false); // Add checkbox
            row.add(Integer.toString(i));
            row.add("Book " + i);
            row.add("Author " + i);
            row.add("200");
            row.add("Available");
            data.add(row);
        }

        // Create the main table
        DefaultTableModel tableModel = new DefaultTableModel(data, viewColumnNames) {
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

        // Add the main table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Add button to add selected books to cart
        addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(this::addToCart);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        add(addToCartButton, gbc);

        // Create the cart table
        DefaultTableModel cartTableModel = new DefaultTableModel(new Vector<>(), cartColumnNames);
        cartTable = new JTable(cartTableModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        add(cartScrollPane, gbc);

        // Add button to borrow from cart
        borrowButton = new JButton("Borrow from Cart");
        borrowButton.addActionListener(this::borrowFromCart);

        addToBookListButton = new JButton("Add to Book List");
        addToBookListButton.addActionListener(this::addToBookList);
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


        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().createImage("Icons/bg.jpg");
    }

    private void addToCart(ActionEvent e) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            if ((boolean) model.getValueAt(i, 0)) {
                Vector<Object> rowData = new Vector<>();
                for (int j = 1; j < model.getColumnCount(); j++) {
                    rowData.add(model.getValueAt(i, j));
                }
                cartModel.addRow(rowData);
            }
        }
    }

    private void borrowFromCart(ActionEvent e) {
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();
        StringBuilder booksBorrowed = new StringBuilder("Books borrowed:\n");

        for (int i = 0; i < cartModel.getRowCount(); i++) {
            booksBorrowed.append(cartModel.getValueAt(i, 1)).append("\n"); // Get book title from column index 2
        }

        JOptionPane.showMessageDialog(this, booksBorrowed.toString());
    }

    private void addToBookList(ActionEvent e) {
        // Add selected books to the selected book list
        String selectedBookList = (String) bookListComboBox.getSelectedItem();
        DefaultTableModel cartModel = (DefaultTableModel) cartTable.getModel();

        // Do something with the selected book list and the books in the cart
        StringBuilder message = new StringBuilder("Added to ").append(selectedBookList).append(":\n");
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            message.append(cartModel.getValueAt(i, 1)).append("\n"); // Get book title from column index 1
        }

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
    // manage penalties and view borrowing history of users
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
        backgroundImage = Toolkit.getDefaultToolkit().createImage("Icons/bg.jpg");

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
    private Image backgroundImage;
    private JTextField textField;
    private JButton createBookListButton;
    private JTable bookListTable;
    private JTable bookTable;

    public BookListPanel() {
        setLayout(new BorderLayout());

        // Text field and button panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textField = new JTextField(20);
        topPanel.add(textField);
        createBookListButton = new JButton("Create Book List");
        createBookListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle creating book list here
            }
        });
        topPanel.add(createBookListButton);
        add(topPanel, BorderLayout.NORTH);

        // Table for displaying book lists
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
        backgroundImage = Toolkit.getDefaultToolkit().createImage("Icons/bg.jpg");
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