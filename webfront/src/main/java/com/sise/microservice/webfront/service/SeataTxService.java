package com.sise.microservice.webfront.service;

import com.sise.microservice.api.service.OrderProviderService;
import com.sise.microservice.api.service.StockProviderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SeataTxService {

    @Reference
    private OrderProviderService orderProviderService;

    @Reference
    private StockProviderService stockProviderService;

    /**
     * seata AT分布式事务
     *
     * @param userId
     * @param productId
     * @param price
     * @param quantity
     */
    @GlobalTransactional
    public void saveOrder(Integer userId, Integer productId, BigDecimal price, Integer quantity) {
        orderProviderService.saveOrder(userId, productId, price, quantity);
        stockProviderService.freezeStock(productId, quantity);
    }
}
