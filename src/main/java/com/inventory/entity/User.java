package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String fullName;
    
    @NotBlank(message = "Location is required")
    @Column(name = "location", nullable = false)
    private String location;
    
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    @Column(name = "phone", nullable = false, length = 10)
    private String phone;
    
    @NotBlank(message = "Username is required")
    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;
    
    @NotBlank(message = "Password is required")
    @Column(name = "password", nullable = false, length = 200)
    private String password;
    
    @NotBlank(message = "User type is required")
    @Column(name = "usertype", nullable = false)
    private String userType;

    public User() {}

    public User(String fullName, String location, String phone, String username, String password, String userType) {
        this.fullName = fullName;
        this.location = location;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
