import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class: CustomerView
 * This class represents the view of the Customer.
 * It handles the representation of panels in the program for the customer,
 * such as...
 */
public class CustomerView extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    private JPanel startPanel;
    private JPanel mainMenuPanel;

    private ArrayList<JButton> mainMenuButtonList = new ArrayList<>();
    private ArrayList<JTextField> signInTextFieldList = new ArrayList<>();

    private JButton signInButton = new JButton("SIGN IN");
    private JButton backButton = new JButton("GO BACK");
    private JButton okButton = new JButton("OK");

    /**
     * Constructs the customer view, initializes all panels,
     * and displays the frame.
     */
    public CustomerView() {
        super("Food Guide Customer Menu");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(850, 450);

        startPanel = createSignInPanel();
        mainMenuPanel = createMainMenuPanel();

        mainPanel.add(startPanel, "START_VIEW");
        mainPanel.add(mainMenuPanel, "MAIN_MENU");

        add(mainPanel);

        setVisible(true);
        setResizable(false);
    }

    /**
     * Creates the starting panel.
     *
     * @return JPanel for the START_VIEW card
     */
    private JPanel createSignInPanel() {
        // MAIN PANEL
        JPanel panel = new JPanel(new BorderLayout());

        // TITLE LABELS
        JPanel labelPanel = new JPanel(new GridBagLayout());

        JLabel titlePanel = new JLabel("Welcome customer! Please sign in to continue.");
        titlePanel.setFont(new Font("Verdana", Font.BOLD, 20));

        labelPanel.add(titlePanel);

        panel.add(labelPanel, BorderLayout.NORTH);

        // TEXT FIELDS
        JPanel textFieldPanel = new JPanel(new GridLayout(2, 1));
        textFieldPanel.setBorder(new EmptyBorder(90, 180, 90, 180));

        ArrayList<JLabel> signInLabelList = new ArrayList<>();
        signInLabelList.add(new JLabel("Name:"));
        signInLabelList.add(new JLabel("Email Address:"));

        signInTextFieldList.clear();
        signInTextFieldList.add(new JTextField("Enter name here...", 20));
        signInTextFieldList.add(new JTextField("Enter email address here...", 20));

        for (int i = 0; i < signInTextFieldList.size(); i++) {
            textFieldPanel.add(signInLabelList.get(i));
            textFieldPanel.add(signInTextFieldList.get(i));
        }

        panel.add(textFieldPanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));

        buttonPanel.add(signInButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public ArrayList<String> getSignInInput() {
        ArrayList<String> texts = new ArrayList<>();

        for (JTextField jTextField : signInTextFieldList)
            texts.add(jTextField.getText());

        return texts;
    }

    /**
     * Creates the main menu panel with ORDER and BACK buttons.
     * @return JPanel for the MAIN_MENU card
     */
    private JPanel createMainMenuPanel() {
        int gridyCtr = 0;

        JPanel panel = new JPanel(new BorderLayout());

        JPanel labelPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 10, 0);

        // TITLE LABELS
        JLabel titlePanel1 = new JLabel("Click ORDER to start ordering!");
        titlePanel1.setFont(new Font("Verdana", Font.BOLD, 20));
        JLabel titlePanel2 = new JLabel("Stuff im sorry if these are too complicated:");
        titlePanel2.setFont(new Font("Verdana", Font.PLAIN, 20));

        gbc.gridy = gridyCtr++;
        labelPanel.add(titlePanel1, gbc);
        gbc.gridy = gridyCtr++;
        labelPanel.add(titlePanel2, gbc);

        panel.add(labelPanel, BorderLayout.NORTH);

        // COFFEE TRUCK LIST
        JPanel infoPanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(4, 0, 1, 0);
        gbc.anchor = GridBagConstraints.WEST;

        //

        panel.add(infoPanel, BorderLayout.CENTER);

        // BUTTONS
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.decode("#2E5E19"));

        mainMenuButtonList.clear();
        mainMenuButtonList.add(new JButton("ORDER"));
        mainMenuButtonList.add(new JButton("GO BACK"));
        mainMenuButtonList.get(1).setActionCommand("GO BACK TO MAIN");

        for (JButton buttons : mainMenuButtonList)
            buttonPanel.add(buttons);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Refreshes dynamic panels when truck data changes.
     */
    public void refreshPanels() {
        // Remove
        mainPanel.remove(mainMenuPanel);

        // Generate and add
        mainMenuPanel = createMainMenuPanel();

        mainPanel.add(mainMenuPanel, "MAIN_MENU");

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Attaches a shared ActionListener to all navigation and action buttons.
     * @param listener the ActionListener to register
     */
    public void setActionListener(ActionListener listener) {
        // Remove
        signInButton.removeActionListener(listener);
        backButton.removeActionListener(listener);
        okButton.removeActionListener(listener);

        // Add
        signInButton.addActionListener(listener);
        backButton.addActionListener(listener);
        okButton.addActionListener(listener);
        for (JButton buttons : mainMenuButtonList) {
            buttons.removeActionListener(listener);
            buttons.addActionListener(listener);
        }
    }

    /**
     * Returns the main panel containing all card views.
     * @return the CardLayout container panel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Returns the CardLayout manager for switching views.
     * @return the CardLayout used by this view
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }
}