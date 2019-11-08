package com.xd.batch.strategy.enums;

public enum OrderTypeEnum {

    INSTANT(1,"即时订单"),
    CANCEL(3,"取消订单"),
    BOOKING(2,"预定订单");
    private int value;
    private String name;

    OrderTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

}
