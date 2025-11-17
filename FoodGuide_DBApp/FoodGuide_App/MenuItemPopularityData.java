/**
 * Class: MenuItemPopularityData
 * A Data Transfer Object (DTO) to hold a food menu item's name
 * and its total quantity ordered for the feedback report.
 */
public class MenuItemPopularityData {
    private String foodAlias;
    private int totalOrdered;

    public MenuItemPopularityData(String foodAlias, int totalOrdered) {
        this.foodAlias = foodAlias;
        this.totalOrdered = totalOrdered;
    }

    // --- Getters ---

    public String getFoodAlias() {
        return foodAlias;
    }

    public int getTotalOrdered() {
        return totalOrdered;
    }
}