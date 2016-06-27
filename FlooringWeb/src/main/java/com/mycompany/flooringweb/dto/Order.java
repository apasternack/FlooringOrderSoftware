/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author apprentice
 */
public class Order {

    private int id;
    @NotEmpty(message = "You must supply a customer name")
    private String customerName;
    private String state;
    private Double taxRate;
    private String productType;
    @Range(min = 0, max = 100000)
    @NotNull
    private Double area;
    private Double materialCostPerSf;
    private Double laborCostPerSf;
    private Double materialCost;
    private Double laborCost;
    private Double totalTax;
    private Double totalCost;
//    @DateTimeFormat(pattern = "MM/dd/yyyy")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY/MM/dd", timezone = "EST")
    private Date orderDate;
    private Integer productId;
    private Integer taxId;

    public Order() {
    }

    public Order(String customerName, String state, Double taxRate, String productType, Double area, Double materialCostPerSf, Double laborCostPerSf, Double materialCost, Double laborCost, Double totalTax, Double totalCost, Date orderDate) {
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.materialCostPerSf = materialCostPerSf;
        this.laborCostPerSf = laborCostPerSf;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.totalTax = totalTax;
        this.totalCost = totalCost;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getMaterialCostPerSf() {
        return materialCostPerSf;
    }

    public void setMaterialCostPerSf(Double materialCostPerSf) {
        this.materialCostPerSf = materialCostPerSf;
    }

    public Double getLaborCostPerSf() {
        return laborCostPerSf;
    }

    public void setLaborCostPerSf(Double laborCostPerSf) {
        this.laborCostPerSf = laborCostPerSf;
    }

    public Double getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(Double materialCost) {
        this.materialCost = materialCost;
    }

    public Double getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Double laborCost) {
        this.laborCost = laborCost;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

}
