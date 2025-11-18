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

    private JPanel startPanel;
    private JPanel userRegistrationPanel;
    private JPanel transactionPanel;
    private JPanel foodRatingPanel;
    private JPanel userActionsPanel;
    private JPanel transactionHistoryPanel;
    private JPanel restaurantRecommendationPanel;

    // Sign In Panel components
    private ArrayList<JButton> userActionsButtonList = new ArrayList<>();
    private ArrayList<JTextField> signInTextFieldList = new ArrayList<>();
    private JButton signInButton = new JButton("SIGN IN");
    private JButton newUserButton = new JButton("IM A NEW USER");
    private JButton backToMainMenuButton = new JButton("BACK TO MAIN MENU");
    private JLabel userIdLabel;

    // User Registration Panel components
    private ArrayList<JTextField> registerTextFieldList = new ArrayList<>();
    private JButton userRegistrationButton = new JButton("REGISTER");
    private JButton backUserRegistrationButton = new JButton("BACK TO MAIN MENU");

    // Transaction Panel components
    private ArrayList<JButton> transactionButtonList = new ArrayList<>();
    private JComboBox<FoodItem> foodItemComboBox;
    private JComboBox<String> restaurantComboBox;
    private JTextArea transactionCartArea;
    private JTextField promoInputField;
    private JLabel initialPriceLabel;
    private JLabel promoLabel;
    private JLabel finalPriceLabel;

    // Rating Panel components
    private ArrayList<JButton> ratingButtonList = new ArrayList<>();
    private JComboBox<Integer> qualityRatingComboBox;
    private JComboBox<Integer> authenticityRatingComboBox;
    private JTextArea ratingCommentsArea;
    private JLabel overallRatingLabel;

    // History Panel Components
    private ArrayList<JButton> historyButtonList = new ArrayList<>();
    private JTextField filterStartDateField;
    private JTextField filterEndDateField;
    private JComboBox<String> filterRestaurantComboBox;
    private JTextField filterMaxPriceField;
    private JTextField filterPromoField;
    private JTable historyTable;
    private DefaultTableModel historyTableModel;

    // Restaurant Recommendation panels
    private ArrayList<JButton> restaurantRecButtonList = new ArrayList<>();
    private JPanel originScrollPanel = new JPanel(); //will be overridden
    private JScrollPane originScrollPlane = new JScrollPane();
    private JScrollPane eventScrollPlane = new JScrollPane();
    private JScrollPane restaurantScrollPlane = new JScrollPane();
    private JTextField originSearchBar;
    private JTextField eventSearchBar;
    private CardLayout restaurantCardLayout;
    private JPanel restaurantCardPanel;
    private ArrayList<JToggleButton> originToggles = new ArrayList<>();
    private ArrayList<JToggleButton> eventToggles = new ArrayList<>();

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
        userRegistrationPanel = createUserRegistrationPanel();
        userActionsPanel = createUserActionsPanel();
        transactionPanel = createTransactionPanel();
        foodRatingPanel = createFoodRatingPanel();
        transactionHistoryPanel = createTransactionHistoryPanel();
        restaurantRecommendationPanel = createRestaurantRecommendationPanel();

        mainPanel.add(startPanel, "START_VIEW");
        mainPanel.add(userRegistrationPanel, "USER_REGISTRATION_VIEW");
        mainPanel.add(userActionsPanel, "USER_ACTIONS_MENU");
        mainPanel.add(transactionPanel, "TRANSACTION_CREATE");
        mainPanel.add(foodRatingPanel, "RATING_MENU");
        mainPanel.add(transactionHistoryPanel, "HISTORY_VIEW");
        mainPanel.add(restaurantRecommendationPanel, "RESTAURANT_RECOMMENDATION");

        add(mainPanel);

        setVisible(true);
        setResizable(true); // Allow resizing
    }

    /**
     * Creates the starting panel.
     * (Unchanged)
     */
    private JPanel createSignInPanel() {
        // Main panel
        JPanel panel = new JPanel(new BorderLayout());

        // Labels
        JPanel labelPanel = new JPanel(new GridBagLayout());
        JLabel titlePanel = new JLabel("Welcome customer! Please sign in to continue.");
        titlePanel.setFont(new Font("Verdana", Font.BOLD, 20));
        labelPanel.add(titlePanel);
        panel.add(labelPanel, BorderLayout.NORTH);

        // TextFields
        JPanel textFieldPanel = new JPanel(new GridLayout(2, 1));
        textFieldPanel.setBorder(new EmptyBorder(90, 180, 90, 180));
        ArrayList<JLabel> signInLabelList = new ArrayList<>();
        signInLabelList.add(new JLabel("Username:"));
        signInLabelList.add(new JLabel("Email Address:"));
        signInTextFieldList.clear();
        signInTextFieldList.add(new JTextField(20));
        signInTextFieldList.add(new JTextField(20));
        for (int i = 0; i < signInTextFieldList.size(); i++) {
            textFieldPanel.add(signInLabelList.get(i));
            textFieldPanel.add(signInTextFieldList.get(i));
        }
        panel.add(textFieldPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));
        buttonPanel.add(signInButton);
        buttonPanel.add(newUserButton);
        buttonPanel.add(backToMainMenuButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the new user registration panel.
     */
    private JPanel createUserRegistrationPanel() {
        // Main panel
        JPanel panel = new JPanel(new BorderLayout());

        // Labels
        JPanel labelPanel = new JPanel(new GridBagLayout());
        JLabel titlePanel = new JLabel("Welcome new customer! Please enter your details.");
        titlePanel.setFont(new Font("Verdana", Font.BOLD, 20));
        labelPanel.add(titlePanel);
        panel.add(labelPanel, BorderLayout.NORTH);

        // TextFields
        JPanel textFieldPanel = new JPanel(new GridLayout(2, 1));
        textFieldPanel.setBorder(new EmptyBorder(90, 180, 90, 180));
        ArrayList<JLabel> signInLabelList = new ArrayList<>();
        signInLabelList.add(new JLabel("Username:"));
        signInLabelList.add(new JLabel("Email Address:"));
        registerTextFieldList.clear();
        registerTextFieldList.add(new JTextField(20));
        registerTextFieldList.add(new JTextField(20));
        for (int i = 0; i < registerTextFieldList.size(); i++) {
            textFieldPanel.add(signInLabelList.get(i));
            textFieldPanel.add(registerTextFieldList.get(i));
        }
        panel.add(textFieldPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));
        buttonPanel.add(userRegistrationButton);
        buttonPanel.add(backUserRegistrationButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public ArrayList<String> getSignInInput(String panel) {
        ArrayList<String> texts = new ArrayList<>();
        ArrayList<JTextField> jTextFieldList = new ArrayList<>();

        jTextFieldList = switch (panel) {
            case "SIGN IN" -> signInTextFieldList;
            case "REGISTER" -> registerTextFieldList;
            default -> jTextFieldList;
        };
        for (JTextField jTextField : jTextFieldList)
            texts.add(jTextField.getText());

        return texts;
    }

    /**
     * Creates the user actions panel.
     */
    private JPanel createUserActionsPanel() {
        // Main panel
        JPanel panel = new JPanel(new BorderLayout());

        // Labels
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

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));

        userActionsButtonList.clear();

        JButton createTransactionButton = new JButton("DO A TRANSACTION");
        createTransactionButton.setActionCommand("CREATE TRANSACTION");
        userActionsButtonList.add(createTransactionButton);

        JButton viewHistoryButton = new JButton("VIEW TRANSACTION HISTORY");
        viewHistoryButton.setActionCommand("VIEW TRANSACTION HISTORY");
        userActionsButtonList.add(viewHistoryButton);

        JButton restaurantRecButton = new JButton("VIEW RESTAURANT RECOMMENDATION");
        restaurantRecButton.setActionCommand("VIEW RESTAURANT RECOMMENDATION");
        userActionsButtonList.add(restaurantRecButton);

        JButton logOutButton = new JButton("LOG OUT");
        logOutButton.setActionCommand("LOG OUT");
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
     * Creates the transaction panel.
     * (Unchanged)
     */
    private JPanel createTransactionPanel() {
        // ... (panel, title, form, gbc setup is unchanged) ...
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JLabel titleLabel = new JLabel("Create A Transaction");
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
        addItemButton.setActionCommand("ADD ITEM");
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

        JPanel pricePanel = new JPanel(new GridLayout(0, 2, 5, 5)); // 0 rows, 2 cols
        pricePanel.setBorder(BorderFactory.createTitledBorder("Price Details"));

        pricePanel.add(new JLabel("Initial Price:"));
        initialPriceLabel = new JLabel("P0.00");
        pricePanel.add(initialPriceLabel);

        pricePanel.add(new JLabel("Promo Code:"));
        promoInputField = new JTextField(); // Default 10%
        promoInputField.setToolTipText("Enter a valid code");
        pricePanel.add(promoInputField);

        // This label now shows the calculated discount *amount*
        pricePanel.add(new JLabel("Discount Amount:"));
        promoLabel = new JLabel("-P0.00");
        pricePanel.add(promoLabel);

        pricePanel.add(new JLabel("Final Price:"));
        finalPriceLabel = new JLabel("P0.00");
        finalPriceLabel.setFont(finalPriceLabel.getFont().deriveFont(Font.BOLD));
        pricePanel.add(finalPriceLabel);

        formPanel.add(pricePanel, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));
        transactionButtonList.clear();
        transactionButtonList.add(addItemButton);
        JButton calculateButton = new JButton("Calculate Total");
        calculateButton.setActionCommand("CALCULATE TOTAL");
        transactionButtonList.add(calculateButton);
        JButton proceedButton = new JButton("Purchase and Proceed to Rating");
        proceedButton.setActionCommand("PROCEED TO RATING");
        transactionButtonList.add(proceedButton);
        JButton backButton = new JButton("GO BACK");
        backButton.setActionCommand("GO BACK TRANSACTION");
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
        submitButton.setActionCommand("SUBMIT RATING");
        ratingButtonList.add(submitButton);
        JButton backButton = new JButton("GO BACK");
        backButton.setActionCommand("GO BACK FOOD RATING");
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
        filterPanel.add(new JLabel("Promo Code:"), gbc);

        // Promo Field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // <-- THE FIX
        gbc.weightx = 1.0; // <-- THE FIX
        filterPromoField = new JTextField(10);
        filterPromoField.setToolTipText("Enter as a decimal, e.g., 0.10 for 10%");
        filterPanel.add(filterPromoField, gbc);

        panel.add(filterPanel, BorderLayout.CENTER);

        // --- Results Table (Center) ---
        String[] columnNames = {"ID", "Date", "Restaurant", "Initial Price", "Promo Code", "Final Price"};
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
        searchButton.setActionCommand("SEARCH HISTORY");
        historyButtonList.add(searchButton);

        JButton backButton = new JButton("GO BACK");
        backButton.setActionCommand("GO BACK HISTORY");
        historyButtonList.add(backButton);

        for(JButton btn : historyButtonList) {
            buttonPanel.add(btn);
        }
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates the restaurant recommendation panel.
     * @return JPanel for the RESTAURANT_RECOMMENDATION card
     */
    private JPanel createRestaurantRecommendationPanel() {
        // Reset Restaurant Recommendation Button List to eliminate duplicates
        restaurantRecButtonList.clear();

        // Main Panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Restaurant Recommendation");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        //Create a card layout to switch between panels
        restaurantCardLayout = new CardLayout();
        // Main panel holding card layout
        restaurantCardPanel = new JPanel(restaurantCardLayout);
        panel.add(restaurantCardPanel, BorderLayout.CENTER);

        restaurantCardPanel.add(createSelectOrigin(), "VIEW SEARCH FOOD CULTURE");
        restaurantCardPanel.add(createSelectEvent(), "VIEW SEARCH FOOD EVENT");
        restaurantCardPanel.add(createRestaurantRecos(), "VIEW RESTAURANT RECOMMENDATION");
        // restaurantCardLayout.show(restaurantCardPanel, "VIEW SEARCH FOOD CULTURE");

        return panel;
    }

    /// HELPER FUNCTIONS FOR RESTAURANT RECOMMENDATION;
    private JPanel createSelectOrigin() {
        JPanel searchFoodCulture = new JPanel(new BorderLayout());
        searchFoodCulture.setBorder(BorderFactory.createTitledBorder("Select Food Culture"));

        // Search Bar
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        originSearchBar = new JTextField(55);
        JButton searchOrigin = new JButton("Search");
        searchOrigin.setActionCommand("SEARCH ORIGIN");
        restaurantRecButtonList.add(searchOrigin);
        searchBarPanel.add(originSearchBar);
        searchBarPanel.add(searchOrigin);
        searchFoodCulture.add(searchBarPanel, BorderLayout.NORTH);

        // Scroll plane
        originScrollPlane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        originScrollPlane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        searchFoodCulture.add(originScrollPlane);

        // Button Panel (South)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));

        JButton backButton = new JButton("GO BACK");
        backButton.setActionCommand("GO BACK RESTAURANT RECOMMENDATION");
        restaurantRecButtonList.add(backButton);

        JButton proceedButton = new JButton("Proceed to Food Events");
        proceedButton.setActionCommand("PROCEED RESTAURANT RECOMMENDATION");
        restaurantRecButtonList.add(proceedButton);

        buttonPanel.add(backButton);
        buttonPanel.add(proceedButton);
        searchFoodCulture.add(buttonPanel, BorderLayout.SOUTH);

        return searchFoodCulture;
    }

    private JPanel createSelectEvent() {
        JPanel searchFoodEvent = new JPanel(new BorderLayout());
        searchFoodEvent.setBorder(BorderFactory.createTitledBorder("Select Food Event"));

        //search bar
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        eventSearchBar = new JTextField(55);
        JButton searchEvent = new JButton("Search");
        searchEvent.setActionCommand("SEARCH EVENT");
        restaurantRecButtonList.add(searchEvent);
        searchBarPanel.add(eventSearchBar);
        searchBarPanel.add(searchEvent);
        searchFoodEvent.add(searchBarPanel, BorderLayout.NORTH);

        // Scroll plane
        eventScrollPlane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        eventScrollPlane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        searchFoodEvent.add(eventScrollPlane);

        // Button Panel (South)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));

        JButton backButton = new JButton("GO BACK");
        backButton.setActionCommand("GO BACK RESTAURANT RECOMMENDATION");
        restaurantRecButtonList.add(backButton);

        JButton proceedButton = new JButton("Get Recommendations");
        proceedButton.setActionCommand("PROCEED RESTAURANT RECOMMENDATION");
        restaurantRecButtonList.add(proceedButton);

        buttonPanel.add(backButton);
        buttonPanel.add(proceedButton);
        searchFoodEvent.add(buttonPanel, BorderLayout.SOUTH);

        return searchFoodEvent;
    }

    private JPanel createRestaurantRecos() {
        JPanel restaurantRecos = new JPanel(new BorderLayout());
        restaurantRecos.setBorder(BorderFactory.createTitledBorder("Restaurant Recommendations"));

        // Scroll plane
        restaurantScrollPlane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        restaurantScrollPlane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        restaurantRecos.add(restaurantScrollPlane);

        // Button panel (south)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK RESTAURANT RECOMMENDATION");
        restaurantRecButtonList.add(okButton);

        buttonPanel.add(okButton);
        restaurantRecos.add(buttonPanel, BorderLayout.SOUTH);

        return restaurantRecos;
    }
    
    public void populateFoodOriginsScroll(ArrayList<String> origins) {
        populateFoodOriginsScroll(origins, "");
    }

    /**
     * Puts origin toggle buttons inside the food origin scroll plane and repaints
     */
    public void populateFoodOriginsScroll(ArrayList<String> origins, String filter) {
        JPanel plane = new JPanel(new GridBagLayout());
        originToggles.clear();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // gap between buttons
        gbc.fill = GridBagConstraints.HORIZONTAL; // make button fill width
        gbc.weightx = 1.0; // let it expand horizontally
        gbc.gridy = 0;
        gbc.gridx = 0;

        int col = 0;

        for (String origin : origins) {
            System.out.println(origin);
            if (!origin.toLowerCase().contains(filter.toLowerCase())) //lower case it all
                continue;

            JToggleButton originButton = new JToggleButton(origin);
            originButton.setPreferredSize(new Dimension(0, 100));
            originToggles.add(originButton);
            plane.add(originButton, gbc);

            col++;
            if (col == 2) {
                col = 0;
                gbc.gridx = 0;
                gbc.gridy++;
            } else {
                gbc.gridx = 1;
            }

        }
        // after loop: if last row has only 1 button, add invisible filler
        if (col == 1) {
            gbc.gridx = 1;
            JPanel filler = new JPanel();
            filler.setPreferredSize(new Dimension(0, 0)); // takes no vertical space
            plane.add(filler, gbc);
        }

        originScrollPlane.setViewportView(plane);
        originScrollPlane.revalidate();
        originScrollPlane.repaint();
    }
    
    public void populateFoodEventsScroll(ArrayList<String> events) {
        populateFoodEventsScroll(events, "");
    }

    /**
     * Puts origin toggle buttons inside the food origin scroll plane and repaints
     */
    public void populateFoodEventsScroll(ArrayList<String> events, String filter) {
        JPanel plane = new JPanel(new GridBagLayout());
        eventToggles.clear();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // gap between buttons
        gbc.fill = GridBagConstraints.HORIZONTAL; // make button fill width
        gbc.weightx = 1.0; // let it expand horizontally
        gbc.gridy = 0;
        gbc.gridx = 0;

        int col = 0;

        for (String event : events) {
            System.out.println(event);
            if (!event.toLowerCase().contains(filter.toLowerCase())) //lower case it all
                continue;

            JToggleButton eventButton = new JToggleButton(event);
            eventButton.setPreferredSize(new Dimension(0, 100));
            eventToggles.add(eventButton);
            plane.add(eventButton, gbc);

            col++;
            if (col == 2) {
                col = 0;
                gbc.gridx = 0;
                gbc.gridy++;
            } else {
                gbc.gridx = 1;
            }
        }
        // after loop: if last row has only 1 button, add invisible filler
        if (col == 1) {
            gbc.gridx = 1;
            JPanel filler = new JPanel();
            filler.setPreferredSize(new Dimension(0, 0)); // takes no vertical space
            plane.add(filler, gbc);
        }

        eventScrollPlane.setViewportView(plane);
        eventScrollPlane.revalidate();
        eventScrollPlane.repaint();
    }
    /**
     * Puts origin toggle buttons inside the food origin scroll plane and repaints
     */
    public void populateRestaurantRecos(ArrayList<RestaurantData> restaurants) {
        JPanel plane = new JPanel();
        BoxLayout planeBLayout = new BoxLayout(plane, BoxLayout.Y_AXIS);
        plane.setLayout(planeBLayout);

        for (RestaurantData restaurant : restaurants) {
            JPanel restaurantPlane = new JPanel();
            BoxLayout bLayout = new BoxLayout(restaurantPlane, BoxLayout.Y_AXIS);
            
            restaurantPlane.setLayout(bLayout);
            restaurantPlane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));;
            restaurantPlane.setBackground(Color.WHITE);
            restaurantPlane.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // --- Labels ---
            JLabel nameLabel = new JLabel("Name: " + restaurant.getRestaurantName());
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel descLabel = new JLabel("Description: " + restaurant.getDescription());
            descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel ratingLabel = new JLabel("Rating: " + restaurant.getTotalRating());
            ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            restaurantPlane.add(nameLabel);
            restaurantPlane.add(descLabel);
            restaurantPlane.add(ratingLabel);

            plane.add(restaurantPlane);
            plane.add(Box.createVerticalStrut(10));
        }
        JPanel bufferForBottomOfScroll = new JPanel();
        bufferForBottomOfScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        plane.add(bufferForBottomOfScroll);

        restaurantScrollPlane.setViewportView(plane);
        restaurantScrollPlane.revalidate();
        restaurantScrollPlane.repaint();
    }

    /**
     * This hides switching layout in a layer of Abstraction
     */
    public void switchRestaurantCardPanel(String key) {
        restaurantCardLayout.show(restaurantCardPanel, key);
    }
    /**
     * Gets selected origins for restaurant recommendation
     * @return ArrayList of Strings containing origin names
     */
    public ArrayList<String> getSelectedOrigins() {
        ArrayList<String> origins = new ArrayList<>();
        for (JToggleButton origin : originToggles) {
            if (!origin.isSelected())
                continue;

            origins.add(origin.getText());
        }

        if (origins.isEmpty()) {
            for (JToggleButton origin : originToggles)
                origins.add(origin.getText());
        }

        return origins;
    }

    /**
     * Gets selected events for restaurant recommendation
     * @return ArrayList of Strings containing event names
     */
    public ArrayList<String> getSelectedEvents() {
        ArrayList<String> events = new ArrayList<>();
        for (JToggleButton event : eventToggles) {
            if (!event.isSelected())
                continue;

            events.add(event.getText());
        }

        //if none is selected, return everything
        if (events.isEmpty()) {
            for (JToggleButton event : eventToggles)
                events.add(event.getText());
        }

        return events;
    }

    /**
     * Resets all selected toggles for restaurant recommendation
     */
    public void resetRestaurantRecommendation() {
        for (JToggleButton btn : originToggles)
            btn.setSelected(false);

        for (JToggleButton btn : eventToggles)
            btn.setSelected(false);
    }
    /**
     * Gets the text inside the search for origins
     * @Returns String
     */
    public String getOriginSearchBarField() {
        return originSearchBar.getText();
    }

    /**
     * Gets the text inside the search for events
     * @Returns String
     */
    public String getEventSearchBarField() {
        return eventSearchBar.getText();
    }

    // --- Getters for Transaction Panel (Unchanged) ---
    public JComboBox<FoodItem> getFoodItemComboBox() { return foodItemComboBox; }
    public JTextArea getTransactionCartArea() { return transactionCartArea; }
    public JLabel getInitialPriceLabel() { return initialPriceLabel; }
    public JLabel getPromoLabel() { return promoLabel; }
    public JTextField getPromoField() { return promoInputField; }
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
    public CardLayout getRestaurantCardLayout() {return restaurantCardLayout;}
    public JPanel getRestaurantCardPanel() {return restaurantCardPanel;}

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
                        transaction.getPromo(), // Will show 0.10
                        String.format("%.2f", transaction.getFinalPrice())
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
     * Refreshes dynamic panels.
     * --- MODIFIED: Added history panel ---
     */
    public void refreshPanels() {
        mainPanel.remove(userActionsPanel);
        mainPanel.remove(transactionPanel);
        mainPanel.remove(foodRatingPanel);
        mainPanel.remove(transactionHistoryPanel);

        userActionsPanel = createUserActionsPanel();
        transactionPanel = createTransactionPanel();
        foodRatingPanel = createFoodRatingPanel();
        transactionHistoryPanel = createTransactionHistoryPanel();

        mainPanel.add(userActionsPanel, "USER_ACTIONS_MENU");
        mainPanel.add(transactionPanel, "TRANSACTION_CREATE");
        mainPanel.add(foodRatingPanel, "RATING_MENU");
        mainPanel.add(transactionHistoryPanel, "HISTORY_VIEW");

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Attaches a shared ActionListener to all buttons.
     * --- MODIFIED: Added history buttons ---
     */
    public void setActionListener(ActionListener listener) {
        signInButton.removeActionListener(listener);
        signInButton.addActionListener(listener);

        newUserButton.removeActionListener(listener);
        newUserButton.addActionListener(listener);

        userRegistrationButton.removeActionListener(listener);
        userRegistrationButton.addActionListener(listener);

        backUserRegistrationButton.removeActionListener(listener);
        backUserRegistrationButton.addActionListener(listener);

        backToMainMenuButton.removeActionListener(listener);
        backToMainMenuButton.addActionListener(listener);

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

        // Listeners for history panel buttons
        for (JButton button : historyButtonList) {
            button.removeActionListener(listener);
            button.addActionListener(listener);
        }

        // Listeners for restaurant rec buttons
        for (JButton button : restaurantRecButtonList) {
            button.removeActionListener(listener);
            button.addActionListener(listener);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}
