import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FoodDataBase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/food_culture";
    private static final String USER = "root";
    private static final String PASS = "password";

    private static final String USER_TABLE = "food_user";
    private static final String USER_ID_COL = "food_user_id";
    private static final String USER_NAME_COL = "food_user_name";
    private static final String USER_EMAIL_COL = "food_user_email";

    private static final String RESTAURANT_TABLE = "restaurant";
    private static final String RESTAURANT_ID_COL = "restaurant_id";
    private static final String RESTAURANT_NAME_COL = "restaurant_name";

    private static final String FOOD_MENU_TABLE = "food_menu";
    private static final String FOOD_ALIAS_COL = "food_alias";
    private static final String PRICE_COL = "price";

    private static final String USER_INSERT_QUERY =
            "INSERT INTO " + USER_TABLE + " (" + USER_NAME_COL + ", " + USER_EMAIL_COL + ") VALUES (?, ?)";

    private static final String USER_LOGIN_QUERY =
            "SELECT " + USER_ID_COL + " FROM " + USER_TABLE + " WHERE " + USER_NAME_COL + " = ? AND " + USER_EMAIL_COL + " = ?";

    private static final String RESTAURANT_NAMES_QUERY =
            "SELECT " + RESTAURANT_NAME_COL + " FROM " + RESTAURANT_TABLE + " ORDER BY " + RESTAURANT_NAME_COL + " ASC";

    private static final String FOOD_MENU_QUERY =
            "SELECT fm." + FOOD_ALIAS_COL + ", fm." + PRICE_COL + " " +
                    "FROM " + FOOD_MENU_TABLE + " fm " +
                    "JOIN " + RESTAURANT_TABLE + " r ON fm." + RESTAURANT_ID_COL + " = r." + RESTAURANT_ID_COL + " " +
                    "WHERE r." + RESTAURANT_NAME_COL + " = ? " +
                    "ORDER BY fm." + FOOD_ALIAS_COL + " ASC";
    /**
     * Attempts to get a connection to the database.
     * @return A Connection object.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
     */
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        // 1. Load the MySQL driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. Return the connection
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Registers a new user in the database.
     * @param username The username to insert.
     * @param email    The email to insert.
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
     * @param username The username to check.
     * @param email    The email to check.
     * @return The user's food_user_id if successful, or null if login fails.
     */
    public Integer loginUser(String username, String email) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(USER_LOGIN_QUERY)) {

            stmt.setString(1, username);
            stmt.setString(2, email);

            try (ResultSet rs = stmt.executeQuery()) {
                // Check if the result set has at least one row
                if (rs.next()) {
                    // Login is successful, return the user ID
                    return rs.getInt(USER_ID_COL);
                } else {
                    // No matching user found
                    return null;
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null; // Return null on database error
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

            // Loop through the result set and add names to the list
            while (rs.next()) {
                restaurantNames.add(rs.getString(RESTAURANT_NAME_COL));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Return an empty list on error
        }
        return restaurantNames;
    }

    /**
     * Fetches the menu items for a specific restaurant.
     * @param restaurantName The name of the restaurant to get the menu for.
     * @return An ArrayList of FoodItem objects.
     */
    public ArrayList<FoodItem> getFoodMenuForRestaurant(String restaurantName) {
        ArrayList<FoodItem> menuItems = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(FOOD_MENU_QUERY)) {

            // Set the restaurant name parameter in the query
            stmt.setString(1, restaurantName);

            try (ResultSet rs = stmt.executeQuery()) {
                // Loop through the results and create FoodItem objects
                while (rs.next()) {
                    String foodAlias = rs.getString(FOOD_ALIAS_COL);
                    double price = rs.getDouble(PRICE_COL);
                    menuItems.add(new FoodItem(foodAlias, price));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Return an empty list on error
        }
        return menuItems;
    }

}
