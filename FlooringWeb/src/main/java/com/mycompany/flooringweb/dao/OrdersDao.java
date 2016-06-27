/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.dto.Order;
import java.util.Date;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface OrdersDao {

    Order checkIfOrderIsInADateFile(int orderId, String date);

    Order create(Order order, boolean testMode);

    void delete(Order order, boolean testMode);

    List<Order> getList();

    Order get(Integer id);

    List<Order> getOrdersForDate(Date date);
    
    List<Date> getListOfDatesWithOrders();

    void update(Order order, boolean testMode);
    
    boolean isTest();

    void setTest(boolean test);
    
}
