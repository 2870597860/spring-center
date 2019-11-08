package com.xd.batch.strategy.service;

import com.xd.batch.strategy.entity.OrderDTO;

public abstract class AbstractStrategy {
    abstract void process(OrderDTO orderDTO);
}
