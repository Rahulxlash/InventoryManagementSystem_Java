package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private Integer prodID;
    
    @NotBlank(message = "Product code is required")
    @Column(name = "productcode", nullable = false, unique = true)
    private String prodCode;
    
    @NotBlank(message = "Product name is required")
    @Column(name = "productname", nullable = false)
    private String prodName;
    
    @NotNull(message = "Cost price is required")
    @Positive(message = "Cost price must be positive")
    @Column(name = "costprice", nullable = false)
    private Double costPrice;
    
    @NotNull(message = "Sell price is required")
    @Positive(message = "Sell price must be positive")
    @Column(name = "sellprice", nullable = false)
    private Double sellPrice;
    
    @NotBlank(message = "Brand is required")
    @Column(name = "brand", nullable = false)
    private String brand;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CurrentStock currentStock;

    public Product() {}

    public Product(String prodCode, String prodName, Double costPrice, Double sellPrice, String brand) {
        this.prodCode = prodCode;
        this.prodName = prodName;
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
        this.brand = brand;
    }

    public Integer getProdID() {
        return prodID;
    }

    public void setProdID(Integer prodID) {
        this.prodID = prodID;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public CurrentStock getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(CurrentStock currentStock) {
        this.currentStock = currentStock;
    }
}
