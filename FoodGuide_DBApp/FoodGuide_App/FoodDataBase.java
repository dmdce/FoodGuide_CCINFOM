import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FoodDataBase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/food_culture";
    private static final String USER = "root";
    private static final String PASS = "YOUR_OWN_PASSWORD";

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
     *
     * @param username The username to insert.
     * @param email    The email to insert.
     * @return true if registration was successful, false otherwise.
     */
    public boolean registerUser(String username, String email) {

        // --- TODO: FILL THESE IN WITH YOUR TABLE/COLUMN NAMES ---
        String sql = "INSERT INTO food_user (food_user_name, food_user_email) VALUES (?,?)";

        // This 'try-with-resources' block automatically closes the connection
        // and statement, even if an error occurs.
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Bind the parameters to the query ("?" marks)
            // This prevents SQL injection attacks
            stmt.setString(1, username);
            stmt.setString(2, email);

            // Execute the query
            // .executeUpdate() returns the number of rows affected
            int rowsAffected = stmt.executeUpdate();

            // If rowsAffected > 0, the insert was successful
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            // Handle database errors (e.g., user already exists, DB is offline)
            e.printStackTrace(); // Log the full error to the console
            return false;
        }
    }

    // --- You will add other methods here (e.g., getFoodItems(), createTransaction()) ---
}
