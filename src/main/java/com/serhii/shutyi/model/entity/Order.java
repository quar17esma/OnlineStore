package com.serhii.shutyi.model.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    int id;
    LocalDateTime orderedAt;
    Client client;
    List<Good> goods;

    public Order(int id, LocalDateTime orderedAt, Client client, List<Good> goods) {
        this.id = id;
        this.orderedAt = orderedAt;
        this.client = client;
        this.goods = goods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderedAt=" + orderedAt +
                ", client=" + client +
                ", goods=" + goods +
                '}';
    }
}
