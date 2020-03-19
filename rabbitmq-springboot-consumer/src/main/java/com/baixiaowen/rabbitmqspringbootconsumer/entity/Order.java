package com.baixiaowen.rabbitmqspringbootconsumer.entity;

import java.io.Serializable;

public class Order implements Serializable {
    
    private String id;
    
    private String orderName;

    public Order() {
    }

    public Order(String id, String orderName) {
        this.id = id;
        this.orderName = orderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
