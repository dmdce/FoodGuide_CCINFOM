import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;

import javax.swing.JTable;
import javax.swing.JScrollPane;

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
    private JPanel userReportPanel;
    private JPanel revenueReportPanel;
    private JPanel feedbackReportPanel;
    private JPanel createPromoPanel;

    private ArrayList<JButton> mainMenuButtonList = new ArrayList<>();
    private ArrayList<JButton> manageDatabaseButtonList = new ArrayList<>();
    private ArrayList<JButton> generateReportsButtonList = new ArrayList<>();

    private JButton backButton = new JButton("GO BACK");
    private JButton backGenerateReportsButton = new JButton("GO BACK");

    // User Registration/Creation Panel components
    private ArrayList<JTextField> userRegTextFieldList = new ArrayList<>();
    private ArrayList<JButton> userRegButtonList = new ArrayList<>();
    private JButton registerButton = new JButton("REGISTER USER");
    private JButton backUserRegButton = new JButton("GO BACK");

    // User Report Panel components
    private DefaultTableModel userReportTableModel;
    private JTable userReportTable;
    private JButton backUserReportButton = new JButton("GO BACK");

    private DefaultTableModel revenueReportTableModel;
    private JTable revenueReportTable;
    private JButton backRevenueReportButton = new JButton("GO BACK");
    private ArrayList<JButton> revenueReportButtonList = new ArrayList<>();

    private JComboBox<String> feedbackRestaurantComboBox;
    private JButton fetchFeedbackButton;
    private JLabel overallRatingLabel;
    private DefaultTableModel menuPopularityTableModel;
    private JTable menuPopularityTable;
    private DefaultTableModel commentsTableModel;
    private JTable commentsTable;
    private JButton backFeedbackReportButton = new JButton("GO BACK");
    private ArrayList<JButton> feedbackReportButtonList = new ArrayList<>();

    private ArrayList<JTextField> createPromoFields = new ArrayList<>();
    private ArrayList<JButton> createPromoButtonList = new ArrayList<>();
    private JComboBox<String> chooseRestaurantForPromo = new JComboBox<>();
    private JButton createPromo = new JButton("CREATE PROMO");
    private JButton backPromoCodeButton = new JButton("GO BACK");

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
        userReportPanel = createUserReportPanel();
        revenueReportPanel = createRevenueReportPanel();
        feedbackReportPanel = createFeedbackReportPanel();
        createPromoPanel = createPromoCodePanel();

        mainPanel.add(mainMenuPanel, "MAIN_MENU");
        mainPanel.add(manageDatabasePanel, "MANAGE_DATABASE_MENU");
        mainPanel.add(generateReportsPanel, "GENERATE_REPORTS_MENU");
        mainPanel.add(userRegPanel, "USER_REG");
        mainPanel.add(userCreationPanel, "USER_CREATE");
        mainPanel.add(userReportPanel, "USER_REPORT_PANEL");
        mainPanel.add(revenueReportPanel, "REVENUE_REPORT_PANEL");
        mainPanel.add(feedbackReportPanel, "FEEDBACK_REPORT_PANEL");
        mainPanel.add(createPromoPanel, "CREATE_PROMO_CODE_PANEL");

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
     * Creates a panel to display detailed feedback for a selected restaurant.
     * @return a JPanel for the FEEDBACK_REPORT_PANEL card
     */
    private JPanel createFeedbackReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- Top Selection Panel ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select Restaurant:"));
        feedbackRestaurantComboBox = new JComboBox<>();
        topPanel.add(feedbackRestaurantComboBox);

        // Per your new convention: no underscores
        fetchFeedbackButton = new JButton("Fetch Report");
        fetchFeedbackButton.setActionCommand("Fetch Feedback Report");
        topPanel.add(fetchFeedbackButton);

        panel.add(topPanel, BorderLayout.NORTH);

        // --- Center Content Panel ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Overall Rating
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingPanel.setBorder(BorderFactory.createTitledBorder("Overall Rating"));
        overallRatingLabel = new JLabel("N/A");
        overallRatingLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        ratingPanel.add(overallRatingLabel);
        // Make this panel align left in the BoxLayout
        ratingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(ratingPanel);

        // Menu Popularity Table
        String[] menuColumnNames = {"Menu Item", "Total Times Ordered"};
        menuPopularityTableModel = new DefaultTableModel(menuColumnNames, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        menuPopularityTable = new JTable(menuPopularityTableModel);
        JScrollPane menuScrollPane = new JScrollPane(menuPopularityTable);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("Menu Item Popularity (Ranked)"));
        menuScrollPane.setPreferredSize(new Dimension(800, 150)); // Give it a preferred size
        // Make this panel align left in the BoxLayout
        menuScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(menuScrollPane);

        // Comments Table
        String[] commentsColumnNames = {"User Comments"};
        commentsTableModel = new DefaultTableModel(commentsColumnNames, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        commentsTable = new JTable(commentsTableModel);
        JScrollPane commentsScrollPane = new JScrollPane(commentsTable);
        commentsScrollPane.setBorder(BorderFactory.createTitledBorder("Feedback Comments"));
        commentsScrollPane.setPreferredSize(new Dimension(800, 150)); // Give it a preferred size
        // Make this panel align left in the BoxLayout
        commentsScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(commentsScrollPane);

        panel.add(centerPanel, BorderLayout.CENTER);

        // --- Button Panel (South) ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#FCD303"));

        feedbackReportButtonList.clear();
        // Per your new convention: no underscores
        backFeedbackReportButton.setActionCommand("GO BACK FEEDBACK REPORT");
        feedbackReportButtonList.add(backFeedbackReportButton);

        // Also add the fetch button to this list so the listener is attached
        feedbackReportButtonList.add(fetchFeedbackButton);

        buttonPanel.add(backFeedbackReportButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Builds the database management selection panel.
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
        manageDatabaseButtonList.add(new JButton("Log a New Dish"));
        manageDatabaseButtonList.add(new JButton("Create User"));
        manageDatabaseButtonList.add(new JButton("Create Transaction"));
        manageDatabaseButtonList.add(new JButton("Create Promo"));
        manageDatabaseButtonList.add(new JButton("GO BACK"));

        for (JButton jButton : manageDatabaseButtonList) {
            manageDatabaseButtonPanel.add(jButton);
        }

        panel.add(manageDatabaseButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates a panel to display the ranked restaurant revenue report.
     * @return a JPanel for the REVENUE_REPORT_PANEL card
     */
    private JPanel createRevenueReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel("Restaurant Revenue & Transaction Report");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        titlePanel.add(label);
        panel.add(titlePanel, BorderLayout.NORTH);

        // Results Table
        String[] columnNames = {"Restaurant Name", "Total Revenue", "Total Transactions"};
        revenueReportTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells not editable
            }
        };
        revenueReportTable = new JTable(revenueReportTableModel);
        JScrollPane scrollPane = new JScrollPane(revenueReportTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        buttonPanel.setBackground(Color.decode("#FCD303"));

        revenueReportButtonList.clear();

        backRevenueReportButton.setActionCommand("GO BACK REVENUE REPORT"); // No underscore
        revenueReportButtonList.add(backRevenueReportButton);

        for(JButton btn : revenueReportButtonList) {
            buttonPanel.add(btn);
        }

        panel.add(buttonPanel, BorderLayout.SOUTH);

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

        JLabel label = new JLabel("Choose a report to generate");
        label.setFont(new Font("Verdana", Font.BOLD, 20));

        titlePanel.add(label);

        panel.add(titlePanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel generateReportsButtonPanel = new JPanel(new GridLayout(0, 1));
        generateReportsButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        generateReportsButtonPanel.setBackground(Color.decode("#FCD303"));

        generateReportsButtonList.clear();
        generateReportsButtonList.add(new JButton("Registered Users"));
        generateReportsButtonList.add(new JButton("User Referral Impact"));
        generateReportsButtonList.add(new JButton("Popular Menu Items"));
        generateReportsButtonList.add(new JButton("Food, Ratings and Feedback"));
        generateReportsButtonList.add(new JButton("Revenue and Transaction"));

        for (JButton jButton : generateReportsButtonList) {
            generateReportsButtonPanel.add(jButton);
        }

        generateReportsButtonPanel.add(backGenerateReportsButton);

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

        // BUTTONS
        JPanel userRegButtonPanel = new JPanel(new GridLayout(0, 1));
        userRegButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        userRegButtonPanel.setBackground(Color.decode("#FCD303"));

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

        // REGISTRATION FORM
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

        panel.add(formPanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel userRegButtonPanel = new JPanel(new FlowLayout()); // Use FlowLayout like your others
        userRegButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        userRegButtonPanel.setBackground(Color.decode("#FCD303"));

        // Initialize button list (clears it for refreshPanels)
        userRegButtonList.clear();

        // Add buttons to list
        userRegButtonList.add(createPromo);
        userRegButtonList.add(backPromoCodeButton);

        // Add buttons from the list to the panel
        for (JButton button : userRegButtonList) {
            userRegButtonPanel.add(button);
        }

        panel.add(userRegButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates a panel to display the creation menu for Promos
     * @return a JPanel for CREATE_PROMO card 
     */
    private JPanel createPromoCodePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        
        JLabel titleLabel = new JLabel("Create Promo Code");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel createPromoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        createPromoPanel.setBorder(BorderFactory.createTitledBorder("Select Food Culture"));
        
        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(new JLabel("Enter Code: "));
        labels.add(new JLabel("Enter Percentage (0.10 = 10%): "));
        labels.add(new JLabel("Enter Description: "));
        labels.add(new JLabel("(Optional) Choose Restaurant: "));

        createPromoFields.add(new JTextField());
        createPromoFields.add(new JTextField());
        createPromoFields.add(new JTextField());

        for (int i = 0; i < 4 ; i++) {
            createPromoPanel.add(labels.get(i));
            if (i == 3) break;
            createPromoPanel.add(createPromoFields.get(i));
        }
        createPromoPanel.add(chooseRestaurantForPromo);
        panel.add(createPromoPanel, BorderLayout.CENTER);

        // --- UPDATED: BUTTONS ---
        JPanel createPromoButtonPanel = new JPanel(new FlowLayout()); // Use FlowLayout like your others
        createPromoButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        createPromoButtonPanel.setBackground(Color.decode("#FCD303"));

        // Initialize button list (clears it for refreshPanels)
        createPromoButtonList.clear();

        // Add buttons to list
        createPromoButtonList.add(createPromo);
        createPromoButtonList.add(backUserRegButton);

        // Add buttons from the list to the panel
        for (JButton button : createPromoButtonList) {
            createPromoButtonPanel.add(button);
        }

        panel.add(createPromoButtonPanel, BorderLayout.SOUTH);
        return panel;
    }

    /** 
     * Sets the drop-down menu for restaurant names and clears fields
     */
    public void resetCreatePromoCode(ArrayList<String> restaurantNames) {
        for (JTextField field : createPromoFields)  {
            field.setText("");
            field.revalidate();
            field.repaint();
        }

        chooseRestaurantForPromo.removeAllItems();
        chooseRestaurantForPromo.addItem("");
        for (String name : restaurantNames) {
            chooseRestaurantForPromo.addItem(name);
        }

        chooseRestaurantForPromo.revalidate();
        chooseRestaurantForPromo.repaint();
    }

    /**
     * Gets the values inputted in the CreatePromo text fields
     * @return an ArrayList of Strings containing the inputted Data
     */
    public ArrayList<String> getCreatePromoFields() {
        ArrayList<String> fields = new ArrayList<>();
        for (JTextField field : createPromoFields) {
            if (field.getText().isEmpty())
                continue;
            fields.add(field.getText());
        }
        return fields;
    }

    /**
     * Gets chosen restaurant in create promo Field
     * returns "" if nothing is chosen
     * @return String or "" if nothing is chosen
     */
    public String getChosenRestaurantForPromo() {
        if (chooseRestaurantForPromo.getSelectedIndex() == -1) //no chosen restaurant 
            return "";

        return (String) chooseRestaurantForPromo.getSelectedItem();
    }

    /**
     * Creates a panel to display a list of all registered users.
     * @return a JPanel for the USER_REPORT_PANEL card
     */
    private JPanel createUserReportPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // LABELS
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel("Registered User List");
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        titlePanel.add(label);
        panel.add(titlePanel, BorderLayout.NORTH);

        String[] columnNames = {"User ID", "Username", "Email Address"};
        userReportTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells not editable
            }
        };
        userReportTable = new JTable(userReportTableModel);
        JScrollPane scrollPane = new JScrollPane(userReportTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        // BUTTONS
        JPanel userReportButtonPanel = new JPanel(new FlowLayout()); // Changed to FlowLayout
        userReportButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        userReportButtonPanel.setBackground(Color.decode("#FCD303"));

        userReportButtonPanel.add(backUserReportButton);

        panel.add(userReportButtonPanel, BorderLayout.SOUTH);

        return panel;
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

    /**
     * Clears and repopulates the user report table with fresh data.
     * @param users An ArrayList of UserData objects.
     */
    public void updateUserReportTable(ArrayList<UserData> users) {
        if (userReportTableModel == null) {
            return;
        }

        // Clear old results
        userReportTableModel.setRowCount(0);

        // Add new results
        if (users != null) {
            for (UserData user : users) {
                userReportTableModel.addRow(new Object[]{
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                });
            }
        }
    }

    /*
     * --------------------------------------------------------------------------------------------
     * LAYOUT AND BUTTON BACKBONE
     * --------------------------------------------------------------------------------------------
     */

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
        mainPanel.remove(userReportPanel);
        mainPanel.remove(revenueReportPanel);
        mainPanel.remove(feedbackReportPanel);

        // Generate and add
        manageDatabasePanel = createManageDatabasePanel();
        generateReportsPanel = createGenerateReportsPanel();
        userRegPanel = createUserRegPanel();
        userCreationPanel = createUserCreationPanel();
        userReportPanel = createUserReportPanel();
        revenueReportPanel = createRevenueReportPanel();
        feedbackReportPanel = createFeedbackReportPanel();

        mainPanel.add(manageDatabasePanel, "MANAGE_DATABASE_MENU");
        mainPanel.add(generateReportsPanel, "GENERATE_REPORTS_MENU");
        mainPanel.add(userRegPanel, "USER_REG");
        mainPanel.add(userCreationPanel, "USER_CREATE");
        mainPanel.add(userReportPanel, "USER_REPORT_PANEL");
        mainPanel.add(revenueReportPanel, "REVENUE_REPORT_PANEL");
        mainPanel.add(feedbackReportPanel, "FEEDBACK_REPORT_PANEL");

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
        backUserReportButton.removeActionListener(listener);

        // Add
        backButton.addActionListener(listener);
        backGenerateReportsButton.addActionListener(listener);
        backUserRegButton.addActionListener(listener);
        backUserReportButton.addActionListener(listener);
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

        for (JButton jButton : revenueReportButtonList) {
            jButton.removeActionListener(listener);
            jButton.addActionListener(listener);
        }

        for (JButton jButton : feedbackReportButtonList) {
            jButton.removeActionListener(listener);
            jButton.addActionListener(listener);
        }

        // Add ActionCommands (actions with same names but different functions)
        backUserReportButton.setActionCommand("GO BACK USER REPORT");
        backUserRegButton.setActionCommand("GO BACK USER REG");
    }

    /**
     * Clears and repopulates the revenue report table with fresh data.
     * @param data An ArrayList of RestaurantRevenueData objects.
     */
    public void updateRevenueReportTable(ArrayList<RestaurantRevenueData> data) {
        if (revenueReportTableModel == null) {
            return;
        }

        // Clear old results
        revenueReportTableModel.setRowCount(0);

        // Add new results
        if (data != null) {
            for (RestaurantRevenueData row : data) {
                revenueReportTableModel.addRow(new Object[]{
                        row.getRestaurantName(),
                        String.format("P%.2f", row.getTotalRevenue()),
                        row.getTotalTransactions()
                });
            }
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
     * Gets the selected restaurant name from the feedback panel's JComboBox.
     * @return The selected restaurant name.
     */
    public String getFeedbackSelectedRestaurant() {
        Object item = feedbackRestaurantComboBox.getSelectedItem();
        return (item != null) ? item.toString() : "[Select One]";
    }

    /**
     * Populates the restaurant selection JComboBox on the feedback panel.
     * @param restaurantNames An ArrayList of restaurant names.
     */
    public void populateFeedbackRestaurantComboBox(ArrayList<String> restaurantNames) {
        if (feedbackRestaurantComboBox == null) return;
        feedbackRestaurantComboBox.removeAllItems();
        feedbackRestaurantComboBox.addItem("[Select One]");
        for (String name : restaurantNames) {
            feedbackRestaurantComboBox.addItem(name);
        }
    }

    /**
     * Updates all components on the feedback panel with new data.
     * If data is null, it clears the panel.
     * @param data The complete RestaurantFeedbackReport DTO.
     */
    public void updateFeedbackReportPanel(RestaurantFeedbackReport data) {
        if (data == null) {
            updateOverallRatingLabel(0.0);
            updateMenuPopularityTable(null);
            updateCommentsTable(null);
        } else {
            updateOverallRatingLabel(data.getOverallRating());
            updateMenuPopularityTable(data.getMenuPopularity());
            updateCommentsTable(data.getComments());
        }
    }

    private void updateOverallRatingLabel(double rating) {
        if (overallRatingLabel == null) return;
        if (rating == 0.0) {
            overallRatingLabel.setText("N/A (No ratings yet)");
        } else {
            overallRatingLabel.setText(String.format("%.2f / 5.0", rating));
        }
    }

    private void updateMenuPopularityTable(ArrayList<MenuItemPopularityData> items) {
        if (menuPopularityTableModel == null) return;
        menuPopularityTableModel.setRowCount(0); // Clear old data
        if (items != null) {
            for (MenuItemPopularityData item : items) {
                menuPopularityTableModel.addRow(new Object[]{
                        item.getFoodAlias(),
                        item.getTotalOrdered()
                });
            }
        }
    }

    private void updateCommentsTable(ArrayList<String> comments) {
        if (commentsTableModel == null) return;
        commentsTableModel.setRowCount(0); // Clear old data
        if (comments != null) {
            for (String comment : comments) {
                commentsTableModel.addRow(new Object[]{ comment });
            }
        }
    }
}
