package business;

import java.util.ArrayList;

public class UserManager {
    private final ArrayList<User> userList = new ArrayList<User>();

    private void bootstrapUserList() {
        User u1 = new User("John Doe", "Password123", 0);
        User u2 = new User("John Doe", "Password123", 1);
        User u3 = new User("Admin1", "AdminPassword234", 1);
        User u4 = new User("Carrol", "Password1", 0);

        synchronized (userList) {
            userList.add(u1);
            userList.add(u2);
            userList.add(u3);
            userList.add(u4);
        }
    }



    public UserManager() {
        bootstrapUserList();
    }

    public boolean addUser(String username, String password, int adminStatus) {
        User newUser = new User(username, password, adminStatus);

        synchronized (userList) {
            if (userList.contains(newUser)) {
                return false;
            } else {
                userList.add(newUser);
                return true;
            }
        }
    }
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
            System.out.println(count++ + " " + user.getUsername() + " " + user.getPassword() + " " + user.getAdminStatus());
        }
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();

        System.out.println("Display all uses");
        userManager.displayUsers();

        System.out.println("---------------------------");

        System.out.println("Adding users");
        userManager.addUser("Oran", "password2", 0);
        userManager.addUser("testUser", "password2", 0);
        userManager.addUser("Admin", "password2", 1);

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


