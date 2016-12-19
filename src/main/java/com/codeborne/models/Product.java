package com.codeborne.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Accessors(chain = true)
@Setter
@Getter
public class Product {
  private long id;
  private String name;
  private BigDecimal price;
}
