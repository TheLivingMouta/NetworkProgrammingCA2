package Manager;

import Classes.Film;
import Classes.User;

import java.net.spi.InetAddressResolver;
import java.util.ArrayList;

public class UserManager {
    private final ArrayList<User> userList = new ArrayList<User>();

    private void bootstrapUserList() {
        User u1 = new User("John Doe", "Password123", 0);
        User u2 = new User("John Doe", "Password123", 1);
        User u3 = new User("Admin1", "AdminPassword234", 1);
        User u4 = new User("Carrol", "Password1", 0);

        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);
    }

    public UserManager() {
        bootstrapUserList();
    }

    public boolean addUser(String username, String password, int adminStatus) {
        User newUser = new User(username, password, adminStatus);

        if (userList.contains(newUser)) {
            return false;
        } else {
            userList.add(newUser);
            return true;
        }
    }
    public boolean removeUser(String username){
        User existingUser = new User(username);

        if(userList.contains(existingUser)){
            userList.remove(existingUser);
            return  true;
        } else {
            return false;
        }

    }

    public User getUserByUsename(String username){
        User existingUser = new User(username);

        for(User user : userList){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
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

