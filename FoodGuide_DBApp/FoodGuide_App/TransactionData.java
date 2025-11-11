import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Class: TransactionData
 * A Data Transfer Object (DTO) to hold information about a single transaction
 * for the history panel.
 */
public class TransactionData {
    private int transactionId;
    private Timestamp transactionDate;
    private String restaurantName;
    private double initialPrice;
    private double promo;
    private double finalPrice;

    public TransactionData(int transactionId, Timestamp transactionDate, String restaurantName, double initialPrice, double promo, double finalPrice) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.restaurantName = restaurantName;
        this.initialPrice = initialPrice;
        this.promo = promo;
        this.finalPrice = finalPrice;
    }

    // --- Getters ---

    public int getTransactionId() {
        return transactionId;
    }

    // Returns the date as a nicely formatted string for the table
    public String getTransactionDate() {
        if (transactionDate == null) {
            return "N/A";
        }
        // Format to "yyyy-MM-dd HH:mm"
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(transactionDate);
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public double getPromo() {
        // Your schema stores promo as a decimal (e.g., 0.10)
        // Let's format it as a percentage for display
        return promo;
    }

    public double getFinalPrice() {
        return finalPrice;
    }
}
