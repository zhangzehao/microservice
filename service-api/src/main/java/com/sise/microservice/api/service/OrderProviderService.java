package com.sise.microservice.api.service;

import java.math.BigDecimal;

public interface OrderProviderService {

    void saveOrder(Integer userId, Integer productId, BigDecimal price, Integer quantity);
}
