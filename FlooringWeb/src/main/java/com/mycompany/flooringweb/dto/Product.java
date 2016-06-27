/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author apprentice
 */
public class Product {

    @NotEmpty
    private String productType;
    @Range(min = 0, max = 25)
    @NotNull
    private Double materialCostPerSf;
    @Range(min = 0, max = 25)
    @NotNull
    private Double laborCostPerSf;
    private int Id;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

}
