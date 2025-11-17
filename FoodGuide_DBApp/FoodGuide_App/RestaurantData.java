/**
 * Class: RestaurantData
 * A Data Transfer Object (DTO) to hold information about a single restaurant
 * for restaurant recommendations.
 */
public class RestaurantData {
    private int id;
    private int foodAdressId;
    private String restaurantName;
    private String description;
    private int numOfVisits;
    private float totalRating;

    public RestaurantData(int id, int foodAdressId, String restaurantName, String description, int numOfVisits, float totalRating) {
        this.id = id;
        this.foodAdressId = foodAdressId;
        this.restaurantName = restaurantName;
        this.description = description;
        this.numOfVisits = numOfVisits;
        this.totalRating = totalRating;
    }

    public int getId() { return id; }
    public int getFoodAdressId() { return foodAdressId; }
    public String getRestaurantName() { return restaurantName; }
    public String getDescription() { return description; }
    public int getNumOfVisits() { return numOfVisits; }
    public float getTotalRating() { return totalRating; }
}
