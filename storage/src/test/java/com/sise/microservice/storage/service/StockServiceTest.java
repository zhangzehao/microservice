package com.sise.microservice.storage.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Test
    public void queryOrder() {
        stockService.queryOrder(100);
    }

    @Test
    public void freezeStock() {
        stockService.freezeStock(100, 1);
    }
}