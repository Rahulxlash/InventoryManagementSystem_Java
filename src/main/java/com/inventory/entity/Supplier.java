package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "suppliers")
public class Supplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private Integer suppID;
    
    @NotBlank(message = "Supplier code is required")
    @Column(name = "suppliercode", nullable = false, unique = true)
    private String suppCode;
    
    @NotBlank(message = "Full name is required")
    @Column(name = "fullname", nullable = false)
    private String fullName;
    
    @NotBlank(message = "Location is required")
    @Column(name = "location", nullable = false)
    private String location;
    
    @NotBlank(message = "Mobile is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must be 10 digits")
    @Column(name = "mobile", nullable = false, length = 10)
    private String mobile;

    public Supplier() {}

    public Supplier(String suppCode, String fullName, String location, String mobile) {
        this.suppCode = suppCode;
        this.fullName = fullName;
        this.location = location;
        this.mobile = mobile;
    }

    public Integer getSuppID() {
        return suppID;
    }

    public void setSuppID(Integer suppID) {
        this.suppID = suppID;
    }

    public String getSuppCode() {
        return suppCode;
    }

    public void setSuppCode(String suppCode) {
        this.suppCode = suppCode;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
