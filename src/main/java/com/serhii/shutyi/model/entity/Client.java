package com.serhii.shutyi.model.entity;

import java.util.List;

public class Client {

    private int id;

    private String name;

    private int discount;

    private User user;

    private List<Order> orders;


    public Client(int id, String name, int discount, User user) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discount=" + discount +
                ", user=" + user +
                ", orders=" + orders +
                '}';
    }
}
