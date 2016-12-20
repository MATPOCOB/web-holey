package com.codeborne;

import com.codeborne.models.Product;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
}