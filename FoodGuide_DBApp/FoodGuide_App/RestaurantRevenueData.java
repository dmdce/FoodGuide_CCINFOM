/**
 * Class: RestaurantRevenueData
 * A Data Transfer Object (DTO) to hold information about a restaurant's
 * total revenue and transaction count for the admin's report.
 */
public class RestaurantRevenueData {
    private String restaurantName;
    private double totalRevenue;
    private int totalTransactions;

    public RestaurantRevenueData(String restaurantName, double totalRevenue, int totalTransactions) {
        this.restaurantName = restaurantName;
        this.totalRevenue = totalRevenue;
        this.totalTransactions = totalTransactions;
    }

    // --- Getters ---

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }
}