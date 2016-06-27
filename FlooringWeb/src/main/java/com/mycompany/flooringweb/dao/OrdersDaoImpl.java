/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.controller.ConsoleIO;
import com.mycompany.flooringweb.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apprentice
// */
//public class OrdersDaoImpl implements OrdersDao {
//
//    private List<Order> orders = new ArrayList();
//    private ConsoleIO io = new ConsoleIO();
//    private String path;
//    private boolean test = true;
//
//    public boolean isTest() {
//        return test;
//    }
//
//    public void setTest(boolean test) {
//        this.test = test;
//    }
//
//    public OrdersDaoImpl(String path) {
//        this.path = path;
//        orders = decode(path);
//    }
//
//    @Override
//    public List<Order> getList() {
//        return new ArrayList(orders);
//    }
//
//    @Override
//    public Order get(Integer id) {
//
//        for (Order order : orders) {
//            if (order.getId() == id) {
//                return order;
//            }
//        }
//        return null; //if getList returns null, product object is not in the database
//    }
//
//    @Override
//    public List<Order> getOrdersForDate(String date) {
//
//        List<Order> dailyOrders = new ArrayList();
//
//        for (Order myOrder : orders) {
//            if (myOrder.getOrderDate().equals(date)) {
//                dailyOrders.add(myOrder);
//            }
//        }
//        if (dailyOrders.isEmpty()) {
//            return null;
//        } else {
//            return dailyOrders;
//        }
//
//    }
//
//    @Override
//    public List<String> getListOfDatesWithOrders() {
//
//        List<String> orderDates = new ArrayList();
//
//        for (Order myOrder : orders) {
//
//            if (!orderDates.contains(myOrder.getOrderDate())) {
//
//                orderDates.add(myOrder.getOrderDate());
//            }
//        }
//
//        return orderDates;
//    }
//
//    @Override
//    public Order checkIfOrderIsInADateFile(int orderId, String date) {
//
//        List<Order> orders = getOrdersForDate(date);
//
//        if (orders == null) {
//            return null;
//
//        } else {
//
//            for (Order order : orders) {
//                if (order.getId() == orderId) {
//                    return order;
//                }
//            }
//            return null; //if getList returns null, order object is not in the database
//        }
//    }
//
//    @Override
//    public Order create(Order order, boolean testMode) {
//
//        int highestId = 0;
//
//        for (Order myOrder : orders) {
//            if (myOrder.getId() > highestId) {
//                highestId = myOrder.getId();
//            }
//        }
//
//        order.setId((highestId + 1));
//
//        orders.add(order);
//
//        if (!testMode) {
//            encode(path, order.getOrderDate(), order);
//        }
//
//        return order;
//
//    }
//
//    @Override
//    public void update(Order order, boolean testMode) {
//
//        Order found = null;
//
//        for (Order myOrder : orders) {
//            if (order.getId() == myOrder.getId()) {
//                found = myOrder;
//            }
//        }
//
//        if (found != null) {
//
//            orders.remove(found);
//            orders.add(order);
//
//            if (!testMode) {
//                encode(path, found.getOrderDate(), order);
//            }
//        }
//    }
//
//    @Override
//    public void delete(Order order, boolean testMode) {
//
//        Order found = null;
//
//        for (Order myOrder : orders) {
//            if (order.getId() == myOrder.getId()) {
//                found = myOrder;
//            }
//        }
//
//        if (found != null) {
//
//            orders.remove(found);
//
//            if (!testMode) {
//                encode(path, found.getOrderDate(), order);
//            }
//        }
//    }
//
//    private List<Order> decode(String directory) {
//
//        List<Order> loadedOrders = new ArrayList();
//
//        File dir = new File(directory);
//        File[] directoryListing = dir.listFiles();
//
//        if (directoryListing != null) {
//            for (File child : directoryListing) {
//
//                try {
//                    Scanner sc = new Scanner(new BufferedReader(new FileReader(child.toString())));
//
//                    sc.nextLine();
//
//                    while (sc.hasNextLine()) {
//                        String currentLine = sc.nextLine();
//
//                        String[] stringParts = currentLine.split("(?<!\\\\),");
//
//                        Order myOrder = new Order();
//
//                        int id = Integer.parseInt(stringParts[0]);
//
//                        myOrder.setId(id);
//                        myOrder.setCustomerName(stringParts[1].replaceAll("\\\\", ""));
//                        myOrder.setState(stringParts[2]);
//                        myOrder.setTaxRate(Double.parseDouble(stringParts[3]));
//                        myOrder.setProductType(stringParts[4]);
//                        myOrder.setArea(Double.parseDouble(stringParts[5]));
//                        myOrder.setMaterialCostPerSf(Double.parseDouble(stringParts[6]));
//                        myOrder.setLaborCostPerSf(Double.parseDouble(stringParts[7]));
//                        myOrder.setMaterialCost(Double.parseDouble(stringParts[8]));
//                        myOrder.setLaborCost(Double.parseDouble(stringParts[9]));
//                        myOrder.setTotalTax(Double.parseDouble(stringParts[10]));
//                        myOrder.setTotalCost(Double.parseDouble(stringParts[11]));
//                        myOrder.setOrderDate(child.toString().replace((directory + "/Orders_"), "").replace(".txt", ""));
//                        loadedOrders.add(myOrder);
//                    }
//
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(OrdersDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//
//                }
//
//            }
//        } else {
//            // Handle the case where dir is not really a directory.
//            // Checking dir.isDirectory() above would not be sufficient
//            // to avoid race conditions with another process that deletes
//            // directories.
//        }
//
//        return loadedOrders;
//    }
//
//    private void encode(String directory, String date, Order order) {
//
//        final String TOKEN = ",";
//        DecimalFormat df = new DecimalFormat("#.00");
//        File dir = new File(directory);                     //declaring a new file object of directory "directory" named "dir"
//        File[] directoryListing = dir.listFiles();          //getting an array of filenames(aka, order dates)
//        List<String> orderDates = new ArrayList();           //Making an array of filenames (which are named by order date)
//        String name;
//        if (directoryListing != null) {                      //Check to make sure there are some orderDates, if so make a list of files/orderDates called "orderDates"
//            for (File file : directoryListing) {
//                name = file.toString();
//                orderDates.add(name);
//            }
//            if (orderDates.contains(directory + "/Orders_" + date + ".txt")) {       //check if there are other orders that share the current order to be encoded's date 
//
//                //if so, make an ArrayList of all orders from that date CALL ABOVE FUNCTION (getOrdersForDate(DATE)), 
//                List<Order> daysOrders = getOrdersForDate(date);
//                if (daysOrders != null) {
//                    //then encode them (they already include our new order!!!
//                    try {
//                        PrintWriter out = new PrintWriter(new FileWriter((directory + "/Orders_" + date + ".txt")));
//
//                        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCost"
//                                + "PerSquareFoot,MaterialCost,LaborCost,Tax,Total");
//
//                        for (Order myOrder : daysOrders) {
//
//                            out.print(io.encodeComma(myOrder.getId()));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(myOrder.getCustomerName()));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(myOrder.getState()));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(df.format(myOrder.getTaxRate())));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(myOrder.getProductType()));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(df.format(myOrder.getArea())));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(myOrder.getMaterialCostPerSf()));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(myOrder.getLaborCostPerSf()));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(myOrder.getMaterialCost()));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(myOrder.getLaborCost()));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(df.format(myOrder.getTotalTax())));
//                            out.print(TOKEN);
//
//                            out.print(io.encodeComma(df.format(myOrder.getTotalCost())));
//
//                            out.println("");
//                        }
//
//                        out.flush();
//                        out.close();
//
//                    } catch (IOException ex) {
//                        Logger.getLogger(OrdersDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } else {
//                    try {
//
//                        File file = new File(directory + "/Orders_" + date + ".txt");
//                        file.delete();
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//
//                    }
//
//                }
//
//            } else {
//                encodeNewDateFile(directory, date, order);   //create new date file and encode this one order!!
//            }
//
//        } else {
//            encodeNewDateFile(directory, date, order);
//        }
//
//    }
//
//    private void encodeNewDateFile(String directory, String date, Order myOrder) {
//        final String TOKEN = ",";
//        DecimalFormat df = new DecimalFormat("#.00");
//
//        try (PrintWriter out = new PrintWriter(new FileWriter((directory + "/Orders_" + date + ".txt")))) {
//            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCost"
//                    + "PerSquareFoot,MaterialCost,LaborCost,Tax,Total");
//
//            out.print(io.encodeComma(myOrder.getId()));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(myOrder.getCustomerName()));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(myOrder.getState()));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(df.format(myOrder.getTaxRate())));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(myOrder.getProductType()));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(df.format(myOrder.getArea())));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(myOrder.getMaterialCostPerSf()));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(myOrder.getLaborCostPerSf()));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(myOrder.getMaterialCost()));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(myOrder.getLaborCost()));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(myOrder.getTotalTax()));
//            out.print(TOKEN);
//
//            out.print(io.encodeComma(myOrder.getTotalCost()));
//
//            out.println("");
//
//            out.flush();
//            out.close();
//
//        } catch (IOException ex) {
//            Logger.getLogger(OrdersDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//}
