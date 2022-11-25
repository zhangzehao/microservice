package com.sise.microservice.webfront.controller;

import com.sise.microservice.webfront.service.SeataTxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private SeataTxService seataTxService;

    @PostMapping("/at/saveOrder")
    public String atModel(Integer userId, Integer productId, BigDecimal price, Integer quantity) {
        try {
            seataTxService.saveOrder(userId, productId, price, quantity);
            return "success";
        } catch (Exception e) {
            log.error("发生异常:", e);
            return "error";
        }
    }

    @PostMapping("/tcc/saveOrder")
    public String tccModel(Integer userId, Integer productId, BigDecimal price, Integer quantity) {
        try {
            seataTxService.saveOrderByTcc(userId, productId, price, quantity);
            return "success";
        } catch (Exception e) {
            log.error("发生异常:", e);
            return "error";
        }
    }

}