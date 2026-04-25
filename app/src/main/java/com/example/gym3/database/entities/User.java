package com.example.gym3.database.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gym3.database.GymLogDatabase;

import java.util.*;




@Entity(tableName = GymLogDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;

    private String password;

    private boolean isAdmin;


    // constructor

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        isAdmin = false;
    }


    // equals and hash codes

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && isAdmin() == user.isAdmin() && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), isAdmin());
    }


    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
