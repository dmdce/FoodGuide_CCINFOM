import java.util.*;
import java.awt.*;

/**
 * Class: CustomerModel
 * This class represents the model of the Customer.
 * It takes care the of methods needed for the view and controller like handling
 * data storage, retrieval, manipulation, and validation.
 */
public class CustomerModel {
    private AdminModel am;
    private ArrayList<String> processList;

    /**
     * Constructs a CustomerModel with a reference to the AdminModel.
     *
     * @param am The main AdminModel containing all coffee truck and inventory data.
     */
    public CustomerModel(AdminModel am) {
        this.am = am;
        this.processList = new ArrayList<>();
    }

    /**
     * Gets the AdminModel associated with this customer model.
     *
     * @return The AdminModel instance.
     */
    public AdminModel getAm() {
        return am;
    }
}