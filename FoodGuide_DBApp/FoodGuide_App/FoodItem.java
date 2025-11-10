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
}