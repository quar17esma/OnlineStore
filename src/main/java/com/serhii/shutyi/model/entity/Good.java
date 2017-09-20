package com.serhii.shutyi.model.entity;

import java.util.List;

public class Good {
    private int id;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private List<Category> categories;

    public Good(int id, String name, String description, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Good() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", categories=" + categories +
                '}';
    }

    public static class Builder {
        private Good good;

        public Builder() {
            this.good = new Good();
        }

        public Good build() {
            return good;
        }

        public Builder setId(final int id) {
            good.setId(id);
            return this;
        }

        public Builder setName(final String name) {
            good.setName(name);
            return this;
        }

        public Builder setDescription(final String description) {
            good.setDescription(description);
            return this;
        }

        public Builder setPrice(final int price) {
            good.setPrice(price);
            return this;
        }

        public Builder setQuantity(final int quantity) {
            good.setQuantity(quantity);
            return this;
        }

        public Builder setCategories(final List<Category> categories) {
            good.setCategories(categories);
            return this;
        }
    }
}
