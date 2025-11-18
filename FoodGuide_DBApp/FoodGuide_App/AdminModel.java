import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class: AdminModel
 * (Rest of class description...)
 */
public class AdminModel {
    private MenuController menuController;
    private FoodDataBase db;

    /**
     * Constructs an AdminModel.
     * (Unchanged)
     */
    public AdminModel(MenuController menuController) {
        this.menuController = menuController;
        this.db = new FoodDataBase();
    }

    /**
     * Getting the menu controller instance.
     * (Unchanged)
     */
    public MenuController getMenuController() {
        return menuController;
    }


    /**
     * Passes the registration request to the database class.
     * (Unchanged)
     */
    public boolean registerNewUser(String username, String email) {
        return db.registerUser(username, email);
    }

    /**
     * Passes the login request to the database class.
     * (Unchanged)
     */
    public Integer loginCustomer(String username, String email) {
        return db.loginUser(username, email);
    }

    /**
     * Asks the database for a list of all restaurant names.
     * (Unchanged)
     */
    public ArrayList<String> getRestaurantNames() {
        return db.getAllRestaurantNames();
    }

    /**
     * Asks the database for a list of food items for a specific restaurant.
     * (Unchanged)
     */
    public ArrayList<FoodItem> getFoodMenu(String restaurantName) {
        return db.getFoodMenuForRestaurant(restaurantName);
    }

    /**
     * Passes all transaction and rating data to the database layer to be processed.
     */
    public boolean submitTransactionAndRating(
            Integer userId,
            String restaurantName,
            double initialPrice,
            double promoAmount,
            double finalPrice,
            HashMap<FoodItem, Integer> itemQuantities,
            int quality,
            int authenticity,
            double overallRating,
            String comments
    ) {
        return db.createFullTransaction(
                userId, restaurantName, initialPrice, promoAmount, finalPrice,
                itemQuantities, quality, authenticity, overallRating, comments
        );
    }

    /**
     * Passes order reservation data to the database layer to be processed.
     */
    public boolean submitReservation(
            Integer userId,
            String restaurantName,
            double initialPrice,
            HashMap<FoodItem, Integer> itemQuantities
    ) {
        return db.createReservation(userId, restaurantName, initialPrice, itemQuantities);
    }

    /**
     * Asks the database for a filtered list of transactions for a user.
     * @param userId The user's ID.
     * @param startDate Filter for dates on or after this (yyyy-mm-dd).
     * @param endDate Filter for dates on or before this (yyyy-mm-dd).
     * @param restaurantName Filter for a specific restaurant.
     * @param maxPrice Filter for final price less than or equal to this.
     * @param promo Filter for a specific promo value.
     * @return An ArrayList of TransactionData objects.
     */
    public ArrayList<TransactionData> fetchTransactionHistory(
            Integer userId,
            String startDate,
            String endDate,
            String restaurantName,
            String maxPrice,
            String promo
    ) {
        return db.getTransactionHistory(userId, startDate, endDate, restaurantName, maxPrice, promo);
    }


    /**
     * Asks the database for a list of all registered users.
     * @return An ArrayList of UserData objects.
     */
    public ArrayList<UserData> getAllUsers() {
        return db.fetchAllUsers();
    }

    /**
     * Asks the database for origin names
     * @return an ArrayList of String
     */
    public ArrayList<String> fetchOriginNames() {
        return db.fetchOriginNames();
    }

    /**
     * Asks the database for event names
     * @return an ArrayList of String
     */
    public ArrayList<String> fetchFoodEventNames() {
        return db.fetchFoodEventNames();
    }

    public ArrayList<RestaurantData> fetchRestaurantFromOriginAndEvent(ArrayList<String> origins, ArrayList<String> events) {
        return db.fetchRestaurantFromOriginAndEvent(origins, events);
    }

    /**
     * Asks the database for the ranked restaurant revenue report.
     * @return An ArrayList of RestaurantRevenueData objects.
     */
    public ArrayList<RestaurantRevenueData> getRestaurantRevenueReport() {
        return db.fetchRestaurantRevenueReport();
    }

    /**
     * Asks the database for the complete feedback report for a restaurant.
     * @param restaurantName The name of the restaurant.
     * @return A RestaurantFeedbackReport DTO.
     */
    public RestaurantFeedbackReport getFeedbackReport(String restaurantName) {
        return db.fetchFeedbackReport(restaurantName);
    }

    // ------------------------------------------------------------
    // ✅ EXTRA METHODS NEEDED FOR FOOD EVENTS & ORIGINS (CRUD)
    // ------------------------------------------------------------

    /**
     * Gets all food events from the database.
     * @return ArrayList of String arrays containing [id, name, description]
     */
    public ArrayList<String[]> getAllFoodEvents() {
        return db.getAllFoodEvents();
    }

    /**
     * Adds a new food event to the database.
     */
    public boolean addFoodEvent(String eventName, String description) {
        return db.addFoodEvent(eventName, description);
    }

    /**
     * Updates an existing food event in the database.
     */
    public boolean updateFoodEvent(int eventId, String eventName, String description) {
        return db.updateFoodEvent(eventId, eventName, description);
    }

    /**
     * Deletes a food event from the database.
     */
    public boolean deleteFoodEvent(int eventId) {
        return db.deleteFoodEvent(eventId);
    }

    /**
     * Gets all origins from the database.
     * @return ArrayList of String arrays containing [id, name]
     */
    public ArrayList<String[]> getAllOrigins() {
        return db.getAllOrigins();
    }

    /**
     * Adds a new origin to the database.
     */
    public boolean addOrigin(String originName) {
        return db.addOrigin(originName);
    }

    /**
     * Updates an existing origin in the database.
     */
    public boolean updateOrigin(int originId, String originName) {
        return db.updateOrigin(originId, originName);
    }

    /**
     * Deletes an origin from the database.
     */
    public boolean deleteOrigin(int originId) {
        return db.deleteOrigin(originId);
    }
}
