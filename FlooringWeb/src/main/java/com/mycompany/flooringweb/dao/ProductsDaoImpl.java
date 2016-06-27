/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.dto.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class ProductsDaoImpl implements ProductsDao {

    private List<Product> products = new ArrayList();
    private int nextId;
    private String readWriteFile;
    private ProductsReadWrite rw;

    public ProductsDaoImpl(String readWriteFile, ProductsReadWrite rw) {
        this.readWriteFile = readWriteFile;
        this.rw = rw;
        products = decode(readWriteFile);

    }

    @Override
    public Product create(Product product) {

        int highestId = 0;

        for (Product myProduct : products) {
            if (myProduct.getId() > highestId) {
                highestId = myProduct.getId();
            }
        }

        nextId = highestId + 1;

        product.setId(nextId);

        products.add(product);

        rw.encode(readWriteFile, products);

        return product;

    }

    @Override
    public Product get(Integer id) {

        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null; //if getList returns null, product object is not in the database
    }

    @Override
    public Product get(String productType) {

        for (Product product : products) {
            if (productType.equals(product.getProductType())) {
                return product;
            }
        }
        return null; //if getList returns null, product object is not in the database
    }

    @Override
    public List<Product> getList() {
        return new ArrayList(products);
    }

    @Override
    public void update(Product product) {

        Product found = new Product();

        for (Product myProduct : products) {

            if (myProduct.getId() == product.getId()) {
                found = myProduct;

            }
        }
        products.remove(found);
        products.add(product);
        rw.encode(readWriteFile, products);
    }

    @Override
    public void delete(Product product) {

        Product found = null;

        for (Product myProduct : products) {

            if (myProduct.getId() == product.getId()) {
                found = myProduct;
                break;
            }
        }
        products.remove(found);

        rw.encode(readWriteFile, products);

    }

   

    private List<Product> decode(String file) {

        List<Product> productList = new ArrayList();

        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));

            sc.nextLine();
            int initialId = 1;

            while (sc.hasNextLine()) {
                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(",");

                Product myProduct = new Product();

                myProduct.setProductType(stringParts[0]);
                myProduct.setMaterialCostPerSf(Double.parseDouble(stringParts[1]));
                myProduct.setLaborCostPerSf(Double.parseDouble(stringParts[2]));

                if (myProduct.getId() == 0) {
                    myProduct.setId(initialId);
                    initialId++;
                }

                productList.add(myProduct);

            }

        } catch (IOException ex) {

        }

        return productList;
    }

}
