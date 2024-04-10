package Classes;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private int adminStatus; //0 - user, 1 - admin

    public User() {
    }

    public User(String username, String password, int adminStatus) {
        this.username = username;
        this.password = password;
        this.adminStatus = adminStatus;
    }

    public User(String username) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", password='" + password + '\'' +
                ", adminStatus=" + adminStatus +
                '}';
    }
}
