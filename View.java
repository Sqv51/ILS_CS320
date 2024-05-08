

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class View extends JFrame {

    public View() {
        // ICONS
        ImageIcon librarianNormal = new ImageIcon("Icons/librarian(1).png");
        ImageIcon librarianHover = new ImageIcon("Icons/librarian.png");
        ImageIcon readerNormal = new ImageIcon("Icons/reading(1).png");
        ImageIcon readerHover = new ImageIcon("Icons/reading.png");

        setTitle("Integrated Library System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        // BACKGROUND IMAGE
        Image backgroundImage = Toolkit.getDefaultToolkit().createImage("Icons/bg.jpg");
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        });
        getContentPane().setLayout(new GridBagLayout());

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setOpaque(true);

        // Using a semi-transparent white color (alpha value between 0 and 255)
        userPanel.setBackground(new Color(255, 255, 255, 150)); // Adjust the alpha as necessary
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
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
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
        enterButton.setBackground(new Color(255, 255, 255, 150));
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
                    dispose(); // Close the login frame
                    StaffFrame staffFrame = new StaffFrame();
                    staffFrame.setVisible(true); // Show the main application frame
                }
                else if (username.equals("normal")) {
                    dispose(); // Close the login frame
                    NormalFrame normalFrame = new NormalFrame();
                    normalFrame.setVisible(true); // Show the main application frame
                }

                else {
                    JOptionPane.showMessageDialog(View.this, "Login failed. Please try again.");
                }
            }
        });

        setVisible(true);
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
        tabbedPane.addTab("Booklist", new BooklistPanel());

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
        tabbedPane.addTab("Booklist", new BooklistPanel());
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
    private JButton createButton;
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

        // Button to create the book
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        createButton = new JButton("Create Book");
        createButton.addActionListener(e -> {
            // Action to create the book
            String id = idField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            int pages = Integer.parseInt(pagesField.getText());
            int borrowDays = Integer.parseInt(borrowDaysField.getText());
            // Do something with the book details (e.g., create a book object)
            System.out.println("New Book created: ID - " + id + ", Title - " + title + ", Author - " + author +
                    ", Pages - " + pages + ", Borrow Days - " + borrowDays);
        });

        controlPanel.add(createButton, gbc);

        // Add an empty panel to push the input panel to the center
        add(Box.createGlue(), BorderLayout.NORTH);
        add(Box.createGlue(), BorderLayout.WEST);
        add(Box.createGlue(), BorderLayout.EAST);
        add(Box.createGlue(), BorderLayout.SOUTH);
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

    public ViewBooksPanel() {
        setLayout(new GridBagLayout());

        // Create a table model with column names and initial data
        Vector<String> columnNames = new Vector<>();
        columnNames.add(""); // Add a checkbox column
        columnNames.add("ID");
        columnNames.add("Title");
        columnNames.add("Author");
        columnNames.add("Pages");
        columnNames.add("Availability");

        Vector<Vector<Object>> data = new Vector<>();
        // Add sample data (you can replace this with actual data)
        Vector<Object> row1 = new Vector<>();
        row1.add(false); // Add checkbox
        row1.add("1");
        row1.add("Book 1");
        row1.add("Author 1");
        row1.add("200");
        row1.add("Available");
        data.add(row1);
        Vector<Object> row2 = new Vector<>();
        row2.add(false); // Add checkbox
        row2.add("1");
        row2.add("Book 1");
        row2.add("Author 1");
        row2.add("200");
        row2.add("Available");
        data.add(row2);
        Vector<Object> row3 = new Vector<>();
        row3.add(false); // Add checkbox
        row3.add("1");
        row3.add("Book 1");
        row3.add("Author 1");
        row3.add("200");
        row3.add("Available");
        data.add(row3);
        Vector<Object> row4 = new Vector<>();
        row4.add(false); // Add checkbox
        row4.add("1");
        row4.add("Book 1");
        row4.add("Author 1");
        row4.add("200");
        row4.add("Available");
        data.add(row4);
        Vector<Object> row5 = new Vector<>();
        row5.add(false); // Add checkbox
        row5.add("1");
        row5.add("Book 1");
        row5.add("Author 1");
        row5.add("200");
        row5.add("Available");
        data.add(row5);
        Vector<Object> row6 = new Vector<>();
        row6.add(false); // Add checkbox
        row6.add("1");
        row6.add("Book 1");
        row6.add("Author 1");
        row6.add("200");
        row6.add("Available");
        data.add(row6);
        Vector<Object> row7 = new Vector<>();
        row7.add(false); // Add checkbox
        row7.add("1");
        row7.add("Book 1");
        row7.add("Author 1");
        row7.add("200");
        row7.add("Available");
        data.add(row7);
        Vector<Object> row8 = new Vector<>();
        row8.add(false); // Add checkbox
        row8.add("1");
        row8.add("Book 1");
        row8.add("Author 1");
        row8.add("200");
        row8.add("Available");
        data.add(row8);

        // Create the main table
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
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
        DefaultTableModel cartTableModel = new DefaultTableModel(new Vector<>(), columnNames);
        cartTable = new JTable(cartTableModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        add(cartScrollPane, gbc);

        // Add button to borrow from cart
        borrowButton = new JButton("Borrow from Cart");
        borrowButton.addActionListener(this::borrowFromCart);
        gbc.gridy++;
        add(borrowButton, gbc);

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
            booksBorrowed.append(cartModel.getValueAt(i, 2)).append("\n"); // Get book title from column index 2
        }

        JOptionPane.showMessageDialog(this, booksBorrowed.toString());
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

    public ManageUsersPanel() {
        setLayout(new FlowLayout());
        add(new JLabel("Add Book Panel"));
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
class BooklistPanel extends JPanel {
    private Image backgroundImage;

    public BooklistPanel() {
        setLayout(new FlowLayout());
        add(new JLabel("Add Book Panel"));
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


