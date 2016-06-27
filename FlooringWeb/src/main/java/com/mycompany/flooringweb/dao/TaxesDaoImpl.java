/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.dto.Tax;
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
public class TaxesDaoImpl implements TaxesDao {

    private List<Tax> taxes = new ArrayList();
    private int nextId;
    private String readWriteFile;

//    public TaxLibraryDaoImpl() throws FileNotFoundException {
//        this(DEFAULT_WRITE_FILE);
//    }
    public TaxesDaoImpl(String readWriteFile) {
        this.readWriteFile = readWriteFile;
        taxes = decode(readWriteFile);

    }

    @Override
    public Tax create(Tax tax) {

        int highestId = 0;

        for (Tax myTax : taxes) {
            if (myTax.getId() > highestId) {
                highestId = myTax.getId();
            }
        }

        nextId = highestId + 1;

        tax.setId(nextId);

        taxes.add(tax);

        encode(readWriteFile);

        return tax;

    }

    @Override
    public Tax get(Integer id) {

        for (Tax tax : taxes) {
            if (tax.getId() == id) {
                return tax;
            }
        }
        return null; //if getList returns null, tax object is not in the database
    }
    
    @Override
    public Tax get(String state) {

        for (Tax tax : taxes) {
            if (tax.getState().equals(state)) {
                return tax;
            }
        }
        return null; //if getList returns null, tax object is not in the database
    }

    @Override
    public List<Tax> getList() {
        return new ArrayList(taxes);
    }

    @Override
    public void update(Tax tax) {

        Tax found = new Tax();

        for (Tax myTax : taxes) {

            if (myTax.getId() == tax.getId()) {
                found = myTax;

            }
        }
        taxes.remove(found);
        taxes.add(tax);
        encode(readWriteFile);
    }

    @Override
    public void delete(Tax tax) {

        Tax found = null;

        for (Tax myTax : taxes) {

            if (myTax.getId() == tax.getId()) {
                found = myTax;
                break;
            }
        }
        taxes.remove(found);

        encode(readWriteFile);

    }

    private void encode(String file) {

        final String TOKEN = ",";

        try {
            PrintWriter out = new PrintWriter(new FileWriter(file));

            out.println("State,TaxRate");

            for (Tax myTax : taxes) {

                out.print(myTax.getState());
                out.print(TOKEN);

                out.print(myTax.getTaxRate());

                out.println("");

            }

            out.flush();
            out.close();

        } catch (IOException ex) {

        }
    }

    private List<Tax> decode(String file) {

        List<Tax> taxList = new ArrayList();

        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));
            
            sc.nextLine();
            int initialId = 1;
            
            while (sc.hasNextLine()) {
                String currentLine = sc.nextLine();

                String[] stringParts = currentLine.split(",");

                Tax myTax = new Tax();

                myTax.setState(stringParts[0]);
                myTax.setTaxRate(Double.parseDouble(stringParts[1]));

                
                if (myTax.getId() == 0 ) {
                    myTax.setId(initialId);
                    initialId++;
                }
                
                taxList.add(myTax);
                

            }

        } catch (IOException ex) {

        }

        return taxList;
    }

}
