import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private JPanel mainMenuPanel;
    private JPanel makeTransactionsPanel;
    private JPanel generateReportsPanel;

    private ArrayList<JButton> mainMenuButtonList = new ArrayList<>();
    private ArrayList<JButton> makeTransactionsButtonList = new ArrayList<>();
    private ArrayList<JButton> generateReportsButtonList = new ArrayList<>();

    private JButton backButton = new JButton("GO BACK");
    private JButton backGenerateReportsButton = new JButton("GO BACK");

    /**
     * Constructs the AdminView, initializes all sub-panels,
     * adds them to the CardLayout, and makes the frame visible.
     */
    public AdminView() {
        super("JavaJeep Admin Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(850, 450);

        mainMenuPanel = createMainMenuPanel();
        makeTransactionsPanel = createMakeTransactionsPanel();
        generateReportsPanel = createGenerateReportsPanel();

        mainPanel.add(mainMenuPanel, "MAIN_MENU");
        mainPanel.add(makeTransactionsPanel, "MAKE_TRANSACTIONS_MENU");
        mainPanel.add(generateReportsPanel, "GENERATE_REPORTS_MENU");

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

        ArrayList<JLabel> labelList = new ArrayList<>();
        labelList.add(new JLabel("Choose an option below!"));
        labelList.getFirst().setFont(new Font("Verdana", Font.BOLD, 20));

        for (JLabel labels : labelList) {
            mainMenuLabelPanel.add(labels);
        }

        panel.add(mainMenuLabelPanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel mainMenuButtonPanel = new JPanel(new FlowLayout());
        mainMenuButtonPanel.setBackground(Color.decode("#FCD303"));

        mainMenuButtonList.clear();
        mainMenuButtonList.add(new JButton("MAKE TRANSACTIONS"));
        mainMenuButtonList.add(new JButton("GENERATE REPORTS"));
        mainMenuButtonList.add(new JButton("GO BACK TO MAIN"));

        for (JButton buttons : mainMenuButtonList) {
            mainMenuButtonPanel.add(buttons);
        }

        panel.add(mainMenuButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Builds the truck simulation selection panel.
     * @return a JPanel for the MAKE_TRANSACTIONS_MENU card
     */
    private JPanel createMakeTransactionsPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // LABELS
        JPanel makeTransactionsLabelPanel = new JPanel(new GridBagLayout());

        ArrayList<JLabel> labelList = new ArrayList<>(); // Used for multiple labels
        labelList.add(new JLabel("What kind of transaction do you want to do?"));
        labelList.getFirst().setFont(new Font("Verdana", Font.BOLD, 20));

        for (JLabel jLabel : labelList) {
            makeTransactionsLabelPanel.add(jLabel);
        }

        panel.add(makeTransactionsLabelPanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel makeTransactionsButtonPanel = new JPanel(new GridLayout(0, 1));
        makeTransactionsButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        makeTransactionsButtonPanel.setBackground(Color.decode("#FCD303"));

        makeTransactionsButtonList.clear();
        makeTransactionsButtonList.add(new JButton("Read User Feedback"));
        makeTransactionsButtonList.add(new JButton("Reserve Order"));
        makeTransactionsButtonList.add(new JButton("Log a New Dish"));
        makeTransactionsButtonList.add(new JButton("Personalize Meal Recommendations"));
        makeTransactionsButtonList.add(new JButton("GO BACK"));

        for (JButton jButton : makeTransactionsButtonList) {
            makeTransactionsButtonPanel.add(jButton);
        }

        panel.add(makeTransactionsButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Shows a menu for generating reports.
     * @return a JPanel for the GENERATE_REPORTS_MENU card
     */
    private JPanel createGenerateReportsPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // LABELS
        JPanel titlePanel = new JPanel(new GridBagLayout());

        // GridBagLayout is used for multiple labels
        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.insets = new Insets(20, 0, 10, 0);

        JLabel label = new JLabel("Choose a report to generate");
        label.setFont(new Font("Verdana", Font.BOLD, 20));

        titlePanel.add(label);

        panel.add(titlePanel, BorderLayout.CENTER);

        // Create a vertical scroll panel
        // JScrollPane scrollPane = new JScrollPane(infoPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

        // BUTTONS
        JPanel generateReportsButtonPanel = new JPanel(new GridLayout(0, 1));
        generateReportsButtonPanel.setBorder(new EmptyBorder(10, 150, 10, 150));
        generateReportsButtonPanel.setBackground(Color.decode("#FCD303"));

        generateReportsButtonList.clear();
        generateReportsButtonList.add(new JButton("New User Registration"));
        generateReportsButtonList.add(new JButton("User Referral Impact"));
        generateReportsButtonList.add(new JButton("Popular Menu Items"));
        generateReportsButtonList.add(new JButton("Food, Ratings and Feedback"));
        generateReportsButtonList.add(new JButton("Revenue and Transaction"));

        for (JButton jButton : generateReportsButtonList) {
            generateReportsButtonPanel.add(jButton);
        }

        backGenerateReportsButton.setActionCommand("GO BACK GENERATE REPORTS");
        generateReportsButtonPanel.add(backGenerateReportsButton);

        // panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(generateReportsButtonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Refreshes the display panels of the application. This method removes existing panels,
     * recreates them to reflect any updated data, and then adds them back to the main panel.
     */
    public void refreshPanels() {
        // Remove
        mainPanel.remove(makeTransactionsPanel);
        mainPanel.remove(generateReportsPanel);

        // Generate and add
        makeTransactionsPanel = createMakeTransactionsPanel();
        generateReportsPanel = createGenerateReportsPanel();

        mainPanel.add(makeTransactionsPanel, "MAKE_TRANSACTIONS_MENU");
        mainPanel.add(generateReportsPanel, "GENERATE_REPORTS_MENU");

        // Revalidate and repaint
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
        // Remove to not stack
        backButton.removeActionListener(listener);
        backGenerateReportsButton.removeActionListener(listener);

        // Add
        backButton.addActionListener(listener);
        backGenerateReportsButton.addActionListener(listener);
        for (JButton jButton : mainMenuButtonList) {
            jButton.removeActionListener(listener);
            jButton.addActionListener(listener);
        }
        for (JButton jButton : generateReportsButtonList) {
            jButton.removeActionListener(listener);
            jButton.addActionListener(listener);
        }
        for (JButton jButton : makeTransactionsButtonList) {
            jButton.removeActionListener(listener);
            jButton.addActionListener(listener);
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