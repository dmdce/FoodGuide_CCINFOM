import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel; // Import TableModel
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class: CustomerView
 * This class represents the view of the Customer.
 * (Rest of class description...)
 */
public class CustomerView extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private JPanel transactionPanel;
    private JPanel foodRatingPanel;
    private JPanel startPanel;
    private JPanel userActionsPanel;

    private JPanel transactionHistoryPanel;
    private JTextField promoInputField;

    private ArrayList<JButton> userActionsButtonList = new ArrayList<>();
    private ArrayList<JTextField> signInTextFieldList = new ArrayList<>();
    private JButton signInButton = new JButton("SIGN IN");
    private JButton backButton = new JButton("GO BACK");
    private JLabel userIdLabel;

    // Transaction Panel components
    private ArrayList<JButton> transactionButtonList = new ArrayList<>();
    private JComboBox<FoodItem> foodItemComboBox;
    private JComboBox<String> restaurantComboBox;
    private JTextArea transactionCartArea;
    private JLabel initialPriceLabel;
    private JLabel promoLabel;
    private JLabel finalPriceLabel;

    // Rating Panel components
    private ArrayList<JButton> ratingButtonList = new ArrayList<>();
    private JComboBox<Integer> qualityRatingComboBox;
    private JComboBox<Integer> authenticityRatingComboBox;
    private JTextArea ratingCommentsArea;
    private JLabel overallRatingLabel;

    // --- NEW: History Panel Components ---
    private ArrayList<JButton> historyButtonList = new ArrayList<>();
    private JTextField filterStartDateField;
    private JTextField filterEndDateField;
    private JComboBox<String> filterRestaurantComboBox;
    private JTextField filterMaxPriceField;
    private JTextField filterPromoField;
    private JTable historyTable;
    private DefaultTableModel historyTableModel;
    // --- END: History Panel Components ---

    /**
     * Constructs the customer view, initializes all panels,
     * and displays the frame.
     */
    public CustomerView() {
        super("Food Guide Customer Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(850, 550); // Increased height for the new panel

        startPanel = createSignInPanel();
        userActionsPanel = createUserActionsPanel();
        transactionPanel = createTransactionPanel();
        foodRatingPanel = createFoodRatingPanel();

        // --- NEW: Create history panel ---
        transactionHistoryPanel = createTransactionHistoryPanel();
        // --- END: New ---

        mainPanel.add(startPanel, "START_VIEW");
        mainPanel.add(userActionsPanel, "USER_ACTIONS_MENU");
        mainPanel.add(transactionPanel, "TRANSACTION_CREATE");
        mainPanel.add(foodRatingPanel, "RATING_MENU");

        // --- NEW: Add history panel to layout ---
        mainPanel.add(transactionHistoryPanel, "HISTORY_VIEW");
        // --- END: New ---

        add(mainPanel);

        setVisible(true);
        setResizable(true); // Allow resizing
    }

    /**
     * Creates the starting panel.
     * (Unchanged)
     */
    private JPanel createSignInPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridBagLayout());
        JLabel titlePanel = new JLabel("Welcome customer! Please sign in to continue.");
        titlePanel.setFont(new Font("Verdana", Font.BOLD, 20));
        labelPanel.add(titlePanel);
        panel.add(labelPanel, BorderLayout.NORTH);

        JPanel textFieldPanel = new JPanel(new GridLayout(2, 1));
        textFieldPanel.setBorder(new EmptyBorder(90, 180, 90, 180));
        ArrayList<JLabel> signInLabelList = new ArrayList<>();
        signInLabelList.add(new JLabel("Name:"));
        signInLabelList.add(new JLabel("Email Address:"));
        signInTextFieldList.clear();
        signInTextFieldList.add(new JTextField(20));
        signInTextFieldList.add(new JTextField(20));
        for (int i = 0; i < signInTextFieldList.size(); i++) {
            textFieldPanel.add(signInLabelList.get(i));
            textFieldPanel.add(signInTextFieldList.get(i));
        }
        panel.add(textFieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));
        buttonPanel.add(signInButton);
        buttonPanel.add(backButton);

        JButton backToMain = new JButton("BACK TO MAIN MENU");
        backToMain.setActionCommand("GO BACK TO MAIN");
        buttonPanel.add(backToMain);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    public ArrayList<String> getSignInInput() {
        ArrayList<String> texts = new ArrayList<>();
        for (JTextField jTextField : signInTextFieldList)
            texts.add(jTextField.getText());
        return texts;
    }

    /**
     * Creates the user actions panel.
     */
    private JPanel createUserActionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infoPanel.add(new JLabel("Logged in as User ID:"));
        userIdLabel = new JLabel("N/A");
        userIdLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        infoPanel.add(userIdLabel);
        panel.add(infoPanel, BorderLayout.NORTH);

        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel title = new JLabel("What would you like to do?");
        title.setFont(new Font("Verdana", Font.BOLD, 20));
        titlePanel.add(title);
        panel.add(titlePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));

        userActionsButtonList.clear();

        JButton createTransactionButton = new JButton("CREATE TRANSACTION");
        createTransactionButton.setActionCommand("CREATE_TRANSACTION");
        userActionsButtonList.add(createTransactionButton);

        // --- NEW: View History Button ---
        JButton viewHistoryButton = new JButton("VIEW TRANSACTION HISTORY");
        viewHistoryButton.setActionCommand("VIEW_TRANSACTION_HISTORY");
        userActionsButtonList.add(viewHistoryButton);
        // --- END: New ---

        JButton logOutButton = new JButton("LOG OUT");
        logOutButton.setActionCommand("LOG_OUT");
        userActionsButtonList.add(logOutButton);

        for (JButton button : userActionsButtonList)
            buttonPanel.add(button);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    public void setUserIdLabel(String userId) {
        this.userIdLabel.setText(userId);
    }

    /**
     * Refreshes dynamic panels.
     * --- MODIFIED: Added history panel ---
     */
    public void refreshPanels() {
        mainPanel.remove(userActionsPanel);
        mainPanel.remove(transactionPanel);
        mainPanel.remove(foodRatingPanel);
        // --- NEW: Remove history panel ---
        mainPanel.remove(transactionHistoryPanel);
        // --- END: New ---

        userActionsPanel = createUserActionsPanel();
        transactionPanel = createTransactionPanel();
        foodRatingPanel = createFoodRatingPanel();
        // --- NEW: Re-create history panel ---
        transactionHistoryPanel = createTransactionHistoryPanel();
        // --- END: New ---

        mainPanel.add(userActionsPanel, "USER_ACTIONS_MENU");
        mainPanel.add(transactionPanel, "TRANSACTION_CREATE");
        mainPanel.add(foodRatingPanel, "RATING_MENU");
        // --- NEW: Add history panel back ---
        mainPanel.add(transactionHistoryPanel, "HISTORY_VIEW");
        // --- END: New ---

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Attaches a shared ActionListener to all buttons.
     * --- MODIFIED: Added history buttons ---
     */
    public void setActionListener(ActionListener listener) {
        // ... (signInButton, backButton, southPanel buttons unchanged) ...
        signInButton.removeActionListener(listener);
        signInButton.addActionListener(listener);
        backButton.removeActionListener(listener);
        backButton.addActionListener(listener);
        JPanel southPanel = (JPanel) startPanel.getComponent(2);
        for (Component comp : southPanel.getComponents()) {
            if (comp instanceof JButton) {
                ((JButton) comp).removeActionListener(listener);
                ((JButton) comp).addActionListener(listener);
            }
        }

        // Buttons from userActionsPanel (now includes view history)
        for (JButton button : userActionsButtonList) {
            button.removeActionListener(listener);
            button.addActionListener(listener);
        }

        // Buttons from transactionPanel
        for (JButton button : transactionButtonList) {
            button.removeActionListener(listener);
            button.addActionListener(listener);
        }

        // Buttons from ratingPanel
        for (JButton button : ratingButtonList) {
            button.removeActionListener(listener);
            button.addActionListener(listener);
        }

        // Listener for the restaurant combo box
        if (restaurantComboBox != null) {
            restaurantComboBox.removeActionListener(listener);
            restaurantComboBox.addActionListener(listener);
            restaurantComboBox.setActionCommand("RESTAURANT_SELECTED");
        }

        // --- NEW: Listeners for history panel buttons ---
        for (JButton button : historyButtonList) {
            button.removeActionListener(listener);
            button.addActionListener(listener);
        }
        // --- END: New ---
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    /**
     * Creates the transaction panel.
     * (Unchanged)
     */
    private JPanel createTransactionPanel() {
        // ... (panel, title, form, gbc setup is unchanged) ...
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel("Create New Transaction");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        panel.add(titlePanel, BorderLayout.NORTH);
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ... (Restaurant and Food Item rows are unchanged) ...
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Select Restaurant:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        restaurantComboBox = new JComboBox<>();
        formPanel.add(restaurantComboBox, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Select Food Item:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        foodItemComboBox = new JComboBox<>();
        formPanel.add(foodItemComboBox, gbc);
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        JButton addItemButton = new JButton("Add Item");
        addItemButton.setActionCommand("ADD_ITEM");
        formPanel.add(addItemButton, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(new JLabel("Cart:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        transactionCartArea = new JTextArea(5, 20);
        transactionCartArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionCartArea);
        formPanel.add(scrollPane, gbc);

        // --- Row 3: Price Details ---
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weighty = 0;

        // --- THIS PANEL IS MODIFIED ---
        JPanel pricePanel = new JPanel(new GridLayout(0, 2, 5, 5)); // 0 rows, 2 cols
        pricePanel.setBorder(BorderFactory.createTitledBorder("Price Details"));

        pricePanel.add(new JLabel("Initial Price:"));
        initialPriceLabel = new JLabel("P0.00");
        pricePanel.add(initialPriceLabel);

        // --- MODIFICATION: Replaced static label with an input field ---
        pricePanel.add(new JLabel("Promo % (e.g., 0.10):"));
        promoInputField = new JTextField("0.10"); // Default 10%
        promoInputField.setToolTipText("Enter a value between 0.00 and 1.00");
        pricePanel.add(promoInputField);

        // This label now shows the calculated discount *amount*
        pricePanel.add(new JLabel("Discount Amount:"));
        promoLabel = new JLabel("-P0.00");
        pricePanel.add(promoLabel);
        // --- END MODIFICATION ---

        pricePanel.add(new JLabel("Final Price:"));
        finalPriceLabel = new JLabel("P0.00");
        finalPriceLabel.setFont(finalPriceLabel.getFont().deriveFont(Font.BOLD));
        pricePanel.add(finalPriceLabel);

        formPanel.add(pricePanel, gbc);
        // --- END OF MODIFIED PANEL ---

        panel.add(formPanel, BorderLayout.CENTER);

        // ... (Button panel is unchanged) ...
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));
        transactionButtonList.clear();
        transactionButtonList.add(addItemButton);
        JButton calculateButton = new JButton("Calculate Total");
        calculateButton.setActionCommand("CALCULATE_TOTAL");
        transactionButtonList.add(calculateButton);
        JButton proceedButton = new JButton("Proceed to Rating");
        proceedButton.setActionCommand("PROCEED_TO_RATING");
        transactionButtonList.add(proceedButton);
        JButton backButton = new JButton("GO BACK");
        backButton.setActionCommand("GO_BACK_FROM_TRANSACTION");
        transactionButtonList.add(backButton);
        for (JButton btn : transactionButtonList) {
            buttonPanel.add(btn);
        }
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the panel for rating a food transaction.
     * (Unchanged)
     */
    private JPanel createFoodRatingPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel("Rate Your Transaction");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        panel.add(titlePanel, BorderLayout.NORTH);
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Integer[] scores = {1, 2, 3, 4, 5};
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Food Quality (1-5):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        qualityRatingComboBox = new JComboBox<>(scores);
        formPanel.add(qualityRatingComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Authenticity (1-5):"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        authenticityRatingComboBox = new JComboBox<>(scores);
        formPanel.add(authenticityRatingComboBox, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JButton calcButton = new JButton("Calculate Overall");
        calcButton.setActionCommand("CALCULATE_RATING");
        formPanel.add(calcButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Overall Rating:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        overallRatingLabel = new JLabel("N/A");
        overallRatingLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        formPanel.add(overallRatingLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(new JLabel("Comments:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        ratingCommentsArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(ratingCommentsArea);
        formPanel.add(scrollPane, gbc);
        panel.add(formPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));
        ratingButtonList.clear();
        ratingButtonList.add(calcButton);
        JButton submitButton = new JButton("Submit Final Rating");
        submitButton.setActionCommand("SUBMIT_RATING");
        ratingButtonList.add(submitButton);
        JButton backButton = new JButton("GO BACK");
        backButton.setActionCommand("GO_BACK_TO_TRANSACTION");
        ratingButtonList.add(backButton);
        for (JButton btn : ratingButtonList) {
            buttonPanel.add(btn);
        }
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates the transaction history panel with filters and a results table.
     * @return JPanel for the HISTORY_VIEW card
     */
    private JPanel createTransactionHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Your Transaction History");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // --- Filter Panel (Top) ---
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter By"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Row 0: Start Date / End Date ---

        // Start Date Label
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE; // Labels don't fill
        gbc.weightx = 0.0; // Labels don't get extra width
        filterPanel.add(new JLabel("Start Date (yyyy-mm-dd):"), gbc);

        // Start Date Field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // <-- THE FIX
        gbc.weightx = 1.0; // <-- THE FIX: Allow this cell to grow
        filterStartDateField = new JTextField(10);
        filterStartDateField.setToolTipText("Format: yyyy-mm-dd");
        filterPanel.add(filterStartDateField, gbc);

        // End Date Label
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        filterPanel.add(new JLabel("End Date (yyyy-mm-dd):"), gbc);

        // End Date Field
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL; // <-- THE FIX
        gbc.weightx = 1.0; // <-- THE FIX: Allow this cell to grow
        filterEndDateField = new JTextField(10);
        filterEndDateField.setToolTipText("Format: yyyy-mm-dd");
        filterPanel.add(filterEndDateField, gbc);

        // --- Row 1: Restaurant / Max Price ---

        // Restaurant Label
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        filterPanel.add(new JLabel("Restaurant:"), gbc);

        // Restaurant ComboBox
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // <-- THE FIX
        gbc.weightx = 1.0; // <-- THE FIX
        filterRestaurantComboBox = new JComboBox<>(); // Will be populated
        filterPanel.add(filterRestaurantComboBox, gbc);

        // Max Price Label
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        filterPanel.add(new JLabel("Max Final Price (e.g., 500):"), gbc);

        // Max Price Field
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL; // <-- THE FIX
        gbc.weightx = 1.0; // <-- THE FIX
        filterMaxPriceField = new JTextField(10);
        filterPanel.add(filterMaxPriceField, gbc);

        // --- Row 2: Promo ---

        // Promo Label
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        filterPanel.add(new JLabel("Promo % (e.g., 0.10):"), gbc);

        // Promo Field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // <-- THE FIX
        gbc.weightx = 1.0; // <-- THE FIX
        filterPromoField = new JTextField(10);
        filterPromoField.setToolTipText("Enter as a decimal, e.g., 0.10 for 10%");
        filterPanel.add(filterPromoField, gbc);

        panel.add(filterPanel, BorderLayout.CENTER);

        // --- Results Table (Center) ---
        String[] columnNames = {"ID", "Date", "Restaurant", "Initial Price", "Promo %", "Final Price"};
        historyTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        historyTable = new JTable(historyTableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);

        // Add table
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4; // Span all columns
        gbc.fill = GridBagConstraints.BOTH; // Table fills both ways
        gbc.weightx = 1.0; // Table cell gets full width
        gbc.weighty = 1.0; // Make table take remaining vertical space
        filterPanel.add(scrollPane, gbc);

        // --- Button Panel (South) ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));

        historyButtonList.clear();

        JButton searchButton = new JButton("Search");
        searchButton.setActionCommand("SEARCH_HISTORY");
        historyButtonList.add(searchButton);

        JButton backButton = new JButton("GO BACK");
        backButton.setActionCommand("GO_BACK_FROM_HISTORY");
        historyButtonList.add(backButton);

        for(JButton btn : historyButtonList) {
            buttonPanel.add(btn);
        }
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }


    // --- Getters for Transaction Panel (Unchanged) ---
    public JComboBox<FoodItem> getFoodItemComboBox() { return foodItemComboBox; }
    public JTextArea getTransactionCartArea() { return transactionCartArea; }
    public JLabel getInitialPriceLabel() { return initialPriceLabel; }
    public JLabel getPromoLabel() { return promoLabel; }
    public JLabel getFinalPriceLabel() { return finalPriceLabel; }
    public JComboBox<String> getRestaurantComboBox() { return restaurantComboBox; }

    // --- Getters for Rating Panel (Unchanged) ---
    public JComboBox<Integer> getQualityRatingComboBox() { return qualityRatingComboBox; }
    public JComboBox<Integer> getAuthenticityRatingComboBox() { return authenticityRatingComboBox; }
    public JTextArea getRatingCommentsArea() { return ratingCommentsArea; }
    public JLabel getOverallRatingLabel() { return overallRatingLabel; }


    // --- NEW: Getters for History Filter Fields ---
    public String getFilterStartDate() { return filterStartDateField.getText(); }
    public String getFilterEndDate() { return filterEndDateField.getText(); }
    public String getFilterRestaurant() {
        Object item = filterRestaurantComboBox.getSelectedItem();
        return (item != null) ? item.toString() : "[All]";
    }
    public String getFilterMaxPrice() { return filterMaxPriceField.getText(); }
    public String getFilterPromo() { return filterPromoField.getText(); }
    // --- END: New Getters ---


    // --- Update methods for ComboBoxes (Unchanged) ---
    public void updateRestaurantComboBox(ArrayList<String> restaurantNames) {
        if (restaurantComboBox == null) return;
        restaurantComboBox.removeAllItems();
        restaurantComboBox.addItem("[Select One]");
        for (String name : restaurantNames) {
            restaurantComboBox.addItem(name);
        }
    }

    public void updateFoodItemComboBox(ArrayList<FoodItem> items) {
        if (foodItemComboBox == null) return;
        foodItemComboBox.removeAllItems();
        if (items != null && !items.isEmpty()) {
            for (FoodItem item : items) {
                foodItemComboBox.addItem(item);
            }
        }
    }

    // --- NEW: Method to update history filter ComboBox ---
    public void updateHistoryRestaurantFilter(ArrayList<String> restaurantNames) {
        if (filterRestaurantComboBox == null) return;
        filterRestaurantComboBox.removeAllItems();
        filterRestaurantComboBox.addItem("[All]"); // Add "All" option
        for (String name : restaurantNames) {
            filterRestaurantComboBox.addItem(name);
        }
    }

    public String getPromoInput() {
        return promoInputField.getText();
    }

    public void updateHistoryTable(ArrayList<TransactionData> transactions) {
        if (historyTableModel == null) return;

        // Clear old results
        historyTableModel.setRowCount(0);

        // Add new results
        if (transactions != null) {
            for (TransactionData transaction : transactions) {
                historyTableModel.addRow(new Object[]{
                        transaction.getTransactionId(),
                        transaction.getTransactionDate(),
                        transaction.getRestaurantName(),
                        String.format("%.2f", transaction.getInitialPrice()),
                        String.format("%.2f", transaction.getPromo()), // Will show 0.10
                        String.format("%.2f", transaction.getFinalPrice())
                });
            }
        }
    }
}