/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.controller;

import com.mycompany.flooringweb.dao.AuditDao;
import com.mycompany.flooringweb.dao.OrdersDao;
import com.mycompany.flooringweb.dao.ProductsDao;
import com.mycompany.flooringweb.dao.TaxesDao;
import com.mycompany.flooringweb.dto.Order;
import com.mycompany.flooringweb.dto.Product;
import com.mycompany.flooringweb.dto.Tax;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping(value = "/order")
public class OrderController {

    private OrdersDao ordersDao;
    private ProductsDao productsDao;
    private TaxesDao taxesDao;
    private AuditDao auditDao;
    DecimalFormat df = new DecimalFormat("#.00");

    @Inject
    public OrderController(
            OrdersDao ordersDao,
            ProductsDao productsDao,
            TaxesDao taxesDao,
            AuditDao auditDao
    ) {
        this.ordersDao = ordersDao;
        this.productsDao = productsDao;
        this.taxesDao = taxesDao;
        this.auditDao = auditDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Order create(@Valid @RequestBody Order order) {

        Tax tax = taxesDao.get(order.getState());
        Product product = productsDao.get(order.getProductType());

        order.setTaxRate(tax.getTaxRate());
        order.setLaborCostPerSf(Double.parseDouble(df.format(product.getLaborCostPerSf())));
        order.setMaterialCostPerSf(Double.parseDouble(df.format(product.getMaterialCostPerSf())));

        Double materialCost = Double.parseDouble(df.format(order.getArea() * order.getMaterialCostPerSf()));
        order.setMaterialCost(materialCost);

        Double laborCost = Double.parseDouble(df.format(order.getArea() * order.getLaborCostPerSf()));
        order.setLaborCost(laborCost);

        order.setTaxId(tax.getId());
        order.setProductId(product.getId());

        Double totalTax = Double.parseDouble(df.format((materialCost + laborCost) * 0.01d * order.getTaxRate()));
        order.setTotalTax(totalTax);

        Double totalCost = Double.parseDouble(df.format(materialCost + laborCost + totalTax));
        order.setTotalCost(totalCost);

        order = ordersDao.create(order, ordersDao.isTest());

        return order;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Integer contactId) {

        Order orderToDelete = ordersDao.get(contactId);
        ordersDao.delete(orderToDelete, ordersDao.isTest());

    }

//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
//    public String edit(@PathVariable("id") Integer orderId, Map model) {
//
//        Order order = ordersDao.get(orderId);
//        List<Tax> taxes = taxesDao.getList();
//        List<Product> products = productsDao.getList();
//
//        model.put("order", order);
//        model.put("taxes", taxes);
//        model.put("products", products);
//        model.put("selectedState", order.getState());
//        model.put("selectedProduct", order.getProductType());
//
//        return "edit";
//    }
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public Order editSubmit(@Valid @RequestBody Order order) {

        Tax tax = taxesDao.get(order.getState());
        Product product = productsDao.get(order.getProductType());

        order.setTaxRate(tax.getTaxRate());
        order.setLaborCostPerSf(Double.parseDouble(df.format(product.getLaborCostPerSf())));
        order.setMaterialCostPerSf(Double.parseDouble(df.format(product.getMaterialCostPerSf())));

        Double materialCost = Double.parseDouble(df.format(order.getArea() * order.getMaterialCostPerSf()));
        order.setMaterialCost(materialCost);

        Double laborCost = Double.parseDouble(df.format(order.getArea() * order.getLaborCostPerSf()));
        order.setLaborCost(laborCost);

        Double totalTax = Double.parseDouble(df.format((materialCost + laborCost) * 0.01d * order.getTaxRate()));
        order.setTotalTax(totalTax);

        Double totalCost = Double.parseDouble(df.format(materialCost + laborCost + totalTax));
        order.setTotalCost(totalCost);

        order.setTaxId(tax.getId());
        order.setProductId(product.getId());

        ordersDao.update(order, ordersDao.isTest());

        return order;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Order show(@PathVariable("id") Integer contactId, Map model) {

        Order order = ordersDao.get(contactId);

        Tax tax = taxesDao.get(order.getTaxId());
        Product product = productsDao.get(order.getProductId());

        order.setState(tax.getState());
        order.setTaxRate(tax.getTaxRate());
        order.setProductType(product.getProductType());
        order.setMaterialCostPerSf(product.getMaterialCostPerSf());
        order.setLaborCostPerSf(product.getLaborCostPerSf());

        return order;

    }

    @RequestMapping(value = "/search/{date}", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> search(@PathVariable("date") String orderDate) {

        Date searchDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");

        try {
            searchDate = dateFormat.parse(orderDate);
        } catch (ParseException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Order> ordersByDate = ordersDao.getOrdersForDate(searchDate);

        return ordersByDate;

    }

//    @RequestMapping(value = "/search", method = RequestMethod.POST)
//    public String edit(@RequestParam("search") String search, @RequestParam("searchBy") String searchBy, Map model) {
//
//        List<Order> searchResults;
//
//        if ("releaseDate".equals(searchBy)) {
//
//            Integer searchInt = Integer.parseInt(search);
//
//            searchResults = ordersDao.searchByDate(searchInt);
//
//        } else {
//
//            searchResults = ordersDao.searchBy(searchBy, search);
//
//        }
//        
//        model.put("searchResults", searchResults);
//
//        return "search";
//    }
}
