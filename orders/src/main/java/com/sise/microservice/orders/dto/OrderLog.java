package com.sise.microservice.orders.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderLog implements Serializable {

    private Long id;

    private Long orderId;

    private String detail;
}
