package com.sise.microservice.storage.dao;


import com.sise.microservice.storage.dto.StockEntity;

public interface StockDao {

    StockEntity selectByProductId(Integer productId);

    int insertOne(StockEntity entity);

    int updateByProductId(StockEntity entity);
}
