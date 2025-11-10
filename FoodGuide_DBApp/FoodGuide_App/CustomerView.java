import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class: CustomerView
 * This class represents the view of the Customer.
 * It handles the representation of panels in the program for the customer,
 * such as...
 */
public class CustomerView extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private JPanel transactionPanel;
    private JPanel foodRatingPanel;

    private JPanel startPanel;
    private JPanel userActionsPanel;

    private ArrayList<JButton> userActionsButtonList = new ArrayList<>();
    private ArrayList<JTextField> signInTextFieldList = new ArrayList<>();

    private JButton signInButton = new JButton("SIGN IN");
    private JButton backButton = new JButton("GO BACK");
    private JButton okButton = new JButton("OK");

    private JLabel userIdLabel;

    private ArrayList<JButton> transactionButtonList = new ArrayList<>();
    private JComboBox<FoodItem> foodItemComboBox; // Uses the new FoodItem.java
    private JComboBox<String> restaurantComboBox;
    private JTextArea transactionCartArea;
    private JLabel initialPriceLabel;
    private JLabel promoLabel;
    private JLabel finalPriceLabel;

    private ArrayList<JButton> ratingButtonList = new ArrayList<>();
    private JComboBox<Integer> qualityRatingComboBox;
    private JComboBox<Integer> authenticityRatingComboBox;
    private JTextArea ratingCommentsArea;
    private JLabel overallRatingLabel;

    /**
     * Constructs the customer view, initializes all panels,
     * and displays the frame.
     */
    public CustomerView() {
        super("Food Guide Customer Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(850, 450);

        startPanel = createSignInPanel();
        userActionsPanel = createUserActionsPanel();
        transactionPanel = createTransactionPanel();
        foodRatingPanel = createFoodRatingPanel();

        mainPanel.add(startPanel, "START_VIEW");
        mainPanel.add(userActionsPanel, "USER_ACTIONS_MENU");
        mainPanel.add(transactionPanel, "TRANSACTION_CREATE");
        mainPanel.add(foodRatingPanel, "RATING_MENU");

        add(mainPanel);

        setVisible(true);
        setResizable(false);
    }

    /**
     * Creates the starting panel.
     *
     * @return JPanel for the START_VIEW card
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
     * Creates the new user actions panel (replaces createMainMenuPanel).
     * @return JPanel for the USER_ACTIONS_MENU card
     */
    private JPanel createUserActionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // --- User ID Display ---
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infoPanel.add(new JLabel("Logged in as User ID:"));
        userIdLabel = new JLabel("N/A");
        userIdLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        infoPanel.add(userIdLabel);
        panel.add(infoPanel, BorderLayout.NORTH);

        // --- Center Title ---
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel title = new JLabel("What would you like to do?");
        title.setFont(new Font("Verdana", Font.BOLD, 20));
        titlePanel.add(title);
        panel.add(titlePanel, BorderLayout.CENTER);

        // --- BUTTONS ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));

        userActionsButtonList.clear();

        JButton createTransactionButton = new JButton("CREATE TRANSACTION");
        createTransactionButton.setActionCommand("CREATE_TRANSACTION");
        userActionsButtonList.add(createTransactionButton);

        // userActionsButtonList.add(new JButton("VIEW HISTORY"));

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
     */
    public void refreshPanels() {
        // Remove
        mainPanel.remove(userActionsPanel);
        mainPanel.remove(transactionPanel);
        mainPanel.remove(foodRatingPanel);

        // Generate and add
        userActionsPanel = createUserActionsPanel();
        transactionPanel = createTransactionPanel();
        foodRatingPanel = createFoodRatingPanel();

        mainPanel.add(userActionsPanel, "USER_ACTIONS_MENU");
        mainPanel.add(transactionPanel, "TRANSACTION_CREATE");
        mainPanel.add(foodRatingPanel, "RATING_MENU");

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Attaches a shared ActionListener to all buttons.
     */
    public void setActionListener(ActionListener listener) {
        // Buttons from signInPanel
        signInButton.removeActionListener(listener);
        signInButton.addActionListener(listener);

        backButton.removeActionListener(listener);
        backButton.addActionListener(listener);

        JPanel southPanel = (JPanel) startPanel.getComponent(2); // 2 is BorderLayout.SOUTH
        for (Component comp : southPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.removeActionListener(listener);
                btn.addActionListener(listener);
            }
        }

        // Buttons from userActionsPanel
        for (JButton button : userActionsButtonList) {
            button.removeActionListener(listener);
            button.addActionListener(listener);
        }

        // --- FIX 2: ADDED MISSING LISTENERS FOR NEW PANELS ---
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
        // --- END OF FIX 2 ---
    }

    /**
     * Returns the main panel containing all card views.
     * @return the CardLayout container panel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Returns the CardLayout manager for switching views.
     * @return the CardLayout used by this view
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }
    /**
     * Creates the transaction panel.
     * MOVED FROM ADMINVIEW.
     * @return JPanel for the TRANSACTION_CREATE card
     */
    private JPanel createTransactionPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // TITLE
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel("Create New Transaction");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        panel.add(titlePanel, BorderLayout.NORTH);

        // --- FORM PANEL (Center) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Restaurant Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Select Restaurant:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        restaurantComboBox = new JComboBox<>();
        formPanel.add(restaurantComboBox, gbc);

        // Row 1: Food Items JComboBox
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Select Food Item:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;

        foodItemComboBox = new JComboBox<>(); // Uses FoodItem.java
        formPanel.add(foodItemComboBox, gbc);

        // Row 1, Col 2: Add Item Button
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        JButton addItemButton = new JButton("Add Item");
        addItemButton.setActionCommand("ADD_ITEM");
        formPanel.add(addItemButton, gbc);

        // Row 2: Cart Area
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(new JLabel("Cart:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        transactionCartArea = new JTextArea(5, 20);
        transactionCartArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionCartArea);
        formPanel.add(scrollPane, gbc);

        // --- Row 3: Price Details (in a separate panel) ---
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        JPanel pricePanel = new JPanel(new GridLayout(0, 2, 5, 5));
        pricePanel.setBorder(BorderFactory.createTitledBorder("Price Details"));

        pricePanel.add(new JLabel("Initial Price:"));
        initialPriceLabel = new JLabel("P0.00");
        pricePanel.add(initialPriceLabel);

        pricePanel.add(new JLabel("Promo (10%):"));
        promoLabel = new JLabel("-P0.00");
        pricePanel.add(promoLabel);

        pricePanel.add(new JLabel("Final Price:"));
        finalPriceLabel = new JLabel("P0.00");
        finalPriceLabel.setFont(finalPriceLabel.getFont().deriveFont(Font.BOLD));
        pricePanel.add(finalPriceLabel);

        formPanel.add(pricePanel, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        // --- BUTTONS (South) ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19")); // Customer Theme

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
     * MOVED FROM ADMINVIEW.
     * @return JPanel for the RATING_MENU card
     */
    private JPanel createFoodRatingPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // TITLE
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel("Rate Your Transaction");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        panel.add(titlePanel, BorderLayout.NORTH);

        // --- RATING FORM PANEL (Center) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Integer[] scores = {1, 2, 3, 4, 5};

        // Row 0: Quality Rating
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Food Quality (1-5):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        qualityRatingComboBox = new JComboBox<>(scores);
        formPanel.add(qualityRatingComboBox, gbc);

        // Row 1: Authenticity Rating
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Authenticity (1-5):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        authenticityRatingComboBox = new JComboBox<>(scores);
        formPanel.add(authenticityRatingComboBox, gbc);

        // Row 2: Calculate Button
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JButton calcButton = new JButton("Calculate Overall");
        calcButton.setActionCommand("CALCULATE_RATING");
        formPanel.add(calcButton, gbc);

        // Row 3: Overall Rating Label
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

        // Row 4: Comments
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

        // --- BUTTONS (South) ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19")); // Customer Theme

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

    public JComboBox<FoodItem> getFoodItemComboBox() { // Uses FoodItem.java
        return foodItemComboBox;
    }

    public JTextArea getTransactionCartArea() {
        return transactionCartArea;
    }

    public JLabel getInitialPriceLabel() {
        return initialPriceLabel;
    }

    public JLabel getPromoLabel() {
        return promoLabel;
    }

    public JLabel getFinalPriceLabel() {
        return finalPriceLabel;
    }

    public JComboBox<Integer> getQualityRatingComboBox() {
        return qualityRatingComboBox;
    }

    public JComboBox<Integer> getAuthenticityRatingComboBox() {
        return authenticityRatingComboBox;
    }

    public JTextArea getRatingCommentsArea() {
        return ratingCommentsArea;
    }

    public JLabel getOverallRatingLabel() {
        return overallRatingLabel;
    }

    public JComboBox<String> getRestaurantComboBox() {
        return restaurantComboBox;
    }

    /**
     * Clears and repopulates the restaurant JComboBox with a new list of names.
     * @param restaurantNames An ArrayList of restaurant names from the database.
     */
    public void updateRestaurantComboBox(ArrayList<String> restaurantNames) {
        if (restaurantComboBox == null) {
            return;
        }
        restaurantComboBox.removeAllItems();
        restaurantComboBox.addItem("[Select One]");
        for (String name : restaurantNames) {
            restaurantComboBox.addItem(name);
        }
    }

    /**
     * Clears and repopulates the food item JComboBox.
     * @param items An ArrayList of FoodItem objects for the selected restaurant.
     */
    public void updateFoodItemComboBox(ArrayList<FoodItem> items) {
        if (foodItemComboBox == null) {
            return;
        }

        foodItemComboBox.removeAllItems();

        if (items != null && !items.isEmpty()) {
            for (FoodItem item : items) {
                foodItemComboBox.addItem(item);
            }
        }
    }

    // --- FIX 3: REMOVED THE INNER FoodItem CLASS ---
    // (It is now in its own FoodItem.java file)
}