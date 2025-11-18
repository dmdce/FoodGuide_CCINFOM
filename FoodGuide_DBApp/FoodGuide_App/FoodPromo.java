/**
 * Class: FoodPromo
 * A Data Transfer Object (DTO) to hold information about a single promo
 * for restaurant recommendations.
 */
public class FoodPromo {
    private int id;
    private int restaurantId;
    private String promoCode;
    private float percentageOff;
    private String description;

    public FoodPromo(int id, int restaurantId, String promoCode, float percentageOff, String description) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.promoCode = promoCode;
        this.percentageOff = percentageOff;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public float getPercentageOff() {
        return percentageOff;
    }

    public String getDescription() {
        return description;
    }
}
