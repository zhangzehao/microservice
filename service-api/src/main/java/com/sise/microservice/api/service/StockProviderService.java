package com.sise.microservice.api.service;

public interface StockProviderService {

    void freezeStock(Integer productId, Integer freezeNum);

    void freezeStockByTcc(Integer productId, Integer freezeNum);

}
