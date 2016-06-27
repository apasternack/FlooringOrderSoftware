package com.mycompany.flooringweb.controller;

import com.mycompany.flooringweb.dao.AuditDao;
import com.mycompany.flooringweb.dao.OrdersDao;
import com.mycompany.flooringweb.dao.ProductsDao;
import com.mycompany.flooringweb.dao.TaxesDao;
import com.mycompany.flooringweb.dto.Order;
import com.mycompany.flooringweb.dto.Product;
import com.mycompany.flooringweb.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private OrdersDao ordersDao;
    private ProductsDao productsDao;
    private TaxesDao taxesDao;
    private AuditDao auditDao;

    @Inject
    public HomeController(
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

    public int setProgramMode(String configureFilePath) {

        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(configureFilePath)));

            String programMode = sc.nextLine();

            switch (programMode) {
                case "test":
                    ordersDao.setTest(true);
                    return 1;
                case "prod":
                    ordersDao.setTest(false);
                    return 2;
                default:
                    ordersDao.setTest(true);
                    return 3;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Map model) {

        int testMode = setProgramMode("textFiles/config.txt");

        List<Order> orders = ordersDao.getList();
        List<Tax> taxes = taxesDao.getList();
        List<Product> products = productsDao.getList();
        
        List<Date> orderDates = ordersDao.getListOfDatesWithOrders();

//        model.put("orders", orders);   this code will load up all orders in database on home page load/refresh
        model.put("order", new Order());
        model.put("orderDates", orderDates);
        model.put("taxes", taxes);
        model.put("tax", new Tax());
        model.put("products", products);
        model.put("product", new Product());
        model.put("testMode", testMode);

        return "home";
    }

}
