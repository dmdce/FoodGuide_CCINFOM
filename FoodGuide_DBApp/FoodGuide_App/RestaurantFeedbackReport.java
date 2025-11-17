import java.util.ArrayList;

/**
 * Class: RestaurantFeedbackReport
 * A Data Transfer Object (DTO) to hold the complete set of feedback
 * for a single restaurant: its rating, menu popularity, and comments.
 */
public class RestaurantFeedbackReport {
    private double overallRating;
    private ArrayList<MenuItemPopularityData> menuPopularity;
    private ArrayList<String> comments;

    public RestaurantFeedbackReport(double overallRating, ArrayList<MenuItemPopularityData> menuPopularity, ArrayList<String> comments) {
        this.overallRating = overallRating;
        this.menuPopularity = menuPopularity;
        this.comments = comments;
    }

    // --- Getters ---

    public double getOverallRating() {
        return overallRating;
    }

    public ArrayList<MenuItemPopularityData> getMenuPopularity() {
        return menuPopularity;
    }

    public ArrayList<String> getComments() {
        return comments;
    }
}