create table orders.t_order
(
    id         bigint auto_increment
        primary key,
    user_id    int            not null,
    product_id int            null comment '产品id',
    price      decimal(21, 6) not null comment '产品单价',
    quantity   int            not null comment '产品数量',
    amount     decimal(21, 6) not null comment '订单金额'
);



create table orders.t_order_log
(
    id       bigint auto_increment
        primary key,
    order_id bigint        null,
    detail   varchar(1024) null
);

create table storage.t_stock
(
    id         bigint auto_increment
        primary key,
    product_id int           not null comment '产品id',
    stock_num  int default 0 not null comment '库存数量',
    freeze_num int default 0 not null comment '冻结库存',
    constraint uniq_t_stock_product_id
        unique (product_id)
)
    comment '产品库存表';

