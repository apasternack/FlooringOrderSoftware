/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import org.aspectj.lang.JoinPoint;

/**
 *
 * @author apprentice
 */
public interface AuditDao {

    void log(JoinPoint jp);

//    List<Audit> getList();

}
