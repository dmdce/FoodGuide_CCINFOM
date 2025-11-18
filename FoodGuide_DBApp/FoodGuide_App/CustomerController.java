import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Class: CustomerController
 * (Rest of class description...)
 */
public class CustomerController implements ActionListener {
    private CustomerModel model;
    private CustomerView view;

    // For User Details
    private ArrayList<String> signInInput;
    private String username;
    private String email;
    private Integer loggedInUserId;
    private boolean isSuccess;

    // For Transactions
    private ArrayList<FoodItem> cart;
    private ArrayList<String> restaurantList;
    private String currentTransactionRestaurant;
    private FoodItem selectedItem;
    private double initialPrice;
    private double currentTransactionInitialPrice;
    private Integer promoID;
    private double currentTransactionFinalPrice;
    private HashMap<FoodItem, Integer> itemQuantities;

    // For Reservations
    private String currentReservationRestaurant;
    private double currentReservationPrice;

    // For Restaurant Recommendations
    private ArrayList<String> selectedOrigins = new ArrayList<>();
    private ArrayList<String> selectedEvents = new ArrayList<>();
    private int restaurantCardLevel = 0;

    /**
     * Constructs a CustomerController...
     * (Unchanged)
     */
    public CustomerController(CustomerModel m, CustomerView v) {
        this.model = m;
        this.view = v;
        this.cart = new ArrayList<>();
        view.setActionListener(this);
        view.setVisible(true);
    }

