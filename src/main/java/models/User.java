
package models;
import java.time.LocalDate;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.scene.control.Button;


public class User {
    // Attributes
    private int id;

    public User(int id, String username, String email, String country, int cin, String role, LocalDate date_birth, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.country = country;
        this.cin = cin;
        this.role = role;
        this.date_birth = date_birth;
        this.password = password;
    }

    private String username;
    private String email;

    private String country;
    private int cin;
    private String role;
    private LocalDate date_birth;

    private String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", cin=" + cin +
                ", role='" + role + '\'' +
                ", date_birth=" + date_birth +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public int getCin() {
        return cin;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getDate_birth() {
        return date_birth;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDate_birth(LocalDate date_birth) {
        this.date_birth = date_birth;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(){

    }
    public User(String username, String email, String country, int cin, String role, LocalDate date_birth, String password) {
        this.username = username;
        this.email = email;
        this.country = country;
        this.cin = cin;
        this.role = role;
        this.date_birth = date_birth;
        this.password = password;
    }
}
