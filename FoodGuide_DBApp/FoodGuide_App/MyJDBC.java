import java.sql.*;

public class MyJDBC {
    // Alter information according to computer host
    private static final String DB_URL = "jdbc:mysql://localhost:3306/food_culture";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static void main(String[] args) {
        try {
            // Establish Connection
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // Create Statement
            Statement statement = connection.createStatement();

            // Execute Query: ResultSet gets results from an executed query as a collection of sets
            ResultSet resultSet = statement.executeQuery("SELECT * FROM food_address");

            // Process Results: resultSet.next() returns the next row from the query
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getString("street") + " is in " + resultSet.getString("city")
                );
            }

            // Close before termination
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.getLogger(MyJDBC.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
        }
    }
}
