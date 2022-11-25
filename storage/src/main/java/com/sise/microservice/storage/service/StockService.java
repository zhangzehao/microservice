package com.sise.microservice.storage.service;

import com.sise.microservice.storage.dao.StockDao;
import com.sise.microservice.storage.dto.StockEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Random;

@Service
public class StockService {

    @Autowired
    private StockDao stockDao;

    public StockEntity queryOrder(Integer productId) {
        return stockDao.selectByProductId(productId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void freezeStock(Integer productId, Integer freezeNum) {
        Assert.isTrue(productId != null && productId > 0, "产品id参数错误");
        Assert.isTrue(freezeNum > 0, "冻结库存参数错误");
        StockEntity stockEntity = stockDao.selectByProductId(productId);
        if (stockEntity == null) {
            stockEntity = new StockEntity();
            stockEntity.setProductId(productId);
            stockEntity.setStockNum(10 + new Random().nextInt(10000));
            stockEntity.setFreezeNum(0);
            stockDao.insertOne(stockEntity);
        }

        if (stockEntity.getStockNum() < freezeNum) {
            throw new RuntimeException("库存不足");
        }
        if (freezeNum == 999) {
            throw new RuntimeException("模拟分布式事务异常");
        }

        stockEntity.setFreezeNum(stockEntity.getFreezeNum() + freezeNum);
        stockEntity.setStockNum(stockEntity.getStockNum() - freezeNum);
        stockDao.updateByProductId(stockEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void rollbackFreeze(Integer productId, Integer freezeNum) {
        Assert.isTrue(productId != null && productId > 0, "产品id参数错误");
        Assert.isTrue(freezeNum > 0, "冻结库存参数错误");
        StockEntity stockEntity = stockDao.selectByProductId(productId);
        if (stockEntity == null) {
            return;
        }

        if (stockEntity.getFreezeNum().intValue() >= freezeNum) {
            stockEntity.setFreezeNum(stockEntity.getFreezeNum() - freezeNum);
            stockEntity.setStockNum(stockEntity.getStockNum() + freezeNum);
            stockDao.updateByProductId(stockEntity);
        }
    }

}
