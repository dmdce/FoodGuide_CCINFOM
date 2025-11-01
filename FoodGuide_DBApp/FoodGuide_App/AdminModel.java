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

    /**
     * Constructs an AdminModel.
     *
     * @param menuController The Menu Controller where it handles actions in the menu window.
     */
    public AdminModel(MenuController menuController) {
        this.menuController = menuController;
    }

    /**
     * Getting the menu controller instance.
     *
     * @return the menu controller instance.
     */
    public MenuController getMenuController() {
        return menuController;
    }
}