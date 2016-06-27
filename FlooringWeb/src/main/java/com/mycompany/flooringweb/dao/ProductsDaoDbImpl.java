/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.dao;

import com.mycompany.flooringweb.dto.Product;
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
public class ProductsDaoDbImpl implements ProductsDao {

    private static final String SQL_INSERT_PRODUCT = "INSERT INTO flooring.product (productType, materialCostPerSf, laborCostPerSf) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE flooring.product SET productType = ?, materialCostPerSf = ?, laborCostPerSf = ? WHERE id = ?";
    private static final String SQL_DELETE_PRODUCT = "DELETE FROM flooring.product WHERE id = ?";
    private static final String SQL_GET_PRODUCT = "SELECT * FROM flooring.product WHERE id = ?";
    private static final String SQL_GET_PRODUCT_BY_TYPE = "SELECT * FROM flooring.product WHERE productType = ?";
    private static final String SQL_GET_PRODUCT_LIST = "SELECT * FROM flooring.product";

    private JdbcTemplate jdbcTemplate;

    public ProductsDaoDbImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Product create(Product product) {

        jdbcTemplate.update(SQL_INSERT_PRODUCT,
                product.getProductType(),
                product.getMaterialCostPerSf(),
                product.getLaborCostPerSf());

        Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);  //gets next unique id

        product.setId(id);

        return product;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Product product) {

        jdbcTemplate.update(SQL_DELETE_PRODUCT, product.getId());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Product product) {

        jdbcTemplate.update(SQL_UPDATE_PRODUCT,
                product.getProductType(),
                product.getMaterialCostPerSf(),
                product.getLaborCostPerSf(),
                product.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Product get(Integer id) {

        return jdbcTemplate.queryForObject(SQL_GET_PRODUCT, new ProductMapper(), id);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Product get(String productType) {

        return jdbcTemplate.queryForObject(SQL_GET_PRODUCT_BY_TYPE, new ProductMapper(), productType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Product> getList() {

        return jdbcTemplate.query(SQL_GET_PRODUCT_LIST, new ProductMapper());

    }

    private static final class ProductMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int i) throws SQLException {

            Product product = new Product();

            product.setId(rs.getInt("id"));  //getting the data from "id" column for this iteration through a row
            product.setProductType(rs.getString("productType"));
            product.setMaterialCostPerSf(rs.getDouble("materialCostPerSf"));
            product.setLaborCostPerSf(rs.getDouble("laborCostPerSf"));

            return product;

        }

    }

}
