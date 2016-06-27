/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.dto.Product;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface ProductsDao {

    Product create(Product product);

    void delete(Product product);

    Product get(Integer id);

    Product get(String productType);

    List<Product> getList();

    void update(Product product);

}
