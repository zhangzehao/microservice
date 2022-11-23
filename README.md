## springcloud nacos dubbo seata分布式事务例子

**seata tc server 部署版本: 1.4.2**

**tc server启动配置文件registry.conf例子：**

    registry {
      type = "nacos"
    
      nacos {
        application = "seata-tc-server"
        serverAddr = "127.0.0.1:8848"
        group = "DEFAULT_GROUP"
        namespace = ""
        cluster = "default"
        username = "nacos"
        password = "nacos"
      }
    }
    
    config {
      type = "nacos"
      nacos {
        serverAddr = "127.0.0.1:8848"
        namespace = ""
        group = "DEFAULT_GROUP"
        username = "nacos"
        password = "nacos"
        dataId = "seataServer.properties"
      }
    }

**tc server在nacos上的配置文件seataServer.properties：**

    store.mode=db
    store.db.datasource=druid
    store.db.dbType=mysql
    store.db.driverClassName=com.mysql.cj.jdbc.Driver
    store.db.url=jdbc:mysql://192.168.3.101:3306/seata?serverTimezone=Asia/Shanghai&useSSL=false
    store.db.user=root
    store.db.password=xxx
    store.db.minConn=5
    store.db.maxConn=30
    store.db.globalTable=global_table
    store.db.branchTable=branch_table
    store.db.queryLimit=100
    store.db.lockTable=lock_table
    store.db.maxWait=5000
    
    server.recovery.committingRetryPeriod=1000
    server.recovery.asynCommittingRetryPeriod=1000
    server.recovery.rollbackingRetryPeriod=1000
    server.recovery.timeoutRetryPeriod=1000
    server.maxCommitRetryTimeout=-1
    server.maxRollbackRetryTimeout=-1
    server.rollbackRetryTimeoutUnlockEnable=false
    server.undo.logSaveDays=7
    server.undo.logDeletePeriod=86400000
    
    transport.serialization=seata
    transport.compressor=none
    
    metrics.enabled=false
    metrics.registryType=compact
    metrics.exporterList=prometheus
    metrics.exporterPrometheusPort=9898

**tc server的seata数据库依赖的表**

    create table global_table
    (
        xid                       varchar(128)  not null comment '全局事务ID'
            primary key,
        transaction_id            bigint        null comment '事务ID',
        status                    tinyint       not null comment '状态',
        application_id            varchar(32)   null comment '应用ID',
        transaction_service_group varchar(32)   null comment '事务分组名',
        transaction_name          varchar(128)  null comment '执行事务的方法',
        timeout                   int           null comment '超时时间',
        begin_time                bigint        null comment '开始时间',
        application_data          varchar(2000) null comment '应用数据',
        gmt_create                datetime      null comment '创建时间',
        gmt_modified              datetime      null comment '修改时间'
    ) charset = utf8;
    
    create index idx_gmt_modified_status
        on global_table (gmt_modified, status);
    
    create index idx_transaction_id
        on global_table (transaction_id);
    
    
    create table branch_table
    (
        branch_id         bigint        not null comment '分支事务ID'
            primary key,
        xid               varchar(128)  not null comment '全局事务ID',
        transaction_id    bigint        null comment '全局事务ID，不带TC地址',
        resource_group_id varchar(32)   null comment '资源分组ID',
        resource_id       varchar(256)  null comment '资源ID',
        branch_type       varchar(8)    null comment '事务模式，AT、XA等',
        status            tinyint       null comment '状态',
        client_id         varchar(64)   null comment '客户端ID',
        application_data  varchar(2000) null comment '应用数据',
        gmt_create        datetime(6)   null comment '创建时间',
        gmt_modified      datetime(6)   null comment '修改时间'
    ) charset = utf8;
    
    create index idx_xid
        on branch_table (xid);
    
    
    create table lock_table
    (
        row_key        varchar(128) not null comment '行键'
            primary key,
        xid            varchar(96)  null comment '全局事务ID',
        transaction_id bigint       null comment '全局事务ID，不带TC 地址',
        branch_id      bigint       not null comment '分支ID',
        resource_id    varchar(256) null comment '资源ID',
        table_name     varchar(32)  null comment '表名',
        pk             varchar(36)  null comment '主键对应的值',
        gmt_create     datetime     null comment '创建时间',
        gmt_modified   datetime     null comment '修改时间'
    ) charset = utf8;
    
    create index idx_branch_id
        on lock_table (branch_id);



**AT模式需要在每个微服务的数据库加undo_log表**

    create table undo_log
    (
        branch_id     bigint       not null comment '分支事务ID',
        xid           varchar(100) not null comment '全局事务ID',
        context       varchar(128) not null comment '上下文',
        rollback_info longblob     not null comment '回滚信息',
        log_status    int          not null comment '状态，0正常，1全局已完成',
        log_created   datetime(6)  not null comment '创建时间',
        log_modified  datetime(6)  not null comment '修改时间',
        constraint ux_undo_log
            unique (xid, branch_id)
    ) comment 'AT transaction mode undo table';


**正常请求例子**

    curl --location --request POST 'http://localhost:8081/seata/saveOrder' \
    --form 'userId="888888"' \
    --form 'productId="1000"' \
    --form 'price="1.23"' \
    --form 'quantity="2"'

**测试全局事务回滚例子**

    curl --location --request POST 'http://localhost:8081/seata/saveOrder' \
    --form 'userId="888888"' \
    --form 'productId="1000"' \
    --form 'price="1.23"' \
    --form 'quantity="999"'


