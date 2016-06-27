/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.dto.Order;
import com.mycompany.flooringweb.dto.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class OrdersDaoDbImpl implements OrdersDao {

    private static final String SQL_INSERT_ORDER = "INSERT INTO flooring.flooringOrders (customerName, orderDate, area, materialCost, laborCost, totalTax, totalCost, tax_id, product_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE flooring.flooringOrders SET customerName=?, orderDate=?, area=?, materialCost=?, laborCost=?, totalTax=?, totalCost=?, tax_id=?, product_id=? WHERE id=?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM flooring.flooringOrders WHERE id = ?";
    private static final String SQL_GET_ORDER = "SELECT * FROM flooring.flooringOrders WHERE id = ?";
    private static final String SQL_GET_ORDER_LIST = "SELECT * FROM flooring.flooringOrders";
    private static final String SQL_GET_ORDER_LIST_FOR_DATE = "SELECT * FROM flooring.flooringOrders WHERE orderDate = ?";
    private static final String SQL_GET_LIST_OF_ORDERDATES = "SELECT DISTINCT orderDate FROM flooring.flooringOrders";
//    private static final String SQL_GET_LIST_OF_ORDERDATES = "SELECT DISTINCT DATE_FORMAT(orderDate,'%m-%d-%Y') FROM flooring.flooringOrders";

    private JdbcTemplate jdbcTemplate;
    private boolean test = true;

    public OrdersDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Order create(Order order, boolean testMode) {

        if (!testMode) {

            jdbcTemplate.update(SQL_INSERT_ORDER,
                    order.getCustomerName(),
                    order.getOrderDate(),
                    order.getArea(),
                    order.getMaterialCost(),
                    order.getLaborCost(),
                    order.getTotalTax(),
                    order.getTotalCost(),
                    order.getTaxId(),
                    order.getProductId());

            Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);  //gets next unique id

            order.setId(id);
        }
        return order;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Order order, boolean testMode) {

        if (!testMode) {
            jdbcTemplate.update(SQL_DELETE_ORDER, order.getId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Order order, boolean testMode) {

        if (!testMode) {

            jdbcTemplate.update(SQL_UPDATE_ORDER,
                    order.getCustomerName(),
                    order.getOrderDate(),
                    order.getArea(),
                    order.getMaterialCost(),
                    order.getLaborCost(),
                    order.getTotalTax(),
                    order.getTotalCost(),
                    order.getTaxId(),
                    order.getProductId(),
                    order.getId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Order get(Integer id) {

        return jdbcTemplate.queryForObject(SQL_GET_ORDER, new OrderMapper(), id);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Order checkIfOrderIsInADateFile(int orderId, String date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Order> getList() {

        return jdbcTemplate.query(SQL_GET_ORDER_LIST, new OrderMapper());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Order> getOrdersForDate(Date date) {

        return jdbcTemplate.query(SQL_GET_ORDER_LIST_FOR_DATE, new OrderMapper(), date);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Date> getListOfDatesWithOrders() {
        
        return jdbcTemplate.query(SQL_GET_LIST_OF_ORDERDATES, new DateMapper());
    }

    @Override

    public boolean isTest() {
        return test;
    }

    @Override
    public void setTest(boolean test) {
        this.test = test;
    }

    private static final class OrderMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int i) throws SQLException {

            Order order = new Order();

            order.setId(rs.getInt("id"));  //getting the data from "id" column for this iteration through a row
            order.setCustomerName(rs.getString("customerName"));
            order.setOrderDate(rs.getDate("orderDate"));
            order.setArea(rs.getDouble("area"));
            order.setMaterialCost(rs.getDouble("materialCost"));
            order.setLaborCost(rs.getDouble("laborCost"));
            order.setTotalTax(rs.getDouble("totalTax"));
            order.setTotalCost(rs.getDouble("totalCost"));
            order.setTaxId(rs.getInt("tax_id"));
            order.setProductId(rs.getInt("product_id"));

            return order;

        }

    }
    
    private static final class DateMapper implements RowMapper<Date> {

        @Override
        public Date mapRow(ResultSet rs, int i) throws SQLException {

            Date date = new Date();
            date = rs.getDate("orderDate");
//            date = rs.getDate("DATE_FORMAT(orderDate,'%m-%d-%Y')");
            return date;

        }

    }

}
