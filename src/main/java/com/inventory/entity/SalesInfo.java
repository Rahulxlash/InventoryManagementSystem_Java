package com.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "salesinfo")
public class SalesInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesid")
    private Integer salesId;
    
    @NotBlank(message = "Date is required")
    @Column(name = "date", nullable = false)
    private String date;
    
    @NotBlank(message = "Product code is required")
    @Column(name = "productcode", nullable = false)
    private String productCode;
    
    @NotBlank(message = "Customer code is required")
    @Column(name = "customercode", nullable = false)
    private String customerCode;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @NotNull(message = "Revenue is required")
    @Positive(message = "Revenue must be positive")
    @Column(name = "revenue", nullable = false)
    private Double revenue;
    
    @NotBlank(message = "Sold by is required")
    @Column(name = "soldby", nullable = false)
    private String soldBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productcode", referencedColumnName = "productcode", insertable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customercode", referencedColumnName = "customercode", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soldby", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

    public SalesInfo() {}

    public SalesInfo(String date, String productCode, String customerCode, Integer quantity, Double revenue, String soldBy) {
        this.date = date;
        this.productCode = productCode;
        this.customerCode = customerCode;
        this.quantity = quantity;
        this.revenue = revenue;
        this.soldBy = soldBy;
    }

    public Integer getSalesId() {
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
