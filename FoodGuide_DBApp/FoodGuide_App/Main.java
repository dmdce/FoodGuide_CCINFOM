/**
 *  Class: Main
 *  Controls the flow of the Model-View-Controller-based program.
 */
public class Main {
    public static void main(String[] args) {
        // MenuModel model = new MenuModel(); // REDUNDANT
        MenuView view = new MenuView();
        new MenuController(view);
    }
}
