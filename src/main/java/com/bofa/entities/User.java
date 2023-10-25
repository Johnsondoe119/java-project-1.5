package com.bofa.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    private static int userIdTracker = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;
    private String username;
    private String password;

    @Enumerated(value = EnumType.ORDINAL)
    private Role role;


    @ManyToMany
    @JoinTable(name = "users_clothes",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "iscn")}

    )
    private List<Clothes> clothes;

    public User() {
        this.userId = ++userIdTracker;
        this.clothes = new ArrayList<>();
    }

    public User(String username, String password, Role role) {
        this.userId = ++userIdTracker;
        this.username = username;
        this.password = password;
        this.role = role;
        this.clothes = new ArrayList<>();
    }

    public int getUserId() {
        return this.userId;
    }

    // Getter and setter methods for username, password, and role
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        if (username.length() >= 8) {
            this.username = username;
        } else {
            System.out.println("Username is not long enough. Must be 8 characters.");
        }
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return this.role;
    }


    public void setRole(Role role) {
        this.role = role;
    }

    // Getter and setter methods for clothes
    public List<Clothes> getClothes() {
        return this.clothes;
    }

    public void setClothes(List<Clothes> clothes) {
        this.clothes = clothes;
    }

    // Additional methods to add and remove clothes
    public void addClothes(Clothes clothing) {
        this.clothes.add(clothing);
    }

    public void removeClothes(Clothes clothing) {
        this.clothes.remove(clothing);
    }

    @Override
    public String toString() {
        return "User [userId=" + this.userId +
                ", username=" + this.username +
                ", password=" + this.password +
                ", role=" + this.role +
                ", clothes=" + this.clothes +
                "]";
    }
}
