package filmapp.business;

import filmapp.model.User;

import java.util.ArrayList;

/**
 * Manages user operations such as adding, removing, and retrieving users.
 * It initializes with a default set of users and provides functionality to manipulate the user list.
 */
public class UserManager {
    private final ArrayList<User> userList = new ArrayList<User>();

    /**
     * Populates the user list with default data.
     */
    private void bootstrapUserList() {
        User u1 = new User("John Doe", "Password123");
        User u2 = new User("John Doe", "Password123");
        User u3 = new User("Admin1", "AdminPassword234");
        User u4 = new User("Carrol", "Password1");

        synchronized (userList) {
            userList.add(u1);
            userList.add(u2);
            userList.add(u3);
            userList.add(u4);
        }
    }


    /**
     * Initializes the UserManager and populates it with a predefined set of users.
     */
    public UserManager() {
        bootstrapUserList();
    }

    /**
     * Adds a new user with specified username and password to the user list.
     *
     * @param username the username of the new user
     * @param password the password for the new user
     * @return true if the user was added successfully, false if the user already exists
     */
    public boolean addUser(String username, String password) {
        User newUser = new User(username, password);

        synchronized (userList) {
            if (userList.contains(newUser)) {
                return false;
            } else {
                userList.add(newUser);
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
        User existingUser = new User(username);

        boolean flag = false;

        synchronized (userList) {
            if (userList.contains(existingUser)) {
                userList.remove(existingUser);
                flag = true;

            }
        }
        return flag;
    }

    /**
     * Retrieves a user by username.
     *
     * @param username the username of the user to retrieve
     * @return the User object if found, null otherwise
     */
    public User getUserByUsename(String username) {
        User existingUser = new User(username);

        synchronized (userList) {
            for (User user : userList) {
                    if (user.getUsername().equals(username)) {
                        return user;
                    }
                }
            return null;
        }
    }

    /**
     * Validates a user's username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the User object if credentials are valid, null otherwise
     */
    public User validateUser (String username, String password){
        User user = new User(username, password);

        synchronized (user) {
            if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
                return user;
            } else {
                return null;
            }
        }

    }

    public void displayUsers(){
        int count = 1;
        for (User user : userList) {
            System.out.println(count++ + " " + user.getUsername() + " " + user.getAdminStatus());
        }
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
        System.out.println(userManager.getUserByUsename("Oran"));

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