    /**
     * Handles all button click events from the view.
     * --- MODIFIED: Added history cases ---
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the logged-in user ID for cases that need it
        Integer userId = model.getLoggedInUserId();

        switch (e.getActionCommand()) {
            // ------------------ SIGN IN Panel component ------------------
            case "SIGN IN":
                signInInput = view.getSignInInput("SIGN IN");
                username = signInInput.get(0);
                email = signInInput.get(1);
                if (username.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Username and/or Email cannot be empty.", "Login Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                loggedInUserId = model.getAm().loginCustomer(username, email); // Renamed to avoid conflict
                if (loggedInUserId != null) {
                    model.setLoggedInUser(loggedInUserId);
                    view.setUserIdLabel(loggedInUserId.toString());
                    view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                } else {
                    JOptionPane.showMessageDialog(view, "Invalid username or email. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "IM A NEW USER":
                view.getCardLayout().show(view.getMainPanel(), "USER_REGISTRATION_VIEW");
                break;

            // ------------------ USER REGISTRATION Panel component ------------------
            case "REGISTER":
                signInInput = view.getSignInInput("REGISTER");
                username = signInInput.get(0);
                email = signInInput.get(1);
                if (username.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Username and/or Email cannot be empty.", "Login Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Check if user has already registered, if not, register the new user
                isSuccess = model.getAm().registerNewUser(username, email);
                if (isSuccess) {
                    // Show a success message
                    JOptionPane.showMessageDialog(view,
                            "User registered successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Show an error message (e.g., user already exists)
                    JOptionPane.showMessageDialog(view,
                            "Error: Could not register user. If the user has already registered, the user will promptly log in.",
                            "Registration Failed",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Log in the new user
                loggedInUserId = model.getAm().loginCustomer(username, email); // Renamed to avoid conflict
                if (loggedInUserId != null) {
                    model.setLoggedInUser(loggedInUserId);
                    view.setUserIdLabel(loggedInUserId.toString());
                    view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                } else {
                    JOptionPane.showMessageDialog(view, "Unexpected error occurred.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

                break;

            // ------------------ LOG OUT component ------------------
            case "LOG OUT":
                model.setLoggedInUser(null);
                view.getCardLayout().show(view.getMainPanel(), "START_VIEW");
                break;

            // ------------------ TRANSACTION CREATION (Refactored to helper) Panel component ------------------
            case "RESTAURANT_SELECTED", "RES_RESTAURANT_SELECTED", "CREATE TRANSACTION", "RESERVE TRANSACTION", "USE RESERVATION",
                 "ADD ITEM", "ADD RESERVE ITEM", "CALCULATE TOTAL", "CALCULATE RESERVATION TOTAL", "PROCEED TO RATING",
                 "CALCULATE_RATING", "GO BACK FOOD RATING", "GO BACK TRANSACTION", "GO BACK RESERVATION":
                handleTransactionEvents(e); // Helper function for clarity
                break;

            // ------------------ Reservation panel component ------------------
            case "RESERVE ORDER":
                currentReservationRestaurant = (String) view.getResRestaurantComboBox().getSelectedItem();
                if (currentReservationRestaurant == null || currentReservationRestaurant.equals("[Select One]")) {
                    JOptionPane.showMessageDialog(view, "Please select a valid restaurant.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (currentReservationPrice == 0.0 || cart.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please add items and calculate the total.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (userId == null) {
                    JOptionPane.showMessageDialog(view, "Error: You are not logged in.", "Submission Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                itemQuantities = new HashMap<>();
                for (FoodItem item : cart) {
                    itemQuantities.put(item, itemQuantities.getOrDefault(item, 0) + 1);
                }

                isSuccess = model.getAm().submitReservation(userId, currentReservationRestaurant, currentReservationPrice, itemQuantities);
                if (isSuccess) {
                    JOptionPane.showMessageDialog(view, "Orders reserved! The promo and rating can be done when the reservation will be used.");
                    view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                } else {
                    JOptionPane.showMessageDialog(view, "Error: Could not reserve transaction.\nCheck console for details.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
                break;

            // ------------------ SUBMIT RATING panel component ------------------
            case "SUBMIT RATING":
                int finalQuality = (int) view.getQualityRatingComboBox().getSelectedItem();
                int finalAuthenticity = (int) view.getAuthenticityRatingComboBox().getSelectedItem();
                String comments = view.getRatingCommentsArea().getText();
                double finalOverall = (finalQuality + finalAuthenticity) / 2.0;

                if (userId == null) {
                    JOptionPane.showMessageDialog(view, "Error: You are not logged in.", "Submission Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                itemQuantities = new HashMap<>();
                for (FoodItem item : cart) {
                    itemQuantities.put(item, itemQuantities.getOrDefault(item, 0) + 1);
                }
                isSuccess = model.getAm().submitTransactionAndRating(
                        userId, currentTransactionRestaurant, currentTransactionInitialPrice,
                        promoID, currentTransactionFinalPrice, itemQuantities,
                        finalQuality, finalAuthenticity, finalOverall, comments
                );
                if (isSuccess) {
                    JOptionPane.showMessageDialog(view, "Transaction and Rating Submitted! Thank you!");
                    view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                } else {
                    JOptionPane.showMessageDialog(view, "Error: Could not submit transaction.\nCheck console for details.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
                break;

            // ------------------ History Panel component ------------------
            case "VIEW TRANSACTION HISTORY":
                if (userId == null) {
                    JOptionPane.showMessageDialog(view, "Error: You are not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 1. Populate the restaurant filter
                ArrayList<String> restaurantList = model.getAm().getRestaurantNames();
                view.updateHistoryRestaurantFilter(restaurantList);

                // 2. Do an initial search with no filters
                searchHistory(userId); // Use helper

                // 3. Show the panel
                view.getCardLayout().show(view.getMainPanel(), "HISTORY_VIEW");
                break;
            case "SEARCH HISTORY":
                if (userId == null) {
                    JOptionPane.showMessageDialog(view, "Error: You are not logged in.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                searchHistory(userId); // Use helper
                break;
            case "GO BACK HISTORY":
                view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                break;

            // ------------------ BASIC NAVIGATION BUTTONS ------------------
            case "GO BACK", "BACK TO MAIN MENU":
                view.dispose();
                model.getAm().getMenuController().showMenuView();
                break;

            // New: a general action to return to user actions menu
            case "BACK TO USER ACTIONS":
                System.out.println("test");
                view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                break;

            // ------------------ Restaurant Recommendation component ------------------
            case "VIEW RESTAURANT RECOMMENDATION":
                //show panel
                restaurantCardLevel = 0;
                view.switchRestaurantCardPanel("VIEW SEARCH FOOD CULTURE");
                view.populateFoodOriginsScroll(model.getAm().fetchOriginNames());
                view.getCardLayout().show(view.getMainPanel(), "RESTAURANT_RECOMMENDATION");
                break;

            case "SEARCH ORIGIN":
                view.populateFoodOriginsScroll(
                        model.getAm().fetchOriginNames(),
                        view.getOriginSearchBarField()
                        );
                break;

            case "PROCEED RESTAURANT RECOMMENDATION":
                if (restaurantCardLevel > 2)
                    restaurantCardLevel = 0;

                if (restaurantCardLevel == 0) {
                    /// --- MOVE TO SELECT ORIGINS ---
                    //Clear and add all selected origins
                    selectedOrigins.clear();
                    selectedOrigins.addAll(view.getSelectedOrigins());

                    if (selectedOrigins.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Error: Choose at least one", "Submission Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    // move to food event
                    view.populateFoodEventsScroll(model.getAm().fetchFoodEventNames());
                    view.switchRestaurantCardPanel("VIEW SEARCH FOOD EVENT");
                } else if (restaurantCardLevel == 1) {
                    /// --- MOVE TO SELECT EVENTS ---
                    //Clear and add all selected events
                    selectedEvents.clear();
                    selectedEvents.addAll(view.getSelectedEvents());

                    if (selectedEvents.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Error: Choose at least one", "Submission Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    /// --- MOVE TO RESTAURANT RECOMMENDATION ---
                    view.populateRestaurantRecos(
                            model.getAm().fetchRestaurantFromOriginAndEvent(selectedOrigins, selectedEvents)
                            );

                    view.switchRestaurantCardPanel("VIEW RESTAURANT RECOMMENDATION");
                } else if (restaurantCardLevel == 2) {
                    restaurantCardLevel = 0;
                    view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                }

                restaurantCardLevel++;
                break;

            case "SEARCH EVENT":
                view.populateFoodEventsScroll(
                        model.getAm().fetchFoodEventNames(),
                        view.getEventSearchBarField()
                        );
                break;

            case "GO BACK RESTAURANT RECOMMENDATION", "OK RESTAURANT RECOMMENDATION":
                if (restaurantCardLevel < 0)
                    restaurantCardLevel = 0;

                if (restaurantCardLevel == 0) {
                    view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                } else if (restaurantCardLevel == 1) {
                    view.populateFoodOriginsScroll(model.getAm().fetchOriginNames());
                    view.switchRestaurantCardPanel("VIEW SEARCH FOOD CULTURE");
                } else if (restaurantCardLevel == 2) {
                    view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                }

                restaurantCardLevel--;
                break;
        }
    }

    /**
     * Helper function to handle all transaction-creation events.
     */
    private void handleTransactionEvents(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "RESTAURANT_SELECTED":
                String selectedRestaurant = (String) view.getRestaurantComboBox().getSelectedItem();
                if (selectedRestaurant == null || selectedRestaurant.equals("[Select One]")) {
                    view.updateFoodItemComboBox(new ArrayList<>());
                } else {
                    ArrayList<FoodItem> menuItems = model.getAm().getFoodMenu(selectedRestaurant);
                    view.updateFoodItemComboBox(menuItems);
                }
                break;

            case "RES_RESTAURANT_SELECTED":
                String selectedResRestaurant = (String) view.getResRestaurantComboBox().getSelectedItem();
                if (selectedResRestaurant == null || selectedResRestaurant.equals("[Select One]")) {
                    view.updateResFoodItemComboBox(new ArrayList<>());
                } else {
                    ArrayList<FoodItem> menuItems = model.getAm().getFoodMenu(selectedResRestaurant);
                    view.updateResFoodItemComboBox(menuItems);
                }
                break;

            case "CREATE TRANSACTION":
                cart.clear();
                view.getTransactionCartArea().setText("");
                view.getInitialPriceLabel().setText("P0.00");
                view.getPromoLabel().setText("-P0.00");
                view.getPromoField().setText("");
                view.getFinalPriceLabel().setText("P0.00");
                restaurantList = model.getAm().getRestaurantNames();
                view.updateRestaurantComboBox(restaurantList);
                view.updateFoodItemComboBox(new ArrayList<>());
                view.getRestaurantComboBox().setSelectedIndex(0);
                view.getCardLayout().show(view.getMainPanel(), "TRANSACTION_CREATE");
                break;

            case "RESERVE TRANSACTION":
                updateView();
                cart.clear();
                view.getReservationCartArea().setText("");
                view.getReservationPriceLabel().setText("P0.00");
                restaurantList = model.getAm().getRestaurantNames();
                view.updateResRestaurantComboBox(restaurantList);
                view.updateResFoodItemComboBox(new ArrayList<>());
                view.getResRestaurantComboBox().setSelectedIndex(0);
                view.getCardLayout().show(view.getMainPanel(), "RESERVATION_CREATE");
                break;

            case "USE RESERVATION":
                System.out.println("USE RESERVATION");
                break;

            case "ADD ITEM":
                selectedItem = (FoodItem) view.getFoodItemComboBox().getSelectedItem();
                if (selectedItem == null) return;
                cart.add(selectedItem);
                updateCartView("ADD ITEM");
                break;

            case "ADD RESERVE ITEM":
                selectedItem = (FoodItem) view.getResFoodItemComboBox().getSelectedItem();
                if (selectedItem == null) return;
                cart.add(selectedItem);
                updateCartView("ADD RESERVE ITEM");
                break;

            case "CALCULATE TOTAL":
                initialPrice = 0.0;
                for (FoodItem item : cart) {
                    initialPrice += item.getPrice();
                }

                double promoPercent = 0.0;
                promoID = null;
                String codeInput = view.getPromoInput();
                if (!codeInput.isEmpty()) {
                    FoodPromo promo = model.getAm().getFoodPromo(
                            view.getPromoInput(), 
                            (String) view.getRestaurantComboBox().getSelectedItem()
                            );
                    if (promo == null) {
                        JOptionPane.showMessageDialog(view, "Invalid promo! Promo might be for another restaurant or entered incorrectly", "Input Error", JOptionPane.WARNING_MESSAGE);
                        break;
                    } else {
                        promoPercent = promo.getPercentageOff();
                        promoID = promo.getId();
                    }
                }


                double promoAmount = initialPrice * promoPercent;
                double finalPrice = initialPrice - promoAmount;
                currentTransactionInitialPrice = initialPrice;
                currentTransactionFinalPrice = finalPrice;
                view.getInitialPriceLabel().setText(String.format("P%.2f", initialPrice));
                view.getPromoLabel().setText(String.format("-P%.2f", promoAmount));
                view.getFinalPriceLabel().setText(String.format("P%.2f", finalPrice));
                break;

            case "CALCULATE RESERVATION TOTAL":
                initialPrice = 0.0;
                for (FoodItem item : cart) {
                    initialPrice += item.getPrice();
                }
                currentReservationPrice = initialPrice;
                view.getReservationPriceLabel().setText(String.format("P%.2f", initialPrice));
                break;

            case "PROCEED TO RATING":
                currentTransactionRestaurant = (String) view.getRestaurantComboBox().getSelectedItem();
                if (currentTransactionRestaurant == null || currentTransactionRestaurant.equals("[Select One]")) {
                    JOptionPane.showMessageDialog(view, "Please select a valid restaurant.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (currentTransactionFinalPrice == 0.0 || cart.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Please add items and calculate the total.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                view.getQualityRatingComboBox().setSelectedIndex(0);
                view.getAuthenticityRatingComboBox().setSelectedIndex(0);
                view.getRatingCommentsArea().setText("");
                view.getOverallRatingLabel().setText("N/A");
                view.getCardLayout().show(view.getMainPanel(), "RATING_MENU");
                break;

            case "CALCULATE_RATING":
                int quality = (int) view.getQualityRatingComboBox().getSelectedItem();
                int authenticity = (int) view.getAuthenticityRatingComboBox().getSelectedItem();
                double average = (quality + authenticity) / 2.0;
                view.getOverallRatingLabel().setText(String.format("%.1f / 5.0", average));
                break;

            case "GO BACK FOOD RATING":
                view.getCardLayout().show(view.getMainPanel(), "TRANSACTION_CREATE");
                break;

            case "GO BACK TRANSACTION", "GO BACK RESERVATION":
                view.getCardLayout().show(view.getMainPanel(), "USER_ACTIONS_MENU");
                break;
        }
    }

    /**
     * Gets filter values from the view, calls the model, and updates the table.
     * @param userId The ID of the logged-in user.
     */
    private void searchHistory(Integer userId) {
        // 1. Get all filter values from the view
        String startDate = view.getFilterStartDate();
        String endDate = view.getFilterEndDate();
        String restaurant = view.getFilterRestaurant();
        String maxPrice = view.getFilterMaxPrice();
        String promo = view.getFilterPromo();

        // 2. Call the model to get the data
        ArrayList<TransactionData> transactions = model.getAm().fetchTransactionHistory(
                userId, startDate, endDate, restaurant, maxPrice, promo
        );

        // 3. Update the view's table with the results
        view.updateHistoryTable(transactions);
    }

    /**
     * Helper method to update the cart display.
     */
    private void updateCartView(String caseName) {
        JTextArea cartArea = switch (caseName) {
            case "ADD ITEM" -> view.getTransactionCartArea();
            case "ADD RESERVE ITEM" -> view.getReservationCartArea();
            default -> null;
        };

        if (cartArea != null) cartArea.setText("");
        else {
            System.err.println("Error: No cart exists to update view.");
            return;
        }

        if (cart.isEmpty()) cartArea.setText("Cart is empty.");
        else {
            for (FoodItem item : cart) {
                cartArea.append(item.toString() + "\n");
            }
        }
    }

    /**
     * Refreshes all panels
     */
    private void updateView() {
        view.refreshPanels();
        view.setActionListener(this);
    }
}
