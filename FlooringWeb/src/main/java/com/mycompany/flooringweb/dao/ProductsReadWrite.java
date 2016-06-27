/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.dto.Product;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class ProductsReadWrite {
   
    
     public void encode(String file, List<Product> products) {

        final String TOKEN = ",";

        PrintWriter out = null;

        try {
            out = new PrintWriter(new FileWriter(file));

            out.println("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot");

            for (Product myProduct : products) {

                out.print(myProduct.getProductType());
                out.print(TOKEN);

                out.print(myProduct.getMaterialCostPerSf());
                out.print(TOKEN);

                out.print(myProduct.getLaborCostPerSf());

                out.println("");

                out.flush();
                out.close();
            }

        } catch (IOException ex) {

        } 
    }
    
    
}
