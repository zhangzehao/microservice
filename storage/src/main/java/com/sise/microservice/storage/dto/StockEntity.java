package com.sise.microservice.storage.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockEntity implements Serializable {

    private Long id;

    private Integer productId;

    private Integer stockNum;

    private Integer freezeNum;
}
