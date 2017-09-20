package com.serhii.shutyi.model.entity;

import java.util.List;

public class Client {

    private int id;

    private String name;

    private int discount;

    private boolean isInBlackList;

    private User user;

    private List<Order> orders;

    public Client() {
    }

    public Client(int id, String name, int discount, boolean isInBlackList, User user) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.isInBlackList = isInBlackList;
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

    public boolean isInBlackList() {
        return isInBlackList;
    }

    public void setIsInBlackList(boolean isInBlackList) {
        this.isInBlackList = isInBlackList;
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
                ", isInBlackList=" + isInBlackList +
                ", user=" + user +
                ", orders=" + orders +
                '}';
    }

    public static class Builder {
        private  Client client;

        public Builder() {
            this.client = new Client();
        }

        public Client build() {
            return client;
        }

        public Builder setId(final int id) {
            client.setId(id);
            return this;
        }

        public Builder setName(final String name) {
            client.setName(name);
            return this;
        }

        public Builder setDiscount(final int discount) {
            client.setDiscount(discount);
            return this;
        }

        public Builder setIsInBlackList(final boolean isInBlackList) {
            client.setIsInBlackList(isInBlackList);
            return this;
        }

        public Builder setUser(final User user) {
            client.setUser(user);
            return this;
        }

        public Builder setOrders(final List<Order> orders) {
            client.setOrders(orders);
            return this;
        }
    }
}
