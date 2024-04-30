package business;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private int adminStatus; //0 - user, 1 - admin

    public User() {
        this.adminStatus = 0;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.adminStatus = 0;
    }

    public User(String username) {
        this.adminStatus = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }
    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return adminStatus == user.adminStatus && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, adminStatus);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", adminStatus=" + adminStatus +
                '}';
    }
}
