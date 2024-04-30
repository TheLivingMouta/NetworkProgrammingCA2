package filmapp.business;

import filmapp.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages user operations such as adding, removing, and retrieving users.
 * It initializes with a default set of users and provides functionality to manipulate the user list.
 */
public class UserManager {
    private final Map<String, User> userMap = new HashMap<>();

    /**
     * Populates the user list with default data.
     */
    private void bootstrapUserMap() {
        userMap.put("John Doe", new User("John Doe", "Password123"));
        userMap.put("Jane Doe", new User("Jane Doe", "Password123"));
        userMap.put("admin", new User("admin", "adminpassword", 1));
        userMap.put("Carrol", new User("Carrol", "Password1"));
        userMap.put("user", new User("user", "userpassword"));
    }


    /**
     * Initializes the UserManager and populates it with a predefined set of users.
     */
    public UserManager() {
        bootstrapUserMap();
    }

    /**
     * Adds a new user with specified username and password to the user map.
     *
     * @param username the username of the new user
     * @param password the password for the new user
     * @return true if the user was added successfully, false if the user already exists
     */
    public boolean addUser(String username, String password) {
        synchronized (userMap) {
            if (userMap.containsKey(username)) {
                return false;
            } else {
                userMap.put(username, new User(username, password));
                return true;
            }
        }
    }

    /**
     * Removes a user by username.
     *
     * @param username the username of the user to remove
     * @return true if the user was successfully removed, false otherwise
     */
    public boolean removeUser(String username) {
        synchronized (userMap) {
            return userMap.remove(username) != null;
        }
    }

    /**
     * Retrieves a user by username.
     *
     * @param username the username of the user to retrieve
     * @return the User object if found, null otherwise
     */
    public User getUserByUsername(String username) {
        return userMap.get(username);
    }

    /**
     * Validates a user's username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the User object if credentials are valid, null otherwise
     */
    public User validateUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void displayUsers() {
        userMap.values().forEach(System.out::println);
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();

        System.out.println("Display all uses");
        userManager.displayUsers();

        System.out.println("---------------------------");

        System.out.println("Adding users");
        userManager.addUser("Oran", "password2");
        userManager.addUser("testUser", "password2");
        userManager.addUser("Admin", "password2");

        System.out.println("Display all users");
        userManager.displayUsers();

        System.out.println("---------------------------");

        System.out.println("Searching a user by username");
        System.out.println(userManager.getUserByUsername("Oran"));

        System.out.println("---------------------------");

        System.out.println("Display all users");
        userManager.displayUsers();

        System.out.println("---------------------------");

        System.out.println("Removing a user");
        userManager.removeUser("testUser");

        System.out.println("Display all users");
        userManager.displayUsers();

        System.out.println("---------------------------");
    }
}


