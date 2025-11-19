import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Class: ReservationData
 * A Data Transfer Object (DTO) to hold information about a single transaction
 * for the history panel.
 */
public class ReservationData {
    private int reservationId;
    private Timestamp reservationDate;
    private String restaurantName;
    private double initialPrice;

    public ReservationData(int reservationId, Timestamp reservationDate, String restaurantName, double initialPrice) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.restaurantName = restaurantName;
        this.initialPrice = initialPrice;
    }

    // --- Getters ---

    public int getReservationId() {
        return reservationId;
    }

    // Returns the date as a nicely formatted string for the table
    public String getReservationDate() {
        if (reservationDate == null) {
            return "N/A";
        }
        // Format to "yyyy-MM-dd HH:mm"
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reservationDate);
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public double getInitialPrice() {
        return initialPrice;
    }
}
