package com.sise.microservice.orders.dao;

import com.sise.microservice.orders.dto.OrderEntity;

public interface OrderDao {

    OrderEntity selectById(Long id);

    int insertOne(OrderEntity entity);
}
