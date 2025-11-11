import java.util.Objects;

/**
 * Class: FoodItem
 * This is a Data Transfer Object (DTO) class that represents a food menu item.
 * It holds the item's name (alias) and its price.
 * It replaces the inner class that was previously in AdminView and CustomerView.
 */
public class FoodItem {
    String name;
    double price;

    /**
     * Constructs a new FoodItem.
     * @param name The name (alias) of the food.
     * @param price The price of the food.
     */
    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Gets the name of the food item.
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the food item.
     * @return the price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the string representation of the food item,
     * which is used by the JComboBox to display the item.
     * @return A formatted string with the name and price.
     */
    @Override
    public String toString() {
        return String.format("%s - P%.2f", name, price);
    }

    // --- NEW: Added equals() and hashCode() ---
    // This is CRITICAL for using FoodItem as a key in a HashMap to count quantities.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodItem foodItem = (FoodItem) o;
        // Two FoodItems are the same if they have the same name AND same price
        return Double.compare(foodItem.price, price) == 0 &&
                Objects.equals(name, foodItem.name);
    }

    @Override
    public int hashCode() {
        // Generates a hash code based on both name and price
        return Objects.hash(name, price);
    }
    // --- END OF NEW METHODS ---
}