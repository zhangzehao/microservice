package com.sise.microservice.storage.provider;

import com.sise.microservice.api.service.StockProviderService;
import com.sise.microservice.storage.service.FreezeStockTccService;
import com.sise.microservice.storage.service.StockService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class StockProviderServiceImpl implements StockProviderService {

    @Autowired
    private StockService stockService;

    @Autowired
    private FreezeStockTccService freezeStockTccService;

    @Override
    public void freezeStock(Integer productId, Integer freezeNum) {
        stockService.freezeStock(productId, freezeNum);
    }

    @Override
    public void freezeStockByTcc(Integer productId, Integer freezeNum) {
        freezeStockTccService.freezeStock(productId, freezeNum);
    }
}
