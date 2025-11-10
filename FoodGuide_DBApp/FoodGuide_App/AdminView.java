import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class: AdminView
 * This class represents the view of the Admin.
 * It handles the representation of panels in the program for the admin.
 * It waits for signals from the admin controller to show what panels are
 * required to show or hide when an action is done in the program.
 */
public class AdminView extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    
    private JPanel mainPanel = new JPanel(cardLayout);
    private JPanel mainMenuPanel;
    private JPanel manageDatabasePanel;
    private JPanel generateReportsPanel;
    private JPanel userRegPanel;
    private JPanel userCreationPanel;

    private ArrayList<JButton> mainMenuButtonList = new ArrayList<>();
    private ArrayList<JButton> manageDatabaseButtonList = new ArrayList<>();
    private ArrayList<JButton> generateReportsButtonList = new ArrayList<>();
    private ArrayList<JTextField> userRegTextFieldList = new ArrayList<>();
    private ArrayList<JButton> userRegButtonList = new ArrayList<>();

    private JButton backButton = new JButton("GO BACK");
    private JButton backGenerateReportsButton = new JButton("GO BACK");
    private JButton backUserRegButton = new JButton("GO BACK");

    /**
     * Constructs the AdminView, initializes all sub-panels,
     * adds them to the CardLayout, and makes the frame visible.
     */
    public AdminView() {
        super("JavaJeep Admin Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(850, 450);

        mainMenuPanel = createMainMenuPanel();
        manageDatabasePanel = createManageDatabasePanel();
        generateReportsPanel = createGenerateReportsPanel();
        userRegPanel = createUserRegPanel();
        userCreationPanel = createUserCreationPanel();

        mainPanel.add(mainMenuPanel, "MAIN_MENU");
        mainPanel.add(manageDatabasePanel, "MANAGE_DATABASE_MENU");
        mainPanel.add(generateReportsPanel, "GENERATE_REPORTS_MENU");
        mainPanel.add(userRegPanel, "USER_REG");
        mainPanel.add(userCreationPanel, "USER_CREATE");

        add(mainPanel);

        setVisible(true);
        setResizable(true);
    }

    /**
     * Builds the main menu panel with navigation buttons.
     * @return a JPanel for the MAIN_MENU card
     */
    private JPanel createMainMenuPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // LABELS
        JPanel mainMenuLabelPanel = new JPanel(new GridBagLayout());

        ArrayList<JLabel> labelList = new ArrayList<>();
        labelList.add(new JLabel("Choose an option below!"));
        labelList.getFirst().setFont(new Font("Verdana", Font.BOLD, 20));

        for (JLabel labels : labelList) {
            mainMenuLabelPanel.add(labels);
        }

        panel.add(mainMenuLabelPanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel mainMenuButtonPanel = new JPanel(new FlowLayout());
        mainMenuButtonPanel.setBackground(Color.decode("#FCD303"));

        mainMenuButtonList.clear();
        mainMenuButtonList.add(new JButton("MANAGE DATABASE"));
        mainMenuButtonList.add(new JButton("GENERATE REPORTS"));
        mainMenuButtonList.add(new JButton("GO BACK TO MAIN"));

        for (JButton buttons : mainMenuButtonList) {
            mainMenuButtonPanel.add(buttons);
        }

        panel.add(mainMenuButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Builds the truck simulation selection panel.
     * @return a JPanel for the MANAGE_DATABASE_MENU card
     */
    private JPanel createManageDatabasePanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // LABELS
        JPanel manageDatabaseLabelPanel = new JPanel(new GridBagLayout());

        ArrayList<JLabel> labelList = new ArrayList<>(); // Used for multiple labels
        labelList.add(new JLabel("What kind of transaction do you want to do?"));
        labelList.getFirst().setFont(new Font("Verdana", Font.BOLD, 20));

        for (JLabel jLabel : labelList) {
            manageDatabaseLabelPanel.add(jLabel);
        }

        panel.add(manageDatabaseLabelPanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel manageDatabaseButtonPanel = new JPanel(new GridLayout(0, 1));
        manageDatabaseButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        manageDatabaseButtonPanel.setBackground(Color.decode("#FCD303"));

        manageDatabaseButtonList.clear();
        manageDatabaseButtonList.add(new JButton("Read User Feedback"));
        manageDatabaseButtonList.add(new JButton("Reserve Order"));
        manageDatabaseButtonList.add(new JButton("Log a New Dish"));
        manageDatabaseButtonList.add(new JButton("Personalize Meal Recommendations"));
        manageDatabaseButtonList.add(new JButton("GO BACK"));
        manageDatabaseButtonList.add(new JButton("Create User"));
        manageDatabaseButtonList.add(new JButton("Create Transaction"));

        for (JButton jButton : manageDatabaseButtonList) {
            manageDatabaseButtonPanel.add(jButton);
        }

        panel.add(manageDatabaseButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Shows a menu for generating reports.
     * @return a JPanel for the GENERATE_REPORTS_MENU card
     */
    private JPanel createGenerateReportsPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // LABELS
        JPanel titlePanel = new JPanel(new GridBagLayout());

        // GridBagLayout is used for multiple labels
        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.insets = new Insets(20, 0, 10, 0);

        JLabel label = new JLabel("Choose a report to generate");
        label.setFont(new Font("Verdana", Font.BOLD, 20));

        titlePanel.add(label);

        panel.add(titlePanel, BorderLayout.CENTER);

        // Create a vertical scroll panel
        // JScrollPane scrollPane = new JScrollPane(infoPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

        // BUTTONS
        JPanel generateReportsButtonPanel = new JPanel(new GridLayout(0, 1));
        generateReportsButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        generateReportsButtonPanel.setBackground(Color.decode("#FCD303"));

        generateReportsButtonList.clear();
        generateReportsButtonList.add(new JButton("New User Registration"));
        generateReportsButtonList.add(new JButton("User Referral Impact"));
        generateReportsButtonList.add(new JButton("Popular Menu Items"));
        generateReportsButtonList.add(new JButton("Food, Ratings and Feedback"));
        generateReportsButtonList.add(new JButton("Revenue and Transaction"));

        for (JButton jButton : generateReportsButtonList) {
            generateReportsButtonPanel.add(jButton);
        }

        backGenerateReportsButton.setActionCommand("GO BACK GENERATE REPORTS");
        generateReportsButtonPanel.add(backGenerateReportsButton);

        // panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(generateReportsButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createUserRegPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // LABELS
        JPanel titlePanel = new JPanel(new GridBagLayout());

        JLabel label = new JLabel("User Registration Report");
        label.setFont(new Font("Verdana", Font.BOLD, 20));

        titlePanel.add(label);

        panel.add(titlePanel, BorderLayout.NORTH);

        // QUERY RESULT


        // BUTTONS
        JPanel userRegButtonPanel = new JPanel(new GridLayout(0, 1));
        userRegButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        userRegButtonPanel.setBackground(Color.decode("#FCD303"));

        backUserRegButton.setActionCommand("GO BACK USER REG");
        userRegButtonPanel.add(backUserRegButton);

        panel.add(userRegButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createUserCreationPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // LABELS
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel("Input Registration Information");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        titlePanel.add(label);
        panel.add(titlePanel, BorderLayout.NORTH);

        // --- NEW: REGISTRATION FORM ---
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 0 rows, 2 cols, 10px gaps
        formPanel.setBorder(new EmptyBorder(20, 150, 20, 150)); // Add padding

        // Create labels
        JLabel nameLabel = new JLabel("Username:");
        JLabel emailLabel = new JLabel("Email Address:");

        // Initialize text field list (clears it for refreshPanels)
        userRegTextFieldList.clear();

        // Create text fields and add to list
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        userRegTextFieldList.add(nameField);
        userRegTextFieldList.add(emailField);

        // Add components to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        panel.add(formPanel, BorderLayout.CENTER); // Add form to the center

        // --- UPDATED: BUTTONS ---
        JPanel userRegButtonPanel = new JPanel(new FlowLayout()); // Use FlowLayout like your others
        userRegButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        userRegButtonPanel.setBackground(Color.decode("#FCD303"));

        // Initialize button list (clears it for refreshPanels)
        userRegButtonList.clear();

        // Create buttons and add to list
        JButton registerButton = new JButton("REGISTER USER");
        registerButton.setActionCommand("REGISTER_USER"); // New action command for the controller

        // This button should already be declared at the top of AdminView
        backUserRegButton.setActionCommand("GO BACK USER REG");

        userRegButtonList.add(registerButton);
        userRegButtonList.add(backUserRegButton);

        // Add buttons from the list to the panel
        for (JButton button : userRegButtonList) {
            userRegButtonPanel.add(button);
        }

        panel.add(userRegButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Refreshes the display panels of the application. This method removes existing panels,
     * recreates them to reflect any updated data, and then adds them back to the main panel.
     */
    public void refreshPanels() {
        // Remove
        mainPanel.remove(manageDatabasePanel);
        mainPanel.remove(generateReportsPanel);
        mainPanel.remove(userRegPanel);
        mainPanel.remove(userCreationPanel);

        // Generate and add
        manageDatabasePanel = createManageDatabasePanel();
        generateReportsPanel = createGenerateReportsPanel();
        userRegPanel = createUserRegPanel();
        userCreationPanel = createUserCreationPanel();

        mainPanel.add(manageDatabasePanel, "MANAGE_DATABASE_MENU");
        mainPanel.add(generateReportsPanel, "GENERATE_REPORTS_MENU");
        mainPanel.add(userRegPanel, "USER_REG");
        mainPanel.add(userCreationPanel, "USER_CREATE");

        // Revalidate and repaint
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Sets the ActionListener for various GUI components, ensuring that button clicks
     * and other actions are handled by the provided listener.
     *
     * @param listener The ActionListener to be used for the GUI components.
     */
    public void setActionListener(ActionListener listener) {
        // Remove to not stack
        backButton.removeActionListener(listener);
        backGenerateReportsButton.removeActionListener(listener);
        backUserRegButton.removeActionListener(listener);

        // Add
        backButton.addActionListener(listener);
        backGenerateReportsButton.addActionListener(listener);
        backUserRegButton.addActionListener(listener);
        for (JButton jButton : mainMenuButtonList) {
            jButton.removeActionListener(listener);
            jButton.addActionListener(listener);
        }
        for (JButton jButton : generateReportsButtonList) {
            jButton.removeActionListener(listener);
            jButton.addActionListener(listener);
        }
        for (JButton jButton : manageDatabaseButtonList) {
            jButton.removeActionListener(listener);
            jButton.addActionListener(listener);
        }
        for (JButton jButton : userRegButtonList) {
            jButton.removeActionListener(listener);
            jButton.addActionListener(listener);
        }
    }

    /**
     * Returns the main JPanel which acts as a container for different views
     *
     * @return The main JPanel of the view.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Returns the CardLayout manager used by the main panel to switch between different views.
     *
     * @return The CardLayout instance.
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    /**
     * Gets the registration input from the text fields.
     * @return An ArrayList<String> containing the username and email.
     */
    public ArrayList<String> getRegistrationInput() {
        ArrayList<String> texts = new ArrayList<>();
        for (JTextField jTextField : userRegTextFieldList) {
            texts.add(jTextField.getText());
        }
        return texts;
    }
}