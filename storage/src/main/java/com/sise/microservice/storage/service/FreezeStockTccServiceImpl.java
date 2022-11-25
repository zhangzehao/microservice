package com.sise.microservice.storage.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreezeStockTccServiceImpl implements FreezeStockTccService {

    @Autowired
    private StockService stockService;

    @Override
    public void freezeStock(Integer productId, Integer freezeNum) {
        System.out.println("执行freezeStock方法...");
        //事务悬挂问题: 先执行了cancel方法，后执行try方法导致一直没办法commit或者cancel，事务一直在中间状态
        stockService.freezeStock(productId, freezeNum);
    }

    @Override
    public void commitFreeze(BusinessActionContext context) {
        System.out.println("执行commitFreeze方法...");
        //int i = 1/0;
        System.out.println("一阶段直接冻结金额,二阶段提交空实现");
    }

    @Override
    public void rollbackFreeze(BusinessActionContext context) {
        System.out.println("执行rollbackFreeze方法...");
//        int i = 1 / 0;
        //空回滚问题，try方法没有执行或者执行失败，却执行了cancel方法
        System.out.println("全局事务id=" + context.getXid());
        Integer productId = (Integer) context.getActionContext("productId");
        Integer freezeNum = (Integer) context.getActionContext("freezeNum");
        System.out.println("获取try方法的入参,productId=" + productId + ",freezeNum=" + freezeNum);
        stockService.rollbackFreeze(productId, freezeNum);
    }
}
