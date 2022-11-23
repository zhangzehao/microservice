package com.sise.microservice.orders.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderEntity implements Serializable {

    private Long id;

    private Integer userId;

    private Integer productId;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal amount;

    private OrderLog orderLog;

}
