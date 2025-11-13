/**
 * Class: UserData
 * A Data Transfer Object (DTO) to hold information about a single user
 * for the admin's user report.
 */
public class UserData {
    private int id;
    private String username;
    private String email;

    public UserData(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // --- Getters ---

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}