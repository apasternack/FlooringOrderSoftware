/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;


import com.mycompany.flooringweb.dto.Audit;
import com.mycompany.flooringweb.dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author apprentice
 */
public class AuditDaoImpl implements AuditDao {

    Audit audit = new Audit();
    private int nextId;
    private String readWriteFile;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public AuditDaoImpl(String readWriteFile) {
        this.readWriteFile = readWriteFile;
//        auditLog = decode(readWriteFile);  DONT WANT THIS, no decode from file to memory, just want to print a line to file as log is called

    }

    @Override
    public void log(JoinPoint jp) {

        nextId = decode(readWriteFile);  // decode returns the number of lines in audit txt file, which is a value +1 of audits because the header takes up one line;  this # lines == next audit ID
        if (nextId == 0) {
            nextId = 1;
        }
        Object[] args = jp.getArgs();
        Order myOrder = (Order) args[0];

        Date now = new Date();

        audit.setAuditId(nextId);
        audit.setOrderId(myOrder.getId());
        audit.setOrder(myOrder);
        audit.setDate(now);
        audit.setAction(jp.getSignature().getName());

        encode(readWriteFile);

    }

    //WRITE A METHOD THAT READS OUT AUDIT FILE TO AN ARRAY LIST OF AUDITS AND RETURNS THEM out for analysis, reading, etc
//    @Override
//    public List<Audit> getList() {
//        return new ArrayList(auditLog);
//    }
    // If no lines are present (decode will pass this info return ## of lines to encode (OR TO LOG) , print header line and then each log
    // Else, append lines simply
    private void encode(String file) {
        DecimalFormat df = new DecimalFormat("#.00");

        final String TOKEN = ",";

        int numberOfAuditFileLines = decode(readWriteFile);

        try (PrintWriter out = new PrintWriter(new FileWriter(file, true)) //the true will append the new data
                ) {

            if (numberOfAuditFileLines == 0) {

                out.println("AuditID,OrderID,Action,DateActionOccured,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCost"
                        + "PerSquareFoot,MaterialCost,LaborCost,Tax,Total");
            }
            out.print(audit.getAuditId());
            out.print(TOKEN);

            out.print(audit.getOrderId());
            out.print(TOKEN);

            out.print(audit.getAction());
            out.print(TOKEN);

            out.print(dateFormat.format(audit.getDate()));
            out.print(TOKEN);

            out.print(audit.getOrder().getCustomerName());
            out.print(TOKEN);

            out.print(audit.getOrder().getState());
            out.print(TOKEN);

            out.print(df.format(audit.getOrder().getTaxRate()));
            out.print(TOKEN);

            out.print(audit.getOrder().getProductType());
            out.print(TOKEN);

            out.print(df.format(audit.getOrder().getArea()));
            out.print(TOKEN);

            out.print(audit.getOrder().getMaterialCostPerSf());
            out.print(TOKEN);

            out.print(audit.getOrder().getLaborCostPerSf());
            out.print(TOKEN);

            out.print(audit.getOrder().getMaterialCost());
            out.print(TOKEN);

            out.print(audit.getOrder().getLaborCost());
            out.print(TOKEN);

            out.print(audit.getOrder().getTotalTax());
            out.print(TOKEN);

            out.print(audit.getOrder().getTotalCost());

            out.println("");

        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
//        PrintWriter out = null;
//
//        try {
//            out = new PrintWriter(new FileWriter(file));
//
//            if //file empty, write header!!  else dont!!
//            
//            out.println("AuditID,OrderID,Action,DateActionOccured,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCost"
//                    + "PerSquareFoot,MaterialCost,LaborCost,Tax,Total");
//
//            for (Audit myAudit : auditLog) {
//
//                out.print(myAudit.getAuditId());
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrderId());
//                out.print(TOKEN);
//
//                out.print(myAudit.getAction());
//                out.print(TOKEN);
//
//                out.print(dateFormat.format(myAudit.getDate()));
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrder().getCustomerName());
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrder().getState());
//                out.print(TOKEN);
//
//                out.print(df.format(myAudit.getOrder().getTaxRate()));
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrder().getProductType());
//                out.print(TOKEN);
//
//                out.print(df.format(myAudit.getOrder().getArea()));
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrder().getMaterialCostPerSf());
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrder().getLaborCostPerSf());
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrder().getMaterialCost());
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrder().getLaborCost());
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrder().getTotalTax());
//                out.print(TOKEN);
//
//                out.print(myAudit.getOrder().getTotalCost());
//
//                out.println("");
//
//            }
//
//            out.flush();
//            out.close();
//
//        } catch (IOException ex) {
//
//        }
        //appends the string to the file
    }

    //write a decode method that counts ## of lines, then can pass that info out to log so it can then assign a audit id accordingly
    private int decode(String file) {

        int lines = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                lines++;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AuditDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuditDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lines;

    }
//    private List<Audit> decode(String file) {
//
//        List<Audit> auditList = new ArrayList();
//
//        try {
//            Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
//
//            sc.nextLine();
//
//            while (sc.hasNextLine()) {
//                String currentLine = sc.nextLine();
//
//                String[] stringParts = currentLine.split(",");
//
//                Audit myAudit = new Audit();
//
//                myAudit.setAuditId(Integer.parseInt(stringParts[0]));
//                myAudit.setOrderId(Integer.parseInt(stringParts[1]));
//                myAudit.setAction(stringParts[2]);
//                try {
//                    myAudit.setDate(dateFormat.parse(stringParts[3]));
//                } catch (ParseException ex) {
//                    Logger.getLogger(AuditDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                auditList.add(myAudit);
//
//            }
//
//        } catch (IOException ex) {
//
//        }
//
//        return auditList;
//    }
}
