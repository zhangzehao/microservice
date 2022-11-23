package com.sise.microservice.orders.dao;

import com.sise.microservice.orders.dto.OrderLog;

public interface OrderLogDao {

    OrderLog selectByOrderId(Long orderId);

    int insertOne(OrderLog entity);
}
