package com.sise.microservice.orders.service;

import com.alibaba.fastjson.JSONObject;
import com.sise.microservice.orders.dao.OrderDao;
import com.sise.microservice.orders.dao.OrderLogDao;
import com.sise.microservice.orders.dto.OrderEntity;
import com.sise.microservice.orders.dto.OrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderLogDao orderLogDao;

    public OrderEntity queryOrder(Long id) {
        OrderEntity orderEntity = orderDao.selectById(id);
        OrderLog orderLog = orderLogDao.selectByOrderId(id);
        orderEntity.setOrderLog(orderLog);
        return orderEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addOrder(Integer userId, Integer productId, BigDecimal price, Integer quantity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(userId);
        orderEntity.setProductId(productId);
        orderEntity.setQuantity(quantity);
        orderEntity.setPrice(price.setScale(6, BigDecimal.ROUND_DOWN));
        orderEntity.setAmount(orderEntity.getPrice().multiply(new BigDecimal(orderEntity.getQuantity())));
        orderDao.insertOne(orderEntity);

        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderEntity.getId());
        orderLog.setDetail("创建订单: " + JSONObject.toJSONString(orderEntity));
        orderLogDao.insertOne(orderLog);
    }

}
