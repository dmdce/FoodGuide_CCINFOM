import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FoodDataBase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/food_culture";
    private static final String USER = "root";
    private static final String PASS = "12345678";

    // --- USER TABLE ---
    private static final String USER_TABLE = "food_user";
    private static final String USER_ID_COL = "food_user_id";
    private static final String USER_NAME_COL = "food_user_name";
    private static final String USER_EMAIL_COL = "food_user_email";
    private static final String CHECK_EMAIL_QUERY =
            "SELECT COUNT(*) FROM " + USER_TABLE + " WHERE " + USER_EMAIL_COL + " = ?";
    private static final String USER_INSERT_QUERY =
            "INSERT INTO " + USER_TABLE + " (" + USER_NAME_COL + ", " + USER_EMAIL_COL + ") VALUES (?, ?)";
    private static final String USER_LOGIN_QUERY =
            "SELECT " + USER_ID_COL + " FROM " + USER_TABLE + " WHERE " + USER_NAME_COL + " = ? AND " + USER_EMAIL_COL + " = ?";

    // --- RESTAURANT TABLE ---
    private static final String RESTAURANT_TABLE = "restaurant";
    private static final String RESTAURANT_ID_COL = "restaurant_id";
    private static final String RESTAURANT_NAME_COL = "restaurant_name";
    private static final String RESTAURANT_DESCRIPTION_COL = "description";
    private static final String RESTAURANT_NUM_OF_VISITS_COL = "num_of_visits";
    private static final String RESTAURANT_TOTAL_RATING_COL = "total_rating";
    private static final String RESTAURANT_NAMES_QUERY =
            "SELECT " + RESTAURANT_NAME_COL + " FROM " + RESTAURANT_TABLE + " ORDER BY " + RESTAURANT_NAME_COL + " ASC";
    private static final String RESTAURANT_ID_QUERY =
            "SELECT " + RESTAURANT_ID_COL + " FROM " + RESTAURANT_TABLE + " WHERE " + RESTAURANT_NAME_COL + " = ?";

    // --- FOOD_MENU TABLE ---
    private static final String FOOD_MENU_TABLE = "food_menu";
    private static final String FOOD_MENU_ID_COL = "food_menu_id";
    private static final String FOOD_ID_COL = "food_id";
    private static final String FOOD_ALIAS_COL = "food_alias";
    private static final String PRICE_COL = "price";
    private static final String FOOD_MENU_QUERY =
            "SELECT T1." + FOOD_ALIAS_COL + ", T1." + PRICE_COL + " " +
                    "FROM " + FOOD_MENU_TABLE + " T1 " +
                    "JOIN " + RESTAURANT_TABLE + " T2 ON T1." + RESTAURANT_ID_COL + " = T2." + RESTAURANT_ID_COL + " " +
                    "WHERE T2." + RESTAURANT_NAME_COL + " = ?";
    private static final String FOOD_MENU_IDS_QUERY =
            "SELECT " + FOOD_MENU_ID_COL + ", " + FOOD_ID_COL + " " +
                    "FROM " + FOOD_MENU_TABLE + " " +
                    "WHERE " + FOOD_ALIAS_COL + " = ? AND " + RESTAURANT_ID_COL + " = ?";

    // --- FOOD TABLE (for inserting new dishes) ---
    private static final String FOOD_TABLE = "food";
    private static final String FOOD_NAME_COL = "food_name";
    private static final String ORIGIN_ID_COL = "origin_id";
    private static final String FOOD_EVENT_ID_COL = "food_event_id";

    // --- TRANSACTION, ORDER, and RATING TABLES ---
    private static final String TRANSACTION_TABLE = "food_transaction";
    // --- NEW: Columns for history query ---
    private static final String TRANSACTION_ID_COL = "food_transaction_id";
    private static final String TRANSACTION_DATE_COL = "transaction_date";
    private static final String TRANSACTION_INITIAL_PRICE_COL = "initial_price";
    private static final String TRANSACTION_PROMO_COL = "promo";
    private static final String TRANSACTION_FINAL_PRICE_COL = "final_price";
    // --- (end new) ---
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

    private static final String ALL_USERS_QUERY =
            "SELECT " + USER_ID_COL + ", " + USER_NAME_COL + ", " + USER_EMAIL_COL +
                    " FROM " + USER_TABLE + " ORDER BY " + USER_ID_COL + " ASC";

    // --- NEW: FOR RESTAURANT RECOMMENDATION
    private static final String FOOD_ADDRESS_ID_COL = "food_address_id";
    private static final String ORIGINS = "origin";
    private static final String ORIGIN_NAME = "name";
    private static final String ALL_ORIGINS_QUERY =
            "SELECT * FROM " + ORIGINS;
    private static final String FOOD_EVENT = "food_event";
    private static final String FOOD_EVENT_NAME = "food_event_name";
    private static final String FOOD_EVENT_NAME_QUERY =
            "SELECT " + FOOD_EVENT_NAME + " FROM " + FOOD_EVENT;

    private static final String REVENUE_REPORT_QUERY =
            "SELECT " +
                    "    r." + RESTAURANT_NAME_COL + ", " +
                    "    COALESCE(SUM(ft." + TRANSACTION_FINAL_PRICE_COL + "), 0) AS total_revenue, " +
                    "    COUNT(ft." + TRANSACTION_ID_COL + ") AS total_transactions " +
                    "FROM " +
                    "    " + RESTAURANT_TABLE + " r " +
                    "LEFT JOIN " +
                    "    " + TRANSACTION_TABLE + " ft ON r." + RESTAURANT_NAME_COL + " = ft." + RESTAURANT_NAME_COL + " " +
                    "GROUP BY " +
                    "    r." + RESTAURANT_NAME_COL + " " +
                    "ORDER BY " +
                    "    total_revenue DESC, total_transactions DESC";

    private static final String RATING_OVERALL_COL = "overall_rating";
    private static final String RATING_SUGGESTION_COL = "suggestion";

    private static final String OVERALL_RATING_QUERY =
            "SELECT AVG(fr." + RATING_OVERALL_COL + ") AS avg_rating " +
                    "FROM " + RATING_TABLE + " fr " +
                    "JOIN " + RESTAURANT_TABLE + " r ON fr." + RESTAURANT_ID_COL + " = r." + RESTAURANT_ID_COL + " " +
                    "WHERE r." + RESTAURANT_NAME_COL + " = ?";

    private static final String MENU_POPULARITY_QUERY =
            "SELECT " +
                    "    fm." + FOOD_ALIAS_COL + ", " +
                    "    COALESCE(SUM(fo.quantity), 0) AS total_ordered " +
                    "FROM " +
                    "    " + FOOD_MENU_TABLE + " fm " +
                    "JOIN " +
                    "    " + RESTAURANT_TABLE + " r ON fm." + RESTAURANT_ID_COL + " = r." + RESTAURANT_ID_COL + " " +
                    "LEFT JOIN " +
                    "    " + ORDER_TABLE + " fo ON fm." + FOOD_MENU_ID_COL + " = fo." + FOOD_MENU_ID_COL + " " +
                    "WHERE " +
                    "    r." + RESTAURANT_NAME_COL + " = ? " +
                    "GROUP BY " +
                    "    fm." + FOOD_MENU_ID_COL + ", fm." + FOOD_ALIAS_COL + " " +
                    "ORDER BY " +
                    "    total_ordered DESC";

    private static final String COMMENTS_QUERY =
            "SELECT fr." + RATING_SUGGESTION_COL + " " +
                    "FROM " + RATING_TABLE + " fr " +
                    "JOIN " + RESTAURANT_TABLE + " r ON fr." + RESTAURANT_ID_COL + " = r." + RESTAURANT_ID_COL + " " +
                    "WHERE r." + RESTAURANT_NAME_COL + " = ? " +
                    "AND fr." + RATING_SUGGESTION_COL + " IS NOT NULL " + // Ignore nulls
                    "AND fr." + RATING_SUGGESTION_COL + " != '' " + // Ignore empty strings
                    "ORDER BY " +
                    "    fr.food_rating_id DESC";

    /**
     * Attempts to get a connection to the database.
     * (Unchanged)
     */
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Registers a new user in the database.
     * (Unchanged)
     */
    public boolean registerUser(String username, String email) {
        // First check if email already exists
        if (emailExists(email)) {
            System.err.println("Registration failed: Email already exists.");
            return false;
        }

        // Otherwise, proceed with registration
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

    private boolean emailExists(String email) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(CHECK_EMAIL_QUERY)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Validates a user's login credentials and returns their ID.
     * (Unchanged)
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
     * (Unchanged)
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
     * (Unchanged)
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
     * Fetches a report of all restaurants, their total revenue, and transaction counts,
     * sorted by revenue then transaction count.
     * @return An ArrayList of RestaurantRevenueData objects.
     */
    public ArrayList<RestaurantRevenueData> fetchRestaurantRevenueReport() {
        ArrayList<RestaurantRevenueData> reportData = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(REVENUE_REPORT_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reportData.add(new RestaurantRevenueData(
                        rs.getString(RESTAURANT_NAME_COL),
                        rs.getDouble("total_revenue"),
                        rs.getInt("total_transactions")
                ));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return reportData;
    }

    /**
     * Executes the complete transaction and rating submission
     */
    public boolean createFullTransaction(
            Integer userId, String restaurantName, double initialPrice, double promoAmount,
            double finalPrice, HashMap<FoodItem, Integer> itemQuantities, int quality,
            int authenticity, double overallRating, String comments
    ) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            int restaurantId = -1;
            try (PreparedStatement stmt = conn.prepareStatement(RESTAURANT_ID_QUERY)) {
                stmt.setString(1, restaurantName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        restaurantId = rs.getInt(RESTAURANT_ID_COL);
                    } else {
                        throw new SQLException("Restaurant not found: " + restaurantName);
                    }
                }
            }
            int transactionId = -1;
            double promoPercent = (initialPrice > 0) ? (promoAmount / initialPrice) : 0.0;
            try (PreparedStatement stmt = conn.prepareStatement(TRANSACTION_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, restaurantName);
                stmt.setDouble(2, promoPercent);
                stmt.setDouble(3, finalPrice);
                stmt.setDouble(4, initialPrice);
                stmt.setInt(5, userId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) throw new SQLException("Creating transaction failed, no rows affected.");
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transactionId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating transaction failed, no ID obtained.");
                    }
                }
            }
            try (PreparedStatement orderStmt = conn.prepareStatement(ORDER_INSERT_QUERY);
                 PreparedStatement menuIdStmt = conn.prepareStatement(FOOD_MENU_IDS_QUERY)) {
                for (Map.Entry<FoodItem, Integer> entry : itemQuantities.entrySet()) {
                    FoodItem item = entry.getKey();
                    int quantity = entry.getValue();
                    menuIdStmt.setString(1, item.getName());
                    menuIdStmt.setInt(2, restaurantId);
                    int foodMenuId = -1;
                    int foodId = -1;
                    try (ResultSet rs = menuIdStmt.executeQuery()) {
                        if (rs.next()) {
                            foodMenuId = rs.getInt(FOOD_MENU_ID_COL);
                            foodId = rs.getInt(FOOD_ID_COL);
                        } else {
                            throw new SQLException("Food item not found in menu: " + item.getName());
                        }
                    }
                    orderStmt.setInt(1, transactionId);
                    orderStmt.setInt(2, foodMenuId);
                    orderStmt.setInt(3, quantity);
                    orderStmt.setInt(4, foodId);
                    orderStmt.addBatch();
                }
                orderStmt.executeBatch();
            }
            try (PreparedStatement stmt = conn.prepareStatement(RATING_INSERT_QUERY)) {
                stmt.setInt(1, transactionId);
                stmt.setInt(2, restaurantId);
                stmt.setString(3, comments);
                stmt.setInt(4, quality);
                stmt.setInt(5, authenticity);
                stmt.setDouble(6, overallRating);
                stmt.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
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

    /**
     * Executes the order reservation submission TODO BY DARRYL
     */
    public boolean createReservation(
            Integer userId,
            String restaurantName,
            double initialPrice,
            HashMap<FoodItem, Integer> itemQuantities
    ) {
        System.out.println("Congratulations, you managed to print this message!");
        return false;
    }

    /**
     * Searches the database for transactions matching the given filters.
     * @return An ArrayList of TransactionData objects.
     */
    public ArrayList<TransactionData> getTransactionHistory(
            Integer userId,
            String startDate,
            String endDate,
            String restaurantName,
            String maxPrice,
            String promo
    ) {
        ArrayList<TransactionData> transactions = new ArrayList<>();

        // --- Dynamic Query Building ---
        StringBuilder sql = new StringBuilder(
                "SELECT " + TRANSACTION_ID_COL + ", " + TRANSACTION_DATE_COL + ", " +
                        RESTAURANT_NAME_COL + ", " + TRANSACTION_INITIAL_PRICE_COL + ", " +
                        TRANSACTION_PROMO_COL + ", " + TRANSACTION_FINAL_PRICE_COL + " " +
                        "FROM " + TRANSACTION_TABLE + " " +
                        "WHERE " + USER_ID_COL + " = ? "
        );

        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(userId);

        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append(" AND " + TRANSACTION_DATE_COL + " >= ?");
            parameters.add(startDate);
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND " + TRANSACTION_DATE_COL + " <= ?");
            parameters.add(endDate + " 23:59:59");
        }
        if (restaurantName != null && !restaurantName.trim().isEmpty() && !restaurantName.equals("[All]")) {
            sql.append(" AND " + RESTAURANT_NAME_COL + " = ?");
            parameters.add(restaurantName);
        }
        if (maxPrice != null && !maxPrice.trim().isEmpty()) {
            try {
                double price = Double.parseDouble(maxPrice);
                sql.append(" AND " + TRANSACTION_FINAL_PRICE_COL + " <= ?");
                parameters.add(price);
            } catch (NumberFormatException e) {
                System.err.println("Invalid max price format, ignoring filter: " + maxPrice);
            }
        }
        if (promo != null && !promo.trim().isEmpty()) {
            try {
                double promoVal = Double.parseDouble(promo);
                sql.append(" AND " + TRANSACTION_PROMO_COL + " = ?");
                parameters.add(promoVal);
            } catch (NumberFormatException e) {
                System.err.println("Invalid promo format, ignoring filter: " + promo);
            }
        }

        sql.append(" ORDER BY " + TRANSACTION_DATE_COL + " DESC");

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            for (Object param : parameters) {
                stmt.setObject(paramIndex++, param);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new TransactionData(
                            rs.getInt(TRANSACTION_ID_COL),
                            rs.getTimestamp(TRANSACTION_DATE_COL),
                            rs.getString(RESTAURANT_NAME_COL),
                            rs.getDouble(TRANSACTION_INITIAL_PRICE_COL),
                            rs.getDouble(TRANSACTION_PROMO_COL),
                            rs.getDouble(TRANSACTION_FINAL_PRICE_COL)
                    ));
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    /**
     * Fetches a list of all registered users from the database.
     * @return An ArrayList of UserData objects.
     */
    public ArrayList<UserData> fetchAllUsers() {
        ArrayList<UserData> users = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(ALL_USERS_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new UserData(
                        rs.getInt(USER_ID_COL),
                        rs.getString(USER_NAME_COL),
                        rs.getString(USER_EMAIL_COL)
                ));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Fetches a list of all food origins (names only) from the database.
     * Used by the recommendation feature.
     */
    public ArrayList<String> fetchOriginNames() {
        ArrayList<String> origins = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(ALL_ORIGINS_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                origins.add(rs.getString(ORIGIN_NAME));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return origins;
    }

    /**
     * Fetches a list of all food event names (names only) from the database.
     * Used by the recommendation feature.
     */
    public ArrayList<String> fetchFoodEventNames() {
        ArrayList<String> events = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(FOOD_EVENT_NAME_QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                events.add(rs.getString(FOOD_EVENT_NAME));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return events;
    }

    // --------------------------------------------------------------------
    // NEW: insertNewDish for Admin "Log a New Dish"
    // --------------------------------------------------------------------

    /**
     * Inserts a brand new dish into the database:
     *  1. Finds or creates origin and food_event
     *  2. Inserts into food table
     *  3. Inserts into food_menu for the selected restaurant with alias + price
     *
     * @param foodAlias      Dish name / alias
     * @param price          Price for this restaurant
     * @param restaurantName Restaurant where this dish belongs
     * @param originName     Origin name (can be null/empty)
     * @param eventName      Food event name (can be null/empty)
     * @return true if everything was inserted successfully, false otherwise
     */
    public boolean insertNewDish(String foodAlias,
                                 double price,
                                 String restaurantName,
                                 String originName,
                                 String eventName) {
        Connection conn = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // 1. Get restaurant_id from restaurantName
            int restaurantId;
            try (PreparedStatement restStmt = conn.prepareStatement(RESTAURANT_ID_QUERY)) {
                restStmt.setString(1, restaurantName);
                try (ResultSet rs = restStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Restaurant not found: " + restaurantName);
                    }
                    restaurantId = rs.getInt(RESTAURANT_ID_COL);
                }
            }

            // 2. Get or create origin_id
            Integer originId = null;
            if (originName != null && !originName.trim().isEmpty()) {
                String findOriginSql = "SELECT origin_id FROM origin WHERE name = ?";
                try (PreparedStatement findOrigin = conn.prepareStatement(findOriginSql)) {
                    findOrigin.setString(1, originName);
                    try (ResultSet rs = findOrigin.executeQuery()) {
                        if (rs.next()) {
                            originId = rs.getInt("origin_id");
                        }
                    }
                }

                if (originId == null) {
                    String insertOriginSql = "INSERT INTO origin (name) VALUES (?)";
                    try (PreparedStatement insOrigin = conn.prepareStatement(insertOriginSql, Statement.RETURN_GENERATED_KEYS)) {
                        insOrigin.setString(1, originName);
                        insOrigin.executeUpdate();
                        try (ResultSet keys = insOrigin.getGeneratedKeys()) {
                            if (keys.next()) {
                                originId = keys.getInt(1);
                            }
                        }
                    }
                }
            }

            // 3. Get or create food_event_id
            Integer eventId = null;
            if (eventName != null && !eventName.trim().isEmpty()) {
                String findEventSql = "SELECT food_event_id FROM food_event WHERE food_event_name = ?";
                try (PreparedStatement findEvent = conn.prepareStatement(findEventSql)) {
                    findEvent.setString(1, eventName);
                    try (ResultSet rs = findEvent.executeQuery()) {
                        if (rs.next()) {
                            eventId = rs.getInt("food_event_id");
                        }
                    }
                }

                if (eventId == null) {
                    String insertEventSql = "INSERT INTO food_event (food_event_name, description) VALUES (?, NULL)";
                    try (PreparedStatement insEvent = conn.prepareStatement(insertEventSql, Statement.RETURN_GENERATED_KEYS)) {
                        insEvent.setString(1, eventName);
                        insEvent.executeUpdate();
                        try (ResultSet keys = insEvent.getGeneratedKeys()) {
                            if (keys.next()) {
                                eventId = keys.getInt(1);
                            }
                        }
                    }
                }
            }

            // 4. Insert into food table
            int foodId;
            String insertFoodSql =
                    "INSERT INTO " + FOOD_TABLE +
                            " (" + FOOD_NAME_COL + ", " + ORIGIN_ID_COL + ", " + FOOD_EVENT_ID_COL + ") " +
                            "VALUES (?, ?, ?)";

            try (PreparedStatement foodStmt = conn.prepareStatement(insertFoodSql, Statement.RETURN_GENERATED_KEYS)) {
                foodStmt.setString(1, foodAlias);

                if (originId == null) {
                    foodStmt.setNull(2, java.sql.Types.INTEGER);
                } else {
                    foodStmt.setInt(2, originId);
                }

                if (eventId == null) {
                    foodStmt.setNull(3, java.sql.Types.INTEGER);
                } else {
                    foodStmt.setInt(3, eventId);
                }

                int rows = foodStmt.executeUpdate();
                if (rows == 0) {
                    throw new SQLException("Inserting food failed, no rows affected.");
                }

                try (ResultSet keys = foodStmt.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("Inserting food failed, no ID obtained.");
                    }
                    foodId = keys.getInt(1);
                }
            }

            // 5. Insert into food_menu for this restaurant
            String insertMenuSql =
                    "INSERT INTO " + FOOD_MENU_TABLE +
                            " (" + FOOD_ID_COL + ", " + RESTAURANT_ID_COL + ", " + FOOD_ALIAS_COL + ", " + PRICE_COL + ") " +
                            "VALUES (?, ?, ?, ?)";

            try (PreparedStatement menuStmt = conn.prepareStatement(insertMenuSql)) {
                menuStmt.setInt(1, foodId);
                menuStmt.setInt(2, restaurantId);
                menuStmt.setString(3, foodAlias);
                menuStmt.setDouble(4, price);
                menuStmt.executeUpdate();
            }

            conn.commit();
            System.out.println("New dish inserted: " + foodAlias + " @ " + restaurantName);
            return true;

        } catch (Exception e) {
            System.err.println("Error in insertNewDish: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
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

    // --------------------------------------------------------------------
    // 💡 YOUR NEW METHODS: full CRUD for food_event and origin
    // --------------------------------------------------------------------

    /**
     * Gets all food events from the MySQL database.
     * @return ArrayList of String arrays containing [id, name, description]
     */
    public ArrayList<String[]> getAllFoodEvents() {
        ArrayList<String[]> events = new ArrayList<>();
        String sql = "SELECT food_event_id, food_event_name, description FROM food_event ORDER BY food_event_id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] event = new String[3];
                event[0] = String.valueOf(rs.getInt("food_event_id"));
                event[1] = rs.getString("food_event_name");
                String desc = rs.getString("description");
                event[2] = (desc != null) ? desc : "";
                events.add(event);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQL Error getting food events: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error getting food events: " + e.getMessage());
            e.printStackTrace();
        }

        return events;
    }

    /**
     * Adds a new food event to the MySQL database.
     * @param eventName The name of the food event.
     * @param description The description of the food event.
     * @return true if insertion was successful, false otherwise.
     */
    public boolean addFoodEvent(String eventName, String description) {
        String sql = "INSERT INTO food_event (food_event_name, description) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eventName);
            if (description == null || description.trim().isEmpty()) {
                stmt.setNull(2, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(2, description);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQL Error adding food event: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error adding food event: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing food event in the MySQL database.
     * @param eventId The ID of the food event to update.
     * @param eventName The new name of the food event.
     * @param description The new description of the food event.
     * @return true if update was successful, false otherwise.
     */
    public boolean updateFoodEvent(int eventId, String eventName, String description) {
        String sql = "UPDATE food_event SET food_event_name = ?, description = ? WHERE food_event_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eventName);
            if (description == null || description.trim().isEmpty()) {
                stmt.setNull(2, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(2, description);
            }
            stmt.setInt(3, eventId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQL Error updating food event: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error updating food event: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a food event from the MySQL database.
     * @param eventId The ID of the food event to delete.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteFoodEvent(int eventId) {
        String deleteSql = "DELETE FROM food_event WHERE food_event_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            deleteStmt.setInt(1, eventId);
            int rowsAffected = deleteStmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQL Error deleting food event: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error deleting food event: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets all origins from the MySQL database.
     * @return ArrayList of String arrays containing [id, name]
     */
    public ArrayList<String[]> getAllOrigins() {
        ArrayList<String[]> origins = new ArrayList<>();
        String sql = "SELECT origin_id, name FROM origin ORDER BY origin_id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String[] origin = new String[2];
                origin[0] = String.valueOf(rs.getInt("origin_id"));
                origin[1] = rs.getString("name");
                origins.add(origin);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQL Error getting origins: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error getting origins: " + e.getMessage());
            e.printStackTrace();
        }

        return origins;
    }

    /**
     * Adds a new origin to the MySQL database.
     * @param originName The name of the origin (place/country).
     * @return true if insertion was successful, false otherwise.
     */
    public boolean addOrigin(String originName) {
        String sql = "INSERT INTO origin (name) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, originName);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQL Error adding origin: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error adding origin: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing origin in the MySQL database.
     * @param originId The ID of the origin to update.
     * @param originName The new name of the origin.
     * @return true if update was successful, false otherwise.
     */
    public boolean updateOrigin(int originId, String originName) {
        String sql = "UPDATE origin SET name = ? WHERE origin_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, originName);
            stmt.setInt(2, originId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQL Error updating origin: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error updating origin: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an origin from the MySQL database.
     * @param originId The ID of the origin to delete.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteOrigin(int originId) {
        String deleteSql = "DELETE FROM origin WHERE origin_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            deleteStmt.setInt(1, originId);
            int rowsAffected = deleteStmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("SQL Error deleting origin: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error deleting origin: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // --------------------------------------------------------------------
    // END of your added CRUD methods
    // --------------------------------------------------------------------

    public ArrayList<RestaurantData> fetchRestaurantFromOriginAndEvent(
            ArrayList<String> origins,
            ArrayList<String> events
    ) {
        ArrayList<RestaurantData> restaurants = new ArrayList<>();

        String originsList = origins.stream()
                .map(o -> "'" + o + "'")
                .collect(Collectors.joining(", ", "(", ")"));

        String eventsList = events.stream()
                .map(e -> "'" + e + "'")
                .collect(Collectors.joining(", ", "(", ")"));

        final String QUERY =
                "SELECT DISTINCT r.* FROM restaurant r " +
                        "JOIN food_menu fm ON fm.restaurant_id = r.restaurant_id " +
                        "JOIN food f ON f.food_id = fm.food_id " +
                        "LEFT JOIN origin o ON f.origin_id = o.origin_id " +
                        "LEFT JOIN food_event fe ON f.food_event_id = fe.food_event_id " +
                        "WHERE o.name IN " + originsList + " " +
                        "AND fe.food_event_name IN " + eventsList;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                restaurants.add(new RestaurantData(
                        rs.getInt(RESTAURANT_ID_COL),
                        rs.getInt(FOOD_ADDRESS_ID_COL),
                        rs.getString(RESTAURANT_NAME_COL),
                        rs.getString(RESTAURANT_DESCRIPTION_COL),
                        rs.getInt(RESTAURANT_NUM_OF_VISITS_COL),
                        rs.getFloat(RESTAURANT_TOTAL_RATING_COL)
                ));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    /**
     * Fetches a complete feedback report for a single restaurant.
     * @param restaurantName The name of the restaurant to query.
     * @return A RestaurantFeedbackReport DTO containing the rating, menu popularity, and comments.
     */
    public RestaurantFeedbackReport fetchFeedbackReport(String restaurantName) {
        double overallRating = 0.0;
        ArrayList<MenuItemPopularityData> menuPopularity = new ArrayList<>();
        ArrayList<String> comments = new ArrayList<>();

        try (Connection conn = getConnection()) {

            // Query 1: Get Overall Rating
            try (PreparedStatement stmt = conn.prepareStatement(OVERALL_RATING_QUERY)) {
                stmt.setString(1, restaurantName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        overallRating = rs.getDouble("avg_rating");
                    }
                }
            }

            // Query 2: Get Menu Popularity
            try (PreparedStatement stmt = conn.prepareStatement(MENU_POPULARITY_QUERY)) {
                stmt.setString(1, restaurantName);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        menuPopularity.add(new MenuItemPopularityData(
                                rs.getString(FOOD_ALIAS_COL),
                                rs.getInt("total_ordered")
                        ));
                    }
                }
            }

            // Query 3: Get Comments
            try (PreparedStatement stmt = conn.prepareStatement(COMMENTS_QUERY)) {
                stmt.setString(1, restaurantName);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        comments.add(rs.getString(RATING_SUGGESTION_COL));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new RestaurantFeedbackReport(0.0, new ArrayList<>(), new ArrayList<>());
        }

        return new RestaurantFeedbackReport(overallRating, menuPopularity, comments);
    }
}
