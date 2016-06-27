/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.dto.Tax;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface TaxesDao {

    Tax create(Tax tax);

    void delete(Tax tax);

    Tax get(Integer id);
    
    Tax get(String state);

    List<Tax> getList();

    void update(Tax tax);
    
}
