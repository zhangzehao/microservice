package com.sise.microservice.orders.provider;

import com.sise.microservice.api.service.OrderProviderService;
import com.sise.microservice.orders.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Service
public class OrderProviderServiceImpl implements OrderProviderService {

    @Autowired
    private OrderService orderService;

    @Override
    public void saveOrder(Integer userId, Integer productId, BigDecimal price, Integer quantity) {
        orderService.addOrder(userId, productId, price, quantity);
    }

    @Override
    public void saveOrderByTcc(Integer userId, Integer productId, BigDecimal price, Integer quantity) {
        orderService.addOrderByTcc(userId, productId, price, quantity);
    }
}
