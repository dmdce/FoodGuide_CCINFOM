import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement; // Import Statement
import java.util.ArrayList;
import java.util.HashMap; // Import HashMap
import java.util.Map; // Import Map

public class FoodDataBase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/food_culture";
    private static final String USER = "root";
    private static final String PASS = "pass";

    // --- USER TABLE ---
    private static final String USER_TABLE = "food_user";
    private static final String USER_ID_COL = "food_user_id";
    private static final String USER_NAME_COL = "food_user_name";
    private static final String USER_EMAIL_COL = "food_user_email";
    private static final String USER_INSERT_QUERY =
            "INSERT INTO " + USER_TABLE + " (" + USER_NAME_COL + ", " + USER_EMAIL_COL + ") VALUES (?, ?)";
    private static final String USER_LOGIN_QUERY =
            "SELECT " + USER_ID_COL + " FROM " + USER_TABLE + " WHERE " + USER_NAME_COL + " = ? AND " + USER_EMAIL_COL + " = ?";

    // --- RESTAURANT TABLE ---
    private static final String RESTAURANT_TABLE = "restaurant";
    private static final String RESTAURANT_ID_COL = "restaurant_id"; // Added ID column
    private static final String RESTAURANT_NAME_COL = "restaurant_name";
    private static final String RESTAURANT_NAMES_QUERY =
            "SELECT " + RESTAURANT_NAME_COL + " FROM " + RESTAURANT_TABLE + " ORDER BY " + RESTAURANT_NAME_COL + " ASC";
    // --- NEW: Query to get restaurant ID from name ---
    private static final String RESTAURANT_ID_QUERY =
            "SELECT " + RESTAURANT_ID_COL + " FROM " + RESTAURANT_TABLE + " WHERE " + RESTAURANT_NAME_COL + " = ?";

    // --- FOOD_MENU TABLE ---
    private static final String FOOD_MENU_TABLE = "food_menu";
    private static final String FOOD_MENU_ID_COL = "food_menu_id";
    private static final String FOOD_ID_COL = "food_id";
    private static final String FOOD_ALIAS_COL = "food_alias";
    private static final String PRICE_COL = "price";
    // --- UPDATED: Query now joins with restaurant table ---
    private static final String FOOD_MENU_QUERY =
            "SELECT T1." + FOOD_ALIAS_COL + ", T1." + PRICE_COL + " " +
                    "FROM " + FOOD_MENU_TABLE + " T1 " +
                    "JOIN " + RESTAURANT_TABLE + " T2 ON T1." + RESTAURANT_ID_COL + " = T2." + RESTAURANT_ID_COL + " " +
                    "WHERE T2." + RESTAURANT_NAME_COL + " = ?";

    // --- NEW: Query to get Food IDs from menu ---
    private static final String FOOD_MENU_IDS_QUERY =
            "SELECT " + FOOD_MENU_ID_COL + ", " + FOOD_ID_COL + " " +
                    "FROM " + FOOD_MENU_TABLE + " " +
                    "WHERE " + FOOD_ALIAS_COL + " = ? AND " + RESTAURANT_ID_COL + " = ?";

    // --- NEW: TRANSACTION, ORDER, and RATING TABLES & QUERIES ---
    private static final String TRANSACTION_TABLE = "food_transaction";
    private static final String TRANSACTION_INSERT_QUERY =
            "INSERT INTO " + TRANSACTION_TABLE +
                    " (restaurant_name, promo, final_price, initial_price, food_user_id, transaction_date) " +
                    "VALUES (?, ?, ?, ?, ?, NOW())";

    private static final String ORDER_TABLE = "food_order";
    private static final String ORDER_INSERT_QUERY =
            "INSERT INTO " + ORDER_TABLE +
                    " (food_transaction_id, food_menu_id, quantity, food_id) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String RATING_TABLE = "food_rating";
    private static final String RATING_INSERT_QUERY =
            "INSERT INTO " + RATING_TABLE +
                    " (food_transaction_id, restaurant_id, suggestion, quality, authenticity, overall_rating) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    // --- END OF NEW CONSTANTS ---


    /**
     * Attempts to get a connection to the database.
     * @return A Connection object.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
     */
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Registers a new user in the database.
     * @return true if registration was successful, false otherwise.
     */
    public boolean registerUser(String username, String email) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(USER_INSERT_QUERY)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates a user's login credentials and returns their ID.
     * @return The user's food_user_id if successful, or null if login fails.
     */
    public Integer loginUser(String username, String email) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(USER_LOGIN_QUERY)) {

            stmt.setString(1, username);
            stmt.setString(2, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(USER_ID_COL);
                } else {
                    return null;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fetches a list of all restaurant names from the database.
     * @return An ArrayList of restaurant names.
     */
    public ArrayList<String> getAllRestaurantNames() {
        ArrayList<String> restaurantNames = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(RESTAURANT_NAMES_QUERY);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                restaurantNames.add(rs.getString(RESTAURANT_NAME_COL));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return restaurantNames;
    }

    /**
     * Fetches the food menu for a specific restaurant.
     * @param restaurantName The name of the restaurant.
     * @return An ArrayList of FoodItem objects.
     */
    public ArrayList<FoodItem> getFoodMenuForRestaurant(String restaurantName) {
        ArrayList<FoodItem> menuItems = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(FOOD_MENU_QUERY)) {

            stmt.setString(1, restaurantName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String foodAlias = rs.getString(FOOD_ALIAS_COL);
                    double price = rs.getDouble(PRICE_COL);
                    menuItems.add(new FoodItem(foodAlias, price));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    /**
     * Executes the complete transaction and rating submission as a single
     * atomic database operation.
     * @return true if all inserts were successful, false if anything failed.
     */
    public boolean createFullTransaction(
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
        Connection conn = null;
        try {
            conn = getConnection();
            // --- STEP 1: START DATABASE TRANSACTION ---
            // This ensures all or nothing. If one part fails, all parts are undone.
            conn.setAutoCommit(false);

            // --- STEP 2: Get Restaurant ID ---
            int restaurantId = -1;
            try (PreparedStatement stmt = conn.prepareStatement(RESTAURANT_ID_QUERY)) {
                stmt.setString(1, restaurantName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        restaurantId = rs.getInt(RESTAURANT_ID_COL);
                    } else {
                        // If restaurant isn't found, fail the whole transaction
                        throw new SQLException("Restaurant not found: " + restaurantName);
                    }
                }
            }

            // --- STEP 3: Create food_transaction and get its new ID ---
            int transactionId = -1;

            // Your `promo` column is `decimal(3,2)`, e.g., 0.10.
            // We calculate this from the promo *amount*.
            double promoPercent = (initialPrice > 0) ? (promoAmount / initialPrice) : 0.0;

            try (PreparedStatement stmt = conn.prepareStatement(TRANSACTION_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, restaurantName);
                stmt.setDouble(2, promoPercent); // Storing promo as 0.10 (for 10%)
                stmt.setDouble(3, finalPrice);
                stmt.setDouble(4, initialPrice);
                stmt.setInt(5, userId);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Creating transaction failed, no rows affected.");
                }

                // Get the newly generated food_transaction_id
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transactionId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating transaction failed, no ID obtained.");
                    }
                }
            }

            // --- STEP 4: Loop through cart and create food_order entries ---
            // We'll re-use these prepared statements inside the loop
            try (PreparedStatement orderStmt = conn.prepareStatement(ORDER_INSERT_QUERY);
                 PreparedStatement menuIdStmt = conn.prepareStatement(FOOD_MENU_IDS_QUERY)) {

                for (Map.Entry<FoodItem, Integer> entry : itemQuantities.entrySet()) {
                    FoodItem item = entry.getKey();
                    int quantity = entry.getValue();

                    // Step 4a: Get food_menu_id and food_id for this item
                    menuIdStmt.setString(1, item.getName());
                    menuIdStmt.setInt(2, restaurantId);

                    int foodMenuId = -1;
                    int foodId = -1;
                    try (ResultSet rs = menuIdStmt.executeQuery()) {
                        if (rs.next()) {
                            foodMenuId = rs.getInt(FOOD_MENU_ID_COL);
                            foodId = rs.getInt(FOOD_ID_COL);
                        } else {
                            // If the item isn't found, fail the whole transaction
                            throw new SQLException("Food item not found in menu: " + item.getName());
                        }
                    }

                    // Step 4b: Insert into food_order
                    orderStmt.setInt(1, transactionId);
                    orderStmt.setInt(2, foodMenuId);
                    orderStmt.setInt(3, quantity);
                    orderStmt.setInt(4, foodId);
                    orderStmt.addBatch(); // Add this insert to a batch
                }
                // Execute all food_order inserts at once
                orderStmt.executeBatch();
            }

            // --- STEP 5: Create food_rating entry ---
            try (PreparedStatement stmt = conn.prepareStatement(RATING_INSERT_QUERY)) {
                stmt.setInt(1, transactionId);
                stmt.setInt(2, restaurantId);
                stmt.setString(3, comments);
                stmt.setInt(4, quality);
                stmt.setInt(5, authenticity);
                stmt.setDouble(6, overallRating);
                stmt.executeUpdate();
            }

            // --- STEP 6: All steps succeeded! Commit the transaction ---
            conn.commit();
            return true; // Return success

        } catch (Exception e) {
            // --- STEP 7: Something failed. Roll back all changes ---
            e.printStackTrace();
            if (conn != null) {
                try {
                    System.err.println("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false; // Return failure
        } finally {
            // --- STEP 8: Always restore autocommit and close connection ---
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // --- END OF NEW METHOD ---
}