import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class: MenuView
 * This class represents the main menu window of the database application.
 * It provides options for users to register as either an administrator or a customer.
 */
public class MenuView extends JFrame {
    private ArrayList<JLabel> labelList = new ArrayList<JLabel>();
    private ArrayList<JButton> menuList = new ArrayList<JButton>();

    /**
     * Constructs a new MenuView frame.
     * Sets up the window title, default close operation, layout, and size
     * then calls methods to create and add the main label panel and option panel.
     */
    public MenuView() {
        super("Food Guide Main Menu"); // constructor of the JFrame & window title

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 400);

        // create panels here
        createLabelMainPanel();
        createOptionMainPanel();

        setVisible(true); // show window
        setResizable(false); // affix window
    }

    /**
     * Creates and configures the main label panel, which displays a welcome message
     * and a prompt for user registration choice.
     */
    public void createLabelMainPanel() {
        // Each panel should have a setLayout()
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        // new label with text inside
        labelList.add(new JLabel("Hello User, welcome to the Food Guide System!"));
        labelList.getFirst().setFont(new Font("Verdana", Font.BOLD, 20));
        labelList.add(new JLabel("Do you want to sign in as an admin or a customer?"));

        // add buttons to panel
        for (JLabel jLabel : labelList) {
            jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelPanel.add(jLabel);
        }

        // Wrapper panel to center labelPanel vertically and horizontally
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(labelPanel);

        this.add(centerWrapper, BorderLayout.CENTER); // attach panel to frame
    }

    /**
     * Creates and configures the option panel.
     * This panel contains buttons for ADMIN & CUSTOMER, allowing users to select their role.
     */
    private void createOptionMainPanel() {
        // Each panel should have a setLayout()
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        mainPanel.setBackground(Color.decode("#703030")); // change panel color

        menuList.add(new JButton("ADMIN")); // new button with text inside
        menuList.add(new JButton("CUSTOMER"));

        for (int i = 0; i < menuList.size(); i++) // add buttons to panel
            mainPanel.add(menuList.get(i));

        this.add(mainPanel, BorderLayout.SOUTH); // attach panel to frame
    }

    /**
     * Sets the `ActionListener` for all buttons in the `menuList`.
     * This allows external classes to handle button click events.
     *
     * @param listener The ActionListener to be added to each button.
     */
    public void setActionListener(ActionListener listener) {
        for (JButton jButton : menuList)
            jButton.addActionListener(listener);
    }
}