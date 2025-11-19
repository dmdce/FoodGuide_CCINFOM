import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Class: AdminController
 * This class represents the controller of the Admin window which it is in admin view.
 * It takes care the of actions when buttons are pressed in the window.
 */
public class AdminController implements ActionListener {
    private AdminModel model;
    private AdminView view;
    private ArrayList<AdminView.FoodItem> cart;

    private String currentTransactionRestaurant;
    private double currentTransactionFinalPrice;

    public AdminController(AdminModel m, AdminView v) {
        this.model = m;
        this.view = v;
        this.cart = new ArrayList<>();
        view.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {

            // ------------------ Manage Database Panel component ------------------
            case "MANAGE DATABASE":
                view.getCardLayout().show(view.getMainPanel(), "MANAGE_DATABASE_MENU");
                break;

            case "Create User":
                view.getCardLayout().show(view.getMainPanel(), "USER_CREATE");
                break;
            case "Create Promo":
                view.resetCreatePromoCode(model.getRestaurantNames());
                view.getCardLayout().show(view.getMainPanel(), "CREATE_PROMO_CODE_PANEL");
                break;
            case "Log a New Dish": {
                System.out.println("Log a New Dish clicked!");

                // load options for the combo boxes
                ArrayList<String> originNames = model.getOriginNames();
                ArrayList<String> eventNames = model.getFoodEventNames();
                ArrayList<String> restaurantNames = model.getRestaurantNames();

                view.populateLogNewDishCombos(originNames, eventNames, restaurantNames);
                view.getCardLayout().show(view.getMainPanel(), "LOG_NEW_DISH_MENU");
                break;
            }

            // ------------------ SAVE NEW DISH ------------------
            case "SAVE_NEW_DISH": {
                String dishName = view.getDishNameField().getText().trim();
                String priceText = view.getDishPriceField().getText().trim();

                String originName = (String) view.getOriginComboBox().getSelectedItem();
                String eventName = (String) view.getEventComboBox().getSelectedItem();
                String restaurantName = (String) view.getRestaurantComboBox().getSelectedItem();

                // basic validation
                if (dishName.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            view,
                            "Dish name cannot be empty.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                double price;
                try {
                    price = Double.parseDouble(priceText);
                    if (price <= 0) {
                        JOptionPane.showMessageDialog(
                                view,
                                "Price must be a positive number.",
                                "Input Error",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            view,
                            "Price must be a valid number.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                if (originName == null || originName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            view,
                            "Please select an origin.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                if (eventName == null || eventName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            view,
                            "Please select a food event.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                if (restaurantName == null || restaurantName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            view,
                            "Please select a restaurant.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // 🔹 REAL DB CALL HERE
                try {
                    boolean ok = model.addNewDish(dishName, price, restaurantName, originName, eventName);

                    if (ok) {
                        JOptionPane.showMessageDialog(
                                view,
                                "New dish saved to database successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        // clear fields
                        view.getDishNameField().setText("");
                        view.getDishPriceField().setText("");
                    } else {
                        JOptionPane.showMessageDialog(
                                view,
                                "Failed to save dish. Check console for details.",
                                "Database Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            view,
                            "Unexpected error while saving dish:\n" + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                break;
            }

            // ------------------ Register/Create User Panel component ------------------
            case "REGISTER USER": {
                ArrayList<String> input = view.getRegistrationInput();
                String username = input.get(0);
                String email = input.get(1);
                try {
                    if (username.isEmpty() || email.isEmpty()) {
                        JOptionPane.showMessageDialog(view,
                                "Username and/or Email cannot be empty.",
                                "Input Error",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    boolean isSuccess = model.registerNewUser(username, email);

                    if (isSuccess) {
                        JOptionPane.showMessageDialog(view,
                                "User registered successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);

                        view.getCardLayout().show(view.getMainPanel(), "MANAGE_DATABASE_MENU");

                    } else {
                        JOptionPane.showMessageDialog(view,
                                "Error: Could not register user.\nCheck console for details (e.g., duplicate entry).",
                                "Registration Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view,
                            "An unexpected error occurred: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            }

            // ------------------ Register/Create Promo Panel component ------------------
            case "CREATE PROMO":
                ArrayList<String> inputs = view.getCreatePromoFields();
                if (inputs.size() < 3) {
                    JOptionPane.showMessageDialog(view,
                            "Error: You must give a code, percentage, description.",
                            "Promo Code Failed",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
                float percentageOff;
                try {
                    percentageOff = Float.parseFloat(inputs.get(1));
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(view,
                            "Error: Invalid percentage",
                            "Promo Code Failed",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
                if (percentageOff < 0.0001 || percentageOff > 1.0) {
                    JOptionPane.showMessageDialog(view,
                            "Error: Invalid percentage, must be within (0.0001 to 1.0)",
                            "Promo Code Failed",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
                if (model.promoCodeAlreadyExists(inputs.get(0))) {
                    JOptionPane.showMessageDialog(view,
                            "Error: Promo Code already exists!",
                            "Promo Code Failed",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }

                model.createPromoCode(inputs.get(0), percentageOff, inputs.get(2), view.getChosenRestaurantForPromo());
                JOptionPane.showMessageDialog(view,
                        "Promo Code created successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                view.getCardLayout().show(view.getMainPanel(), "MANAGE_DATABASE_MENU");
                break;
            // ------------------ Generate Reports panel component ------------------
            case "GENERATE REPORTS":
                updateView();
                view.getCardLayout().show(view.getMainPanel(), "GENERATE_REPORTS_MENU");
                break;

            case "Registered Users": {
                ArrayList<UserData> users = model.getAllUsers();
                view.updateUserReportTable(users);
                view.getCardLayout().show(view.getMainPanel(), "USER_REPORT_PANEL");
                break;
            }

            case "User Referral Impact":
                System.out.println("User Referral Impact clicked (not implemented yet).");
                break;

            case "Popular Menu Items":
                System.out.println("Popular Menu Items clicked (not implemented yet).");
                break;

            case "Food, Ratings and Feedback": {
                ArrayList<String> restaurantNames = model.getRestaurantNames();
                view.populateFeedbackRestaurantComboBox(restaurantNames);
                view.updateFeedbackReportPanel(null);
                view.getCardLayout().show(view.getMainPanel(), "FEEDBACK_REPORT_PANEL");
                break;
            }

            case "Fetch Feedback Report": {
                String restaurant = view.getFeedbackSelectedRestaurant();
                if (restaurant.equals("[Select One]")) {
                    JOptionPane.showMessageDialog(view, "Please select a restaurant.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                RestaurantFeedbackReport data = model.getFeedbackReport(restaurant);
                view.updateFeedbackReportPanel(data);
                break;
            }

            case "Revenue and Transaction": {
                ArrayList<RestaurantRevenueData> reportData = model.getRestaurantRevenueReport();
                view.updateRevenueReportTable(reportData);
                view.getCardLayout().show(view.getMainPanel(), "REVENUE_REPORT_PANEL");
                break;
            }

            // ------------------ MANAGE FOOD EVENTS AND ORIGIN ------------------
            case "Manage Food Events":
                refreshFoodEventTable();
                view.getCardLayout().show(view.getMainPanel(), "FOOD_EVENT_MENU");
                break;

            case "Manage Origins":
                refreshOriginTable();
                view.getCardLayout().show(view.getMainPanel(), "ORIGIN_MENU");
                break;

            // ------------------ FOOD EVENT MANAGEMENT ------------------
            case "ADD_FOOD_EVENT": {
                String eventName = view.getFoodEventNameField().getText().trim();
                String eventDescription = view.getFoodEventDescriptionArea().getText().trim();

                if (eventName.isEmpty()) {
                    JOptionPane.showMessageDialog(view,
                            "Event name cannot be empty.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    boolean addSuccess = model.addFoodEvent(eventName, eventDescription);
                    if (addSuccess) {
                        JOptionPane.showMessageDialog(view,
                                "Food event added successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        view.getFoodEventNameField().setText("");
                        view.getFoodEventDescriptionArea().setText("");
                        refreshFoodEventTable();
                    } else {
                        JOptionPane.showMessageDialog(view,
                                "Failed to add food event.\n\n" +
                                        "Check console for more details.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view,
                            "Error: " + ex.getMessage() + "\n\nCheck console for details.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                break;
            }

            case "EDIT_FOOD_EVENT": {
                int selectedRow = view.getFoodEventTable().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view,
                            "Please select a food event to edit.",
                            "Selection Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String eventIdStr = (String) view.getFoodEventTableModel().getValueAt(selectedRow, 0);
                int eventId = Integer.parseInt(eventIdStr);
                String newEventName = view.getFoodEventNameField().getText().trim();
                String newEventDescription = view.getFoodEventDescriptionArea().getText().trim();

                if (newEventName.isEmpty()) {
                    JOptionPane.showMessageDialog(view,
                            "Event name cannot be empty.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    boolean updateSuccess = model.updateFoodEvent(eventId, newEventName, newEventDescription);
                    if (updateSuccess) {
                        JOptionPane.showMessageDialog(view,
                                "Food event updated successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        view.getFoodEventNameField().setText("");
                        view.getFoodEventDescriptionArea().setText("");
                        refreshFoodEventTable();
                    } else {
                        JOptionPane.showMessageDialog(view,
                                "Failed to update food event.\n\n" +
                                        "Check console for detailed error messages",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view,
                            "Error: " + ex.getMessage() + "\n\nCheck console for details.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                break;
            }

            case "DELETE_FOOD_EVENT": {
                int deleteRow = view.getFoodEventTable().getSelectedRow();
                if (deleteRow == -1) {
                    JOptionPane.showMessageDialog(view,
                            "Please select a food event to delete.",
                            "Selection Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String deleteEventIdStr = (String) view.getFoodEventTableModel().getValueAt(deleteRow, 0);
                int deleteEventId = Integer.parseInt(deleteEventIdStr);
                String deleteEventName = (String) view.getFoodEventTableModel().getValueAt(deleteRow, 1);

                int confirm = JOptionPane.showConfirmDialog(view,
                        "Are you sure you want to delete '" + deleteEventName + "'?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean deleteSuccess = model.deleteFoodEvent(deleteEventId);
                        if (deleteSuccess) {
                            JOptionPane.showMessageDialog(view,
                                    "Food event deleted successfully!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            view.getFoodEventNameField().setText("");
                            view.getFoodEventDescriptionArea().setText("");
                            refreshFoodEventTable();
                        } else {
                            JOptionPane.showMessageDialog(view,
                                    "Failed to delete food event.\n\n" +
                                            "Check console for detailed error messages",
                                    "Deletion Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view,
                                "Error: " + ex.getMessage() + "\n\nCheck console for details.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
                break;
            }

            case "REFRESH_FOOD_EVENT_TABLE":
                refreshFoodEventTable();
                break;

            // ------------------ ORIGIN MANAGEMENT ------------------
            case "ADD_ORIGIN": {
                String originName = view.getOriginNameField().getText().trim();

                if (originName.isEmpty()) {
                    JOptionPane.showMessageDialog(view,
                            "Origin name cannot be empty.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    boolean addOriginSuccess = model.addOrigin(originName);
                    if (addOriginSuccess) {
                        JOptionPane.showMessageDialog(view,
                                "Origin added successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        view.getOriginNameField().setText("");
                        refreshOriginTable();
                    } else {
                        JOptionPane.showMessageDialog(view,
                                "Failed to add origin.\n\n" +
                                        "Check console for more details.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view,
                            "Error: " + ex.getMessage() + "\n\nCheck console for details.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                break;
            }

            case "EDIT_ORIGIN": {
                int originSelectedRow = view.getOriginTable().getSelectedRow();
                if (originSelectedRow == -1) {
                    JOptionPane.showMessageDialog(view,
                            "Please select an origin to edit.",
                            "Selection Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String originIdStr = (String) view.getOriginTableModel().getValueAt(originSelectedRow, 0);
                int originId = Integer.parseInt(originIdStr);
                String newOriginName = view.getOriginNameField().getText().trim();

                if (newOriginName.isEmpty()) {
                    JOptionPane.showMessageDialog(view,
                            "Origin name cannot be empty.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    boolean updateOriginSuccess = model.updateOrigin(originId, newOriginName);
                    if (updateOriginSuccess) {
                        JOptionPane.showMessageDialog(view,
                                "Origin updated successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        view.getOriginNameField().setText("");
                        refreshOriginTable();
                    } else {
                        JOptionPane.showMessageDialog(view,
                                "Failed to update origin.\n\n" +
                                        "Check console for detailed error messages",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view,
                            "Error: " + ex.getMessage() + "\n\nCheck console for details.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                break;
            }

            case "DELETE_ORIGIN": {
                int originDeleteRow = view.getOriginTable().getSelectedRow();
                if (originDeleteRow == -1) {
                    JOptionPane.showMessageDialog(view,
                            "Please select an origin to delete.",
                            "Selection Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String deleteOriginIdStr = (String) view.getOriginTableModel().getValueAt(originDeleteRow, 0);
                int deleteOriginId = Integer.parseInt(deleteOriginIdStr);
                String deleteOriginName = (String) view.getOriginTableModel().getValueAt(originDeleteRow, 1);

                int originConfirm = JOptionPane.showConfirmDialog(view,
                        "Are you sure you want to delete '" + deleteOriginName + "'?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (originConfirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean deleteOriginSuccess = model.deleteOrigin(deleteOriginId);
                        if (deleteOriginSuccess) {
                            JOptionPane.showMessageDialog(view,
                                    "Origin deleted successfully!",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            view.getOriginNameField().setText("");
                            refreshOriginTable();
                        } else {
                            JOptionPane.showMessageDialog(view,
                                    "Failed to delete origin.\n\n" +
                                            "Check console for detailed error messages",
                                    "Deletion Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view,
                                "Error: " + ex.getMessage() + "\n\nCheck console for details.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
                break;
            }

            case "REFRESH_ORIGIN_TABLE":
                refreshOriginTable();
                break;

            // ------------------ BASIC NAVIGATION BUTTONS ------------------
            case "GO BACK":
            case "GO BACK GENERATE REPORTS":
                view.getCardLayout().show(view.getMainPanel(), "MAIN_MENU");
                break;

            case "GO BACK USER REG":
            case "GO BACK FOOD EVENT":
            case "GO BACK ORIGIN":
            case "GO BACK LOG NEW DISH":
                view.getCardLayout().show(view.getMainPanel(), "MANAGE_DATABASE_MENU");
                break;

            case "GO BACK USER REPORT":
            case "GO BACK FEEDBACK REPORT":
            case "GO BACK REVENUE REPORT":
                view.getCardLayout().show(view.getMainPanel(), "GENERATE_REPORTS_MENU");
                break;

            case "GO BACK TO MAIN":
                view.dispose();
                model.getMenuController().showMenuView();
                break;

            default:
                break;
        }
    }

    private void updateView() {
        view.refreshPanels();
        view.setActionListener(this);
    }

    private void refreshFoodEventTable() {
        DefaultTableModel tableModel = view.getFoodEventTableModel();
        tableModel.setRowCount(0);

        ArrayList<String[]> events = model.getAllFoodEvents();
        for (String[] event : events) {
            tableModel.addRow(event);
        }
    }

    private void refreshOriginTable() {
        DefaultTableModel tableModel = view.getOriginTableModel();
        tableModel.setRowCount(0);

        ArrayList<String[]> origins = model.getAllOrigins();
        for (String[] origin : origins) {
            tableModel.addRow(origin);
        }
    }
}
