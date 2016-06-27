/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.controller;

import com.mycompany.flooringweb.dao.ProductsDao;
import com.mycompany.flooringweb.dao.TaxesDao;
import com.mycompany.flooringweb.dto.Order;
import com.mycompany.flooringweb.dto.Product;
import com.mycompany.flooringweb.dto.Tax;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author apprentice
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

//    private OrdersDao ordersDao;
//    private ProductsDao productsDao;
    private TaxesDao taxesDao;
    private ProductsDao productsDao;

    @Inject
    public AdminController(
            //            OrdersDao ordersDao,
            ProductsDao productsDao,
            TaxesDao taxesDao
    ) {
//        this.ordersDao = ordersDao;
        this.productsDao = productsDao;
        this.taxesDao = taxesDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Map model) {

        List<Tax> taxes = taxesDao.getList();
        List<Product> products = productsDao.getList();

        model.put("taxes", taxes);
        model.put("tax", new Tax());
        model.put("products", products);
        model.put("product", new Product());

        return "admin";
    }

    @RequestMapping(value = "/tax", method = RequestMethod.POST)
    @ResponseBody
    public Tax createTax(@Valid @RequestBody Tax tax) {

        taxesDao.create(tax);
        return tax;
    }

    @RequestMapping(value = "/tax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteTax(@PathVariable("id") Integer id) {

        Tax taxToDelete = taxesDao.get(id);
        taxesDao.delete(taxToDelete);

    }

    @RequestMapping(value = "/tax", method = RequestMethod.PUT)
    @ResponseBody
    public Tax editTaxSubmit(@Valid @RequestBody Tax tax) {

        taxesDao.update(tax);

        return tax;

    }

    @RequestMapping(value = "/tax/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Tax showTax(@PathVariable("id") Integer contactId) {

        Tax tax = taxesDao.get(contactId);

        return tax;

    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    @ResponseBody
    public Product createProduct(@Valid @RequestBody Product product) {

        productsDao.create(product);
        return product;
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteProduct(@PathVariable("id") Integer id) {

        Product productToDelete = productsDao.get(id);
        productsDao.delete(productToDelete);

    }

    @RequestMapping(value = "/product", method = RequestMethod.PUT)
    @ResponseBody
    public Product editProductSubmit(@Valid @RequestBody Product product) {

        productsDao.update(product);

        return product;

    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Product showProduct(@PathVariable("id") Integer contactId) {

        Product product = productsDao.get(contactId);

        return product;

    }

}

//    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
//    public String showTax(@PathVariable("id") Integer id, Map model) {
//
//        Tax tax = taxesDao.get(id);
//
//        model.put("tax", tax);
//
//        return "show";
//
//    }

