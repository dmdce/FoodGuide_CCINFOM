import java.util.*;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class: AdminView
 * This class represents the view of the Admin.
 * It handles the representation of panels in the program for the admin.
 * It waits for signals from the admin controller to show what panels are
 * required to show or hide when an action is done in the program.
 */
public class AdminView extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private JPanel dashboardPanel;

    private ArrayList<JButton> mainMenuButtonList = new ArrayList<JButton>();
    private ArrayList<JButton> simulateButtonList = new ArrayList<JButton>();
    private ArrayList<JButton> dashboardButtonList = new ArrayList<JButton>();

    private JButton backButton = new JButton("GO BACK");
    private JButton backDashboardButton = new JButton("GO BACK");

    /**
     * Constructs the AdminView, initializes all sub-panels,
     * adds them to the CardLayout, and makes the frame visible.
     */
    public AdminView() {
        super("JavaJeep Admin Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(850, 450);

        JPanel mainMenuPanel = createMainMenuPanel();
        JPanel simulatePanel = createSimulatePanel();
        dashboardPanel = createDashboardPanel();

        mainPanel.add(mainMenuPanel, "MAIN_MENU");
        mainPanel.add(simulatePanel, "SIMULATE_VIEW");
        mainPanel.add(dashboardPanel, "DASHBOARD_MENU");

        add(mainPanel);

        setVisible(true);
        setResizable(true);
    }

    /**
     * Builds the main menu panel with navigation buttons.
     * @return a JPanel for the MAIN_MENU card
     */
    private JPanel createMainMenuPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // LABELS
        JPanel mainMenuLabelPanel = new JPanel(new GridBagLayout());

        ArrayList<JLabel> labelList = new ArrayList<JLabel>();
        labelList.add(new JLabel("Choose an option below!"));
        labelList.get(0).setFont(new Font("Verdana", Font.BOLD, 20));

        for (JLabel labels : labelList) {
            mainMenuLabelPanel.add(labels);
        }

        panel.add(mainMenuLabelPanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel mainMenuButtonPanel = new JPanel(new FlowLayout());
        mainMenuButtonPanel.setBackground(Color.decode("#703030"));

        mainMenuButtonList.clear();
        mainMenuButtonList.add(new JButton("SIMULATE"));
        mainMenuButtonList.add(new JButton("DASHBOARD"));
        mainMenuButtonList.add(new JButton("GO BACK TO MAIN"));

        for (JButton buttons : mainMenuButtonList) {
            mainMenuButtonPanel.add(buttons);
        }

        panel.add(mainMenuButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Builds the truck simulation selection panel.
     * @return a JPanel for the SIMULATE_VIEW card
     */
    private JPanel createSimulatePanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // LABELS
        JPanel simulateLabelPanel = new JPanel(new GridBagLayout());

        ArrayList<JLabel> labelList = new ArrayList<JLabel>();
        labelList.add(new JLabel("Question goes here?"));
        labelList.get(0).setFont(new Font("Verdana", Font.BOLD, 20));

        for (JLabel labels : labelList) {
            simulateLabelPanel.add(labels);
        }

        panel.add(simulateLabelPanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel simulateButtonPanel = new JPanel(new FlowLayout());
        simulateButtonPanel.setBackground(Color.decode("#703030"));

        simulateButtonList.clear();
        simulateButtonList.add(new JButton("OPTION 1"));
        simulateButtonList.add(new JButton("OPTION 2"));
        simulateButtonList.add(new JButton("GO BACK"));

        for (JButton buttons : simulateButtonList) {
            simulateButtonPanel.add(buttons);
        }

        panel.add(simulateButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Builds the dashboard showing truck inventories and sales.
     * @return a JPanel for the DASHBOARD_MENU card
     */
    private JPanel createDashboardPanel() {
        double allPrice = 0;
        JPanel panel = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JPanel infoPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 10, 0);

        JLabel name = new JLabel("Dashboard");
        name.setFont(new Font("Verdana", Font.BOLD, 20));

        titlePanel.add(name, gbc);

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(infoPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(infoPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Create a vertical scrollbar
        // JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

        //BUTTONS
        JPanel dashboardButtonPanel = new JPanel(new FlowLayout());
        dashboardButtonPanel.setBackground(Color.decode("#703030"));

        backDashboardButton.setActionCommand("GO BACK DASHBOARD");
        dashboardButtonPanel.add(backDashboardButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(dashboardButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Refreshes the display panels of the application. This method removes existing panels,
     * recreates them to reflect any updated data, and then adds them back to the main panel.
     */
    public void refreshPanels() {
        mainPanel.remove(dashboardPanel);

        dashboardPanel = createDashboardPanel();

        mainPanel.add(dashboardPanel, "DASHBOARD_MENU");

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Sets the ActionListener for various GUI components, ensuring that button clicks
     * and other actions are handled by the provided listener.
     *
     * @param listener The ActionListener to be used for the GUI components.
     */
    public void setActionListener(ActionListener listener) {
        // REMOVE TO NOT STACK
        backButton.removeActionListener(listener);
        backDashboardButton.removeActionListener(listener);

        // ADD
        backButton.addActionListener(listener);
        backDashboardButton.addActionListener(listener);
        for (JButton buttons : mainMenuButtonList) {
            buttons.removeActionListener(listener);
            buttons.addActionListener(listener);
        }
        for (JButton buttons : simulateButtonList) {
            buttons.removeActionListener(listener);
            buttons.addActionListener(listener);
        }
    }

    /**
     * Returns the main JPanel which acts as a container for different views
     *
     * @return The main JPanel of the view.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Returns the CardLayout manager used by the main panel to switch between different views.
     *
     * @return The CardLayout instance.
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }
}