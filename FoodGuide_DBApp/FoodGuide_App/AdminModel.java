import java.awt.*;
import java.util.ArrayList;

/**
 * Class: AdminModel
 * This class represents the model of the Admin.
 * It takes care the of methods needed for the view and controller like handling
 * data storage, retrieval, manipulation, and validation.
 */
public class AdminModel {
    private MenuController menuController;
    private FoodDataBase db;
    /**
     * Constructs an AdminModel.
     *
     * @param menuController The Menu Controller where it handles actions in the menu window.
     */
    public AdminModel(MenuController menuController) {
        this.menuController = menuController;
        this.db = new FoodDataBase();
    }

    /**
     * Getting the menu controller instance.
     *
     * @return the menu controller instance.
     */
    public MenuController getMenuController() {
        return menuController;
    }


    /**
     * Passes the registration request to the database class.
     * @return true if successful, false otherwise.
     */
    public boolean registerNewUser(String username, String email) {
        return db.registerUser(username, email);
    }

    /**
     * Passes the login request to the database class.
     * @param username The username to check.
     * @param email    The email to check.
     * @return The user's ID if successful, or null if login fails.
     */
    public Integer loginCustomer(String username, String email) {
        return db.loginUser(username, email);
    }

    /**
     * Asks the database for a list of all restaurant names.
     * @return An ArrayList of restaurant names.
     */
    public ArrayList<String> getRestaurantNames() {
        return db.getAllRestaurantNames();
    }

    /**
     * Asks the database for the menu of a specific restaurant.
     * @param restaurantName The name of the restaurant.
     * @return An ArrayList of FoodItem objects.
     */
    public ArrayList<FoodItem> getFoodMenu(String restaurantName) {
        return db.getFoodMenuForRestaurant(restaurantName);
    }
}