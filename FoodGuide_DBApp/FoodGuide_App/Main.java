/**
 *  Class: Main
 *  Controls the flow of the Model-View-Controller-based program.
 */
public class Main {
    public static void main(String[] args) {
        MenuView view = new MenuView();
        new MenuController(view);
    }
}
