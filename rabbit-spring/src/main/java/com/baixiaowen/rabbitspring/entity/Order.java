package com.baixiaowen.rabbitspring.entity;

public class Order {

    private String id;

    private String name;

    private String context;

    public Order() {
    }

    public Order(String id, String name, String context) {
        this.id = id;
        this.name = name;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
