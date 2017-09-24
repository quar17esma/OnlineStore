package com.serhii.shutyi.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private LocalDateTime orderedAt;
    private Client client;
    private List<Good> goods;

    public Order() {
        this.goods = new ArrayList<>();
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

    public static class Builder {
        private  Order order;

        public Builder() {
            this.order = new Order();
        }

        public Order build() {
            return order;
        }

        public Builder setId(final int id) {
            order.setId(id);
            return this;
        }

        public Builder setOrderedAt(final LocalDateTime orderedAt) {
            order.setOrderedAt(orderedAt);
            return this;
        }

        public Builder setClient(final Client client) {
            order.setClient(client);
            return this;
        }

        public Builder setGoods(final List<Good> goods) {
            order.setGoods(goods);
            return this;
        }
    }
}
