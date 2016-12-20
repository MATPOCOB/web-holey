package com.codeborne;

import com.codeborne.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;

@Controller
public class HoleySQLController {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @RequestMapping(value = "/sql/search")
  public String search(Model model, @RequestParam(required = false) String query) {
    model.addAttribute("query", query);
    model.addAttribute("products", findProducts(query));
//    model.addAttribute("products", findProductsSafe(query));
    return "/sql/search";
  }

  public List<Product> findProducts(String query) {
    String sql = "SELECT * FROM products WHERE name like '%" + query + "%'";
    return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Product().setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setPrice(new BigDecimal(rs.getString("price")))
    );
  }

  public List<Product> findProductsSafe(String query) {
    PreparedStatementCreator creator = con -> {
      PreparedStatement updateSales = con.prepareStatement(
          "SELECT * FROM products WHERE name like ?");
      updateSales.setString(1, "%"+query+"%");
      return updateSales;
    };

    return jdbcTemplate.query(creator, (rs, rowNum) ->
        new Product().setId(rs.getLong("id"))
            .setName(rs.getString("name"))
            .setPrice(new BigDecimal(rs.getString("price"))));
  }

  public List<Product> listProducts(String orderBy) {
    PreparedStatementCreator creator = con -> {
      PreparedStatement updateSales = con.prepareStatement(
          "SELECT * FROM products ORDER BY " + orderBy);
      return updateSales;
    };

    return jdbcTemplate.query(creator, (rs, rowNum) ->
        new Product().setId(rs.getLong("id"))
            .setName(rs.getString("name"))
            .setPrice(new BigDecimal(rs.getString("price"))));
  }

  @RequestMapping(value = "/sql/list")
  public String list(Model model, @RequestParam(defaultValue = "name", required = false) String orderBy) {
    model.addAttribute("products", listProducts(orderBy));
    model.addAttribute("orderBy", orderBy);
    System.out.println(orderBy);
    return "/sql/list";
  }
}