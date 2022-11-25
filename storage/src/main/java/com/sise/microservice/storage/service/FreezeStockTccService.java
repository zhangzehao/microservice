package com.sise.microservice.storage.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * seata tcc 模式接口
 */
@LocalTCC
public interface FreezeStockTccService {

    /**
     * 定义两阶段提交 name = 该tcc的bean名称,全局唯一 commitMethod = commit 为二阶段确认方法 rollbackMethod = rollback 为二阶段取消方法
     * useTCCFence=true 为开启防悬挂
     * BusinessActionContextParameter注解 传递参数到二阶段中
     *
     * @return String
     */
    @TwoPhaseBusinessAction(name = "freezeStock", commitMethod = "commitFreeze", rollbackMethod = "rollbackFreeze")
    void freezeStock(@BusinessActionContextParameter(paramName = "productId") Integer productId,
                     @BusinessActionContextParameter(paramName = "freezeNum") Integer freezeNum);

    /**
     * 确认方法、可以另命名，但要保证与commitMethod一致 context可以传递try方法的参数
     *
     * @param context 上下文
     * @return boolean
     */
    void commitFreeze(BusinessActionContext context);

    /**
     * 二阶段取消方法
     *
     * @param context 上下文
     * @return boolean
     */
    void rollbackFreeze(BusinessActionContext context);
}
