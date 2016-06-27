/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.dto.Tax;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apprentice
 */
public class TaxesDaoDbImpl implements TaxesDao {

    private static final String SQL_INSERT_TAX = "INSERT INTO flooring.tax (state, taxRate) VALUES (?, ?)";
    private static final String SQL_UPDATE_TAX = "UPDATE flooring.tax SET state = ?, taxRate = ? WHERE id = ?";
    private static final String SQL_DELETE_TAX = "DELETE FROM flooring.tax WHERE id = ?";
    private static final String SQL_GET_TAX = "SELECT * FROM flooring.tax WHERE id = ?";
    private static final String SQL_GET_TAX_BY_STATE = "SELECT * FROM flooring.tax WHERE state = ?";
    private static final String SQL_GET_TAX_LIST = "SELECT * FROM flooring.tax";

    private JdbcTemplate jdbcTemplate;

    public TaxesDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Tax create(Tax tax) {

        jdbcTemplate.update(SQL_INSERT_TAX,
                tax.getState(),
                tax.getTaxRate());

        Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);  //gets next unique id

        tax.setId(id);

        return tax;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Tax tax) {

        jdbcTemplate.update(SQL_DELETE_TAX, tax.getId());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Tax get(Integer id) {

        return jdbcTemplate.queryForObject(SQL_GET_TAX, new TaxMapper(), id);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Tax get(String state) {

        return jdbcTemplate.queryForObject(SQL_GET_TAX_BY_STATE, new TaxMapper(), state);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Tax> getList() {

        return jdbcTemplate.query(SQL_GET_TAX_LIST, new TaxMapper());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Tax tax) {

        jdbcTemplate.update(SQL_UPDATE_TAX,
                tax.getState(),
                tax.getTaxRate(),
                tax.getId());

    }

    private static final class TaxMapper implements RowMapper<Tax> {

        @Override
        public Tax mapRow(ResultSet rs, int i) throws SQLException {

            Tax tax = new Tax();

            tax.setId(rs.getInt("id"));  //getting the data from "id" column for this iteration through a row
            tax.setState(rs.getString("state"));
            tax.setTaxRate(rs.getDouble("taxRate"));

            return tax;

        }

    }

}
