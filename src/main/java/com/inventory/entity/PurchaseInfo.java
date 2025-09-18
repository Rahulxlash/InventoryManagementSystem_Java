package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "purchaseinfo")
public class PurchaseInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchaseID")
    private Integer purchaseId;
    
    @NotBlank(message = "Supplier code is required")
    @Column(name = "suppliercode", nullable = false)
    private String supplierCode;
    
    @NotBlank(message = "Product code is required")
    @Column(name = "productcode", nullable = false)
    private String productCode;
    
    @NotBlank(message = "Date is required")
    @Column(name = "date", nullable = false)
    private String date;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @NotNull(message = "Total cost is required")
    @Positive(message = "Total cost must be positive")
    @Column(name = "totalcost", nullable = false)
    private Double totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suppliercode", referencedColumnName = "suppliercode", insertable = false, updatable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productcode", referencedColumnName = "productcode", insertable = false, updatable = false)
    private Product product;

    public PurchaseInfo() {}

    public PurchaseInfo(String supplierCode, String productCode, String date, Integer quantity, Double totalCost) {
        this.supplierCode = supplierCode;
        this.productCode = productCode;
        this.date = date;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
