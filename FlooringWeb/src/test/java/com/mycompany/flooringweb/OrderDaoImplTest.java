/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb;

import com.mycompany.flooringweb.dao.OrdersDao;
import com.mycompany.flooringweb.dto.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class OrderDaoImplTest {

    public OrderDaoImplTest() {
    }
//
//    ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
//    OrdersDao dao = ctx.getBean("ordersDao", OrdersDaoImpl.class);

    @Before
    public void setUp() {
//
//        Order order = new Order("Adam", "OH", 5.5, "Tile", 550d, 1.3d, 1.5d, 500d, 450d, 230d, 1200d, "06032016");
//        Order order2 = new Order("Adam", "OH", 5.5, "Tile", 550d, 1.3d, 1.5d, 500d, 450d, 230d, 1200d, "06052016");
//        Order order3 = new Order("Adam", "OH", 5.5, "Tile", 550d, 1.3d, 1.5d, 500d, 450d, 230d, 1200d, "06132016");
//
//        dao.create(order, false);
//        dao.create(order2, false);
//        dao.create(order3, false);
//        
    }

    @After
    public void tearDown() {

    }
    

// TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

//    @Test
//    public void testGetListOfDatesWithOrders() {
//
//        List<String> orderDates = dao.getListOfDatesWithOrders();
//        
//        String date = orderDates.get(1);
//        
//        Assert.assertEquals("06052016", date);
//        
//        
//        
//        
//    }

}
